package com.yahoo.ycsb.generator;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertFalse;

import java.io.IOException;
import java.util.*;

public class TestFacebookFieldLengthGenerator {
  @Test
  public void testMinAndMaxParameter() {
    long min = 0;
    try {
      FacebookFieldLengthGenerator fb = new FacebookFieldLengthGenerator();

      SortedMap<Long, Integer> m = new TreeMap<Long, Integer>();
      for (int i = 0; i < 10000; i++) {
        long rnd = fb.nextValue();
        if (m.containsKey(rnd))
          m.put(rnd, m.get(rnd) + 1);
        else
          m.put(rnd, 1);
        //System.out.println(rnd);
        assertFalse(rnd < min);
      }

      //for(Map.Entry<Long,Integer> entry : m.entrySet()) {
        //Long key = entry.getKey();
        //Integer value = entry.getValue();

        //System.out.println(key + " => " + value);
      //}
    } catch (IOException e) {
      System.out.println(e.getMessage());
      assertFalse(true);
    }
  }
}
