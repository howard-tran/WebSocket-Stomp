package com.service;

import com.helper.IFunction;
import com.helper.LogUtils;
import java.util.Optional;

public interface LogService<T> {
	public default Optional<T> run(IFunction<T> func) {
		try {
			return Optional.of((T)func.run());
			//
		} catch (Exception e) {
			RuntimeException exception = new RuntimeException(e);
      LogUtils.LogError("[ERROR]", exception);
      
      return Optional.empty();
		}
	}
}
