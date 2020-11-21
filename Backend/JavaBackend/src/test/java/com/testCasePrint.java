package com;

import com.helper.IFunction;

public class testCasePrint {
  public int testCount = 0;

  public void run(IFunction<Object> func) throws Exception {
    System.out.println(String.format("\n============== test case %d ==============\n", 
      ++this.testCount));
    func.run();
  }
}
