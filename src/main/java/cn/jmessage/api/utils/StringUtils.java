package cn.jmessage.api.utils;

import cn.jiguang.common.ServiceHelper;

public class StringUtils {

	public static void checkUsername(String username) {
		if ( null == username || username.trim().length() == 0) {
			throw new IllegalArgumentException("username must not be empty");
		}
		if (username.contains("\n") || username.contains("\r") || username.contains("\t")) {
			throw new IllegalArgumentException("username must not contain line feed character");
		}
		byte[] usernameByte = username.getBytes();
		if (usernameByte.length < 4 || usernameByte.length > 128) {
			throw new IllegalArgumentException("The length of username must between 4 and 128 bytes. Input is " + username);
		}
		if (!ServiceHelper.checkUsername(username)) {
			throw new IllegalArgumentException("The parameter username contains illegal character," +
					" a-zA-Z_0-9.、-,@。 is legally, and start with alphabet or number. Input is " + username);
		}
	}
	
	public static void checkPassword(String password) {
		if (null == password || password.trim().length() == 0) {
			throw new IllegalArgumentException("password must not be empty");
		}
		
		byte[] passwordByte = password.getBytes();
		if (passwordByte.length < 4 || passwordByte.length > 128) {
			throw new IllegalArgumentException("The length of password must between 4 and 128 bytes. Input is " + password);
		}
		
	}
	
	public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }
	
	public static boolean isTrimedEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }
	
	public static boolean isLineBroken(String s) {
		if (s.contains("\n") || s.contains("\r")) {
			return true;
		}
		return false;
	}
	
}
