package com.yahoo.ycsb.workloads;

import com.yahoo.ycsb.*;
import com.yahoo.ycsb.generator.*;
import com.yahoo.ycsb.Utils;
import com.yahoo.ycsb.WorkloadException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.ArrayList;

/**
 * A Workload modeled after Facebook's Memcached workload.
 * However due to some reusable functionality this class extends {@link
 * CoreWorkload} and overrides necessary methods like init, buildKeyName etc.
 */
public class FacebookWorkload extends CoreWorkload {

  protected NumberGenerator keylengthgenerator;

  protected static final double EXTREME_VALUE_LOCATION = 30.7984;
  protected static final double EXTREME_VALUE_SCALE = 8.20449;
  protected static final double EXTREME_VALUE_SHAPE = -0.078688;

  public static final String USE_DEFAULT_FIELD_LENGTH_GENERATOR = "usedefaultfieldgenerator";


  protected ArrayList<Integer> keysizes;

  @Override
  public void init(Properties p) throws WorkloadException {
    super.init(p);
    boolean usedefaultgenerator = Boolean.parseBoolean(p.getProperty(
          USE_DEFAULT_FIELD_LENGTH_GENERATOR, false));
    if (!usedefaultgenerator) {
      try {
        fieldlengthgenerator = new FacebookFieldLengthGenerator();
      } catch (IOException e) {
        throw new WorkloadException(e.getMessage());
      }
    }

    keylengthgenerator = new GeneralizedExtremeValueGenerator(
        EXTREME_VALUE_LOCATION, 
        EXTREME_VALUE_SCALE, 
        EXTREME_VALUE_SHAPE);

    int keycount = Integer.parseInt(p.getProperty(
        Client.RECORD_COUNT_PROPERTY, Client.DEFAULT_RECORD_COUNT));
    keysizes = new ArrayList<Integer>(keycount);

    // Only read from key size file if we are running the workload
    if (Boolean.valueOf(p.getProperty(
            Client.DO_TRANSACTIONS_PROPERTY, String.valueOf(true)))) {
      readKeySizes();
    }
  }

  protected void readKeySizes() throws WorkloadException {
    // TODO: maybe use a id to identify each run intead of hardcoding filename
    try (BufferedReader in = new BufferedReader(new FileReader(
            "temp_keysizes.txt"))) {
      String line;
      while ((line = in.readLine()) != null) {
        keysizes.add(Integer.parseInt(line));
      }
    } catch (IOException e) {
      throw new WorkloadException(e.getMessage());
    }
  }

  @Override
  public void cleanup() throws WorkloadException {
    // TODO: maybe use a id to identify each run
    // saves the keysizes to file to be reused by the actual workload
    try (BufferedWriter out = new BufferedWriter(new FileWriter(
            "temp_keysizes.txt"))) {
      for (int size: keysizes) {
        out.write(Integer.toString(size));
        out.write("\n"); 
      }
    } catch (IOException e) {
      throw new WorkloadException(e.getMessage());
    } 
  }

  // NOTE: This function might break the ordering of the keys.
  @Override
  protected String buildKeyName(long keynum) {
    int size;
    // only generate a new key size if we have not already done so for this
    // keynum
    // TODO: this check can be optimized.
    if ((int)keynum >= keysizes.size()) {
      size = keylengthgenerator.nextValue().intValue();
      keysizes.add(size);
    } else {
      size = keysizes.get((int)keynum);
    }

    StringBuilder sb = new StringBuilder(size);

    if (!orderedinserts) {
      keynum = Utils.hash(keynum);
    }
    String value = Long.toString(keynum);
    String prekey = "";
    if (value.length() < size) {
      int fill = size - value.length();
      for (int i = 0; i < fill; i++) {
        prekey += '0';
      }
    }
    sb.append(prekey);
    sb.append(value);
    sb.setLength(size);
    return sb.toString();
  }
}
