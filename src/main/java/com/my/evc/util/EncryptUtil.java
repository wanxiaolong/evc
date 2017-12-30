package com.my.evc.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类。
 */
public class EncryptUtil {

	/**
	 * 用MD5算法加密字符串。
	 */
	public static String md5(String text) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(text.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return new BigInteger(1, md5.digest()).toString(16);
	}
}
