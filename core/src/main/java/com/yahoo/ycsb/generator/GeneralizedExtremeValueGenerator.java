package com.yahoo.ycsb.generator;

import com.yahoo.ycsb.Utils;

import static java.lang.Math.log;
import static java.lang.Math.pow;

/**
 * A generator of a generalized extreme value distribution.
 */
public class GeneralizedExtremeValueGenerator extends NumberGenerator {
  private final double mu;
  private final double sigma;
  private final double xi;

  public GeneralizedExtremeValueGenerator(final double mu, final double sigma, 
      final double xi) {
    this.mu = mu;
    this.sigma = sigma;
    this.xi = xi;
  }

  @Override
  public Double nextValue() {
    // TODO: handle xi = 0 case
    double x = Utils.random().nextDouble();
    
    double a = pow(-log(x), -xi);
    double b = -xi * mu * pow(-log(x), xi);
    double c = sigma * pow(-log(x), xi) - sigma;
    return -(a * (b + c)) / xi;
  }

  @Override
  public double mean() {
    throw new UnsupportedOperationException(
        "@todo implement GeneralizedExtremeValueGenerator.mean()");
  }

  //public static void main(String[] args) {
    //GeneralizedExtremeValueGenerator g = new GeneralizedExtremeValueGenerator(90, 100);
    //int j = 0;
    //for (int i = 0; i < 1000; i++) {
      //if (e.nextValue() < 100) {
        //j++;
      //}
    //}
    //System.out.println("Got " + j + " hits.  Expect 900");
  //}
}
