package com.yahoo.ycsb.workloads;

import com.yahoo.ycsb.Utils;
import com.yahoo.ycsb.WorkloadException;
import com.yahoo.ycsb.generator.*;

import java.util.Properties;
import java.io.IOException;

/**
 * A Workload modeled after Facebook's Memcached workload.
 * However due to some reusable functionality this class extends {@link
 * CoreWorkload} and overrides necessary methods like init, buildKeyName etc.
 */
public class FacebookWorkload extends CoreWorkload {

  protected NumberGenerator keylengthgenerator;

  private static final double EXTREME_VALUE_LOCATION = 30.7984;
  private static final double EXTREME_VALUE_SCALE = 8.20449;
  private static final double EXTREME_VALUE_SHAPE = -0.078688;

  @Override
  public void init(Properties p) throws WorkloadException {
    super.init(p);
    try {
      fieldlengthgenerator = new FacebookFieldLengthGenerator();
    } catch (IOException e) {
      throw new WorkloadException("Could not create field length generator.\n");
    }

    keylengthgenerator = new GeneralizedExtremeValueGenerator(
        EXTREME_VALUE_LOCATION, 
        EXTREME_VALUE_SCALE, 
        EXTREME_VALUE_SHAPE);
  }

  // NOTE: This function might break the ordering of the keys.
  @Override
  protected String buildKeyName(long keynum) {
    int size = keylengthgenerator.nextValue().intValue();
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
