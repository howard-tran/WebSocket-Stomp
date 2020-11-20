package com.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class helperTest {

  public void executeFunc(Runnable r) {
    try {
      r.run();
    } catch (Exception e) {
      throw e;
    }
  }

  public Object executeFunc(Supplier<Object> s) {
    try {
      return s.get(); 
    } catch (Exception e) {
      throw e;
    }
  }

  public void helloworld() {
    Consumer<Integer> func = (ert) -> {
      System.out.println(ert);
    };

    executeFunc(() -> {
      func.accept(2); 
    });
  }

  public void helloworld2() {

    Function<Integer, List<Integer>> func = (ert) -> {
      List<Integer> arr = new ArrayList<>(); 
      
      for (int i = 0; i < ert; i++) {
        arr.add(i + 1);
      }
      return arr; 
    };

    List<Integer> t = (ArrayList<Integer>)executeFunc(() -> {
      return func.apply(100); 
    });
    for (int i = 0; i < t.size(); i++) {
      System.out.print(t.get(i) + " ");
    }
    System.out.println(); 
  }
}
