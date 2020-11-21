package com.service;

import com.helper.IFunction;
import com.helper.LogUtils;
import java.util.Optional;

public interface LogService {
  public default Object run(IFunction<Object> func) {
    try {
      return Optional.of(func.run());
      //
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);
      throw new RuntimeException(e);
    }
  }
}
