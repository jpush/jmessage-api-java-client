package cn.jmessage.api.utils;

import cn.jpush.api.common.ServiceHelper;

public class StringUtils {

	public static boolean isUsernameValid(String username) {
		if ( null == username || username.trim().length() == 0) {
			return false;
		}
		if (username.contains("\n") || username.contains("\r") || username.contains("\t")) {
			return false;
		}
		byte[] usernameByte = username.getBytes();
		if (usernameByte.length < 4 || usernameByte.length > 128) {
			return false;
		}
		return ServiceHelper.checkUsername(username);
	}
	
	public static boolean isPasswordValid(String password) {
		if (null == password || password.trim().length() == 0) {
			return false;
		}
		byte[] passwordByte = password.getBytes();
		if (passwordByte.length < 4 || passwordByte.length > 128) {
			return false;
		}
		
		return true;
	}
	
}
