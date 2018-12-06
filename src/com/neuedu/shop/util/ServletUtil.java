package com.neuedu.shop.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtil {
	private static void setEncoding(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		request.setCharacterEncoding("gbk");
		response.setContentType("text/html;charset=gbk");
	}
	public static String getPath(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		setEncoding(request, response);
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
		return path;
	}
	
	public static void forwardPage(String key, Object items, String pagePath, HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		request.setAttribute(key, items);
		request.getRequestDispatcher(pagePath).forward(request, response);
	}
	/**
	 * 对字符串进行MD5加密
	 * @param key  传入一个字符串
	 * @return	返回md5加密的结果
	 */
	public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
           e.printStackTrace();
           return null;
        }
    }

}
