package com;

import com.helper.IFunction;
import java.io.Console;

public class testCasePrint {
  public static int testCount = 0;

	public void run(IFunction<Object> func, String... descriptions) throws Exception {
		System.out.print(String.format("\n[%sTESTCASE%s] %s-------------------- %d --------------------%s",
				ConsoleColors.YELLOW_BOLD_BRIGHT, ConsoleColors.RESET, ConsoleColors.WHITE_BOLD_BRIGHT,
				++testCasePrint.testCount, ConsoleColors.RESET));

		for (int i = 0; i < descriptions.length; i++) {
			System.out.print(String.format("\n[%sINFO%s] %s", ConsoleColors.BLUE_BOLD_BRIGHT, ConsoleColors.RESET,
					descriptions[i]));
		}
		System.out.println("\n");

    func.run();
    System.out.println();
  }
  
  public void logErrorToTerminal(String message) {
    System.out.print(String.format("[%sERROR_IN_TESTCASE%s] %s\n", ConsoleColors.RED_BOLD_BRIGHT, ConsoleColors.RESET,
      message));
  }
}
