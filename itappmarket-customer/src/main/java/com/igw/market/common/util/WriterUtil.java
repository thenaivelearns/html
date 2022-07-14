package com.igw.market.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.igw.market.common.domain.Messages;

import net.sf.json.JSONObject;

/**
 * 输出工具类
 * @author liwen
 *
 */
public class WriterUtil {
	
	public static void writerPrint(HttpServletRequest req , 
			HttpServletResponse response,Messages msg){
		PrintWriter write;
		try {
			response.setHeader("Set-Cookie","cookiename=cookievalue; path=/; Domain=domainvaule; Max-age=seconds; HttpOnly");
			write = response.getWriter();
			JSONObject jsonobject = JSONObject.fromObject(msg);
		    write.write(jsonobject.toString());
			write.flush();
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writerPrint(HttpServletRequest req , 
			HttpServletResponse response,Map<String, String> map){
		PrintWriter write;
		try {
			response.setHeader("Set-Cookie","cookiename=cookievalue; path=/; Domain=domainvaule; Max-age=seconds; HttpOnly");
			write = response.getWriter();
			JSONObject jsonobject = JSONObject.fromObject(map);
		    write.write(jsonobject.toString());
			write.flush();
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writerPrint(HttpServletRequest req , 
			HttpServletResponse response,String str){
		PrintWriter write;
		try {
			response.setHeader("Set-Cookie","cookiename=cookievalue; path=/; Domain=domainvaule; Max-age=seconds; HttpOnly");
			write = response.getWriter();
			JSONObject jsonobject = JSONObject.fromObject(str);
		    write.write(jsonobject.toString());
			write.flush();
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

