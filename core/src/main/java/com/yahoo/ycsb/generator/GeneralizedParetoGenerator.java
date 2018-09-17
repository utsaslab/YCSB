package com.yahoo.ycsb.generator;

import com.yahoo.ycsb.Utils;

/**
 * A generator of a generalized pareto distribution.
 */
public class GeneralizedParetoGenerator extends NumberGenerator {
  private final double mu;
  private final double sigma;
  private final double xi;

  public GeneralizedParetoGenerator(final double mu, final double sigma,
      final double xi) {
    this.mu = mu;
    this.sigma = sigma;
    this.xi = xi;
  }

  @Override
  public Double nextValue() {
    if (xi == 0) {
      return mu - sigma * Math.log(Utils.random().nextDouble());
    } else {
      return mu +
        ((sigma * (Math.pow(Utils.random().nextDouble(), -xi) - 1)) / xi);
    }
  }

  @Override
  public double mean() {
    // For xi < 1
    return mu + (sigma / (1 - xi));
  }

  //public static void main(String[] args) {
    //GeneralizedParetoGenerator g = new GeneralizedParetoGenerator(90, 100);
    //int j = 0;
    //for (int i = 0; i < 1000; i++) {
      //if (e.nextValue() < 100) {
        //j++;
      //}
    //}
    //System.out.println("Got " + j + " hits.  Expect 900");
  //}
}
