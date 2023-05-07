package com.elmiriyounes.crud.Utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtil {
	public static boolean matchPasswords(String password1, String password2){
		return password1.equals(password2);
	}

	public static boolean isValidEmail(String email){
		String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
