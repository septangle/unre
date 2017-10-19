package com.unre.photo.util;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class Token {
	private static final String TOKEN_LIST_NAME = "tokenList";
	public static final String TOKEN_STRING_NAME = "token";

	@SuppressWarnings("rawtypes")
	private static ArrayList getTokenList(HttpSession session) {
		Object obj = session.getAttribute(TOKEN_LIST_NAME);
		if (obj != null) {
			return (ArrayList) obj;
		} else {
			ArrayList tokenList = new ArrayList();
			session.setAttribute(TOKEN_LIST_NAME, tokenList);
			return tokenList;
		}
	}

	@SuppressWarnings("unchecked")
	private static void saveTokenString(String tokenStr, HttpSession session) {
		@SuppressWarnings("rawtypes")
		ArrayList tokenList = getTokenList(session);
		tokenList.add(tokenStr);
		session.setAttribute(TOKEN_LIST_NAME, tokenList);
	}

	private static String generateTokenString() {
		return new Long(System.currentTimeMillis()).toString();
	}

	/** 
	* 生成一个token字符串，并在会话中保存该字符串，然后返回token字符串.token相当于令牌 
	* @param HttpSession session 
	* @return 用于执行特定事务的单个请求的token字符串。 
	*/

	public static String getTokenString(HttpSession session) {
		String tokenStr = generateTokenString();
		saveTokenString(tokenStr, session);
		return tokenStr;
	}

	/** 	
	* 检查token字符串是否有效。如果会话包含token字符串，返回true。否则返回false 
	* @param String tokenStr 
	* @param HttpSession session 
	* @return true: session 包含 tokenStr; false: session 为空或者 tokenStr 不在该session中
	*/

	public static boolean isTokenStringValid(String tokenStr, HttpSession session) {
		boolean valid = false;
		if (session != null) {
			@SuppressWarnings("rawtypes")
			ArrayList tokenList = getTokenList(session);
			if (tokenList.contains(tokenStr)) {
				valid = true;
				tokenList.remove(tokenStr);
			}
		}
		return valid;
	}
}