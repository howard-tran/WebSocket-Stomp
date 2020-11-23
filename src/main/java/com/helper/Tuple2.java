package com.helper;

public class Tuple2<par1, par2> {
  private par1 data1;
  private par2 data2;

  public Tuple2(par1 data1, par2 data2) {
    this.data1 = data1; 
    this.data2 = data2;
  }

  public Boolean isEmpty() {
    return data1 == null || data2 == null;
  }

  public par1 get_1() {
    return this.data1;
  }

  public par2 get_2() {
    return this.data2;
  }
}
