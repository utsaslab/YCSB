package com.yahoo.ycsb.workloads;

import com.yahoo.ycsb.Utils;
import com.yahoo.ycsb.WorkloadException;
import com.yahoo.ycsb.generator.FacebookFieldLengthGenerator;

import java.util.Properties;
import java.io.IOException;

/**
 * A Workload modeled after Facebook's Memcached workload.
 * However due to some reusable functionality this class extends {@link
 * CoreWorkload} and overrides necessary methods like init, buildKeyName etc.
 */
public class FacebookWorkload extends CoreWorkload {
  @Override
  public void init(Properties p) throws WorkloadException {
    super.init(p);
    try {
      fieldlengthgenerator = new FacebookFieldLengthGenerator();
    } catch (IOException e) {
      throw new WorkloadException("Could not create field length generator.\n");
    }
  }

  // TODO: use the FacebookFieldLengthGenerator
  @Override
  protected String buildKeyName(long keynum) {
    if (!orderedinserts) {
      keynum = Utils.hash(keynum);
    }
    String value = Long.toString(keynum);
    int fill = zeropadding - value.length();
    String prekey = "user";
    for (int i = 0; i < fill; i++) {
      prekey += '0';
    }
    return prekey + value;
  }
}
