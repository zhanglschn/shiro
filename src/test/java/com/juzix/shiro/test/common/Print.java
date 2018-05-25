package com.juzix.shiro.test.common;

import java.io.PrintStream;

public abstract class Print {
	public final static void print(Object obj) {
		System.out.println(obj);
	}

	public final static void print() {
		System.out.println();
	}

	public final static void printnb(Object obj) {
		System.out.print(obj);
	}

	public static PrintStream printf(String format, Object... args) {
		return System.out.printf(format, args);
	}
	
	
	public static void format(String paramString, Object... paramVarArgs) {
		System.out.format(paramString, paramVarArgs);
	}
}