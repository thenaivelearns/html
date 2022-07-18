package com.igw.market.common.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * 汉语转拼音
 * 
 * @author
 *
 */
public class PinYinUtil {

	// 日志
	private static final Logger log = Logger.getLogger(PinYinUtil.class);


	/**
	 * 提取第一个汉子/单词的首字母(大写)
	 * @param str
	 * @param flag
	 * flag=true 姓氏多音字
	 * flag=flag 不考虑姓氏多音字
	 * @return
	 */
	public static String getFirstHeadWordChar(String str,Boolean flag) {
		if (isNull(str)) {
			return "";
		}
		if(flag) {
			//姓名多音字替换
			str=diffPro(str,flag);
		}
		String convert = "";
		char word = str.charAt(0);
		// 提取汉字的首字母
		String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
		if (pinyinArray != null) {
			convert += pinyinArray[0].charAt(0);
		} else {
			convert += word;
		}

		convert = string2AllTrim(convert);
		return convert.toUpperCase();
	}

	/**
	 * 提取每个汉字的首字母(大写)
	 * 
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		if (isNull(str)) {
			return "";
		}
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}

		convert = string2AllTrim(convert);
		return convert.toUpperCase();
	}

	/***
	 * 将汉字转成拼音(取首字母或全拼)
	 * 
	 * @param hanzi
	 * @param full
	 *            是否全拼
	 * @return
	 */
	public static String convertHanzi2Pinyin(String hanzi, boolean full) {
		/***
		 * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言 ^[\u4E00-\u9FFF]+$ 匹配简体和繁体 ^[\u4E00-\u9FA5]+$
		 * 匹配简体
		 */
		String regExp = "^[\u4E00-\u9FFF]+$";
		StringBuffer sb = new StringBuffer();
		if (hanzi == null || "".equals(hanzi.trim())) {
			return "";
		}
		String pinyin = "";
		for (int i = 0; i < hanzi.length(); i++) {
			char unit = hanzi.charAt(i);
			if (match(String.valueOf(unit), regExp))// 是汉字，则转拼音
			{
				pinyin = convertSingleHanzi2Pinyin(unit);
				if (full) {
					sb.append(pinyin);
				} else {
					sb.append(pinyin.charAt(0));
				}
			} else {
				sb.append(unit);
			}
		}
		return sb.toString();
	}

	/***
	 * 将单个汉字转成拼音
	 * 
	 * @param hanzi
	 * @return
	 */
	private static String convertSingleHanzi2Pinyin(char hanzi) {
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		String[] res;
		StringBuffer sb = new StringBuffer();
		try {
			res = PinyinHelper.toHanyuPinyinStringArray(hanzi, outputFormat);
			sb.append(res[0]);// 对于多音字，只用第一个拼音
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return sb.toString();
	}

	/***
	 * @param str
	 *            源字符串
	 * @param regex
	 *            正则表达式
	 * @return 是否匹配
	 */
	public static boolean match(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}

	/*
	 * 判断字符串是否为空
	 */

	public static boolean isNull(Object strData) {
		if (strData == null || String.valueOf(strData).trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 去掉字符串包含的所有空格
	 * 
	 * @param value
	 * @return
	 */
	public static String string2AllTrim(String value) {
		if (isNull(value)) {
			return "";
		}
		return value.trim().replace(" ", "");
	}

	/**
	 * 判断a字符是否包含b字符， 包含简拼和全拼 忽略大小写的字母类型
	 * 
	 * @param strData
	 * @return
	 */
	public static boolean checkContains(String a, String b) {
		// 判断简拼
		if (PinYinUtil.getPinYinHeadChar(a).toUpperCase().contains(b.toUpperCase())) {
			return true;
		}
		// 判断全拼
		if (PinYinUtil.convertHanzi2Pinyin(a, true).toUpperCase().contains(b.toUpperCase())) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		
		System.out.println(getFirstHeadWordChar("曾飞",true));

	}

	/**
	 * 与pinyin.properties中多音字的姓名对比
	 * 
	 * @param inputString
	 *            中文姓名
	 * @return 百家姓的拼音读法 flag=true 获取姓氏，简拼 flag=false 获取姓氏，全拼
	 */
	private static String diffPro(String inputString, Boolean flag) {
		Properties properties = new Properties();
		try {
			InputStream flowInputStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("piyin.properties");
			// InputStreamReader flowInputStream =new
			// InputStreamReader(PinYinUtil.class.getResourceAsStream("/piyin.properties"),"utf-8");
			InputStreamReader isr = new InputStreamReader(flowInputStream, "utf8");
			properties.load(isr);
			isr.close();
		} catch (Exception e) {
			log.error("百家姓拼音===获取piyin.properties失败", e);
			return inputString;
		}
		Set keyValue = properties.keySet();
		for (Iterator it = keyValue.iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (inputString.substring(0, key.length()).equals(key)) {
				String value = inputString.substring(key.length(), inputString.length());
				if (flag) {
					// 简拼
					return properties.getProperty(key).substring(0, 1) + value;
				} else {
					// 全拼
					return properties.getProperty(key) + value;
				}
			}
		}
		return inputString;
	}

}
