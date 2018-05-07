package com.my.evc.util;

public class StringUtil {
	public static boolean isEmpty(String s) {
		if(s == null || s.equals("") || s.equalsIgnoreCase("null"))
			return false;
		return true;
	}
}
