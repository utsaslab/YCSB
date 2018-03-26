package com.yahoo.ycsb.generator;

import com.yahoo.ycsb.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Generates field lengths using models similar to Facebook's Memcached
 * workload.  
 * */
public class FacebookFieldLengthGenerator extends NumberGenerator {
  private static final String DISCRETE_DISTRIBUTION_FILE_NAME = "fb.txt";

  private static final double DISCRETE_DISTRIBUTION_PROBABILITY = 0.44155;

  private static final int DISCRETE_DISTRIBUTION_LENGTH = 15;

  private static final double PARETO_LOCATION = 0;
  private static final double PARETO_SCALE = 214.476;
  private static final double PARETO_SHAPE = -0.348238;

  private ArrayList<Double> discreteFieldProbabilities;
  private GeneralizedParetoGenerator paretoGenerator;

  public FacebookFieldLengthGenerator() throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader(
            DISCRETE_DISTRIBUTION_FILE_NAME))) {
      String line;

      discreteFieldProbabilities = new ArrayList<>();

      while ((line = in.readLine()) != null) {
        discreteFieldProbabilities.add(Double.parseDouble(line));
      }
      if (discreteFieldProbabilities.size() != DISCRETE_DISTRIBUTION_LENGTH) {
        throw new IOException("Unexpected number of inputs!\n");
      }
    }

    paretoGenerator = new GeneralizedParetoGenerator(
        PARETO_LOCATION, PARETO_SCALE, PARETO_SHAPE);
  }

  @Override
  public Integer nextValue() {
    int nextValue = -1;
    // Determine if the discrete probabilty distribution should be used
    // instead of pareto
    if (Utils.random().nextDouble() < DISCRETE_DISTRIBUTION_PROBABILITY) {
      double random = Utils.random().nextDouble();

      for (int i = 0; i < DISCRETE_DISTRIBUTION_LENGTH; ++i) {
        random -= discreteFieldProbabilities.get(i);
        if (random <= 0.0d) {
          nextValue = i;
          break;
        }
      }
    } else {
      // we keep asking the pareto distribution until a value greater than
      // the length of the discrete table is returned. We have already
      // decided to not generate a value in the discrete table by this point.
      do {
        nextValue = paretoGenerator.nextValue().intValue();
      } while (nextValue < DISCRETE_DISTRIBUTION_LENGTH);
    }

    return nextValue;
  }

  @Override
  public double mean() {
    throw new UnsupportedOperationException(
        "@todo implement FacebookFieldLengthGenerator.mean()");
  }
}
