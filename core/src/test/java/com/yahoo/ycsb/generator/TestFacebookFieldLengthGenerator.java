package com.yahoo.ycsb.generator;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertFalse;

import java.io.IOException;
import java.nio.file.*;


public class TestFacebookFieldLengthGenerator {
  @Test
  public void testMinAndMaxParameter() {
    long min = 0;
    try {
      FacebookFieldLengthGenerator fb = new FacebookFieldLengthGenerator();

      for (int i = 0; i < 10000; i++) {
        long rnd = fb.nextValue();
        //System.out.println(rnd);
        assertFalse(rnd < min);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
      assertFalse(true);
    }
  }
}
