package com.igw.market.common.util;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;

/**
 * 字符串处理工具类
 * 
 * @author zengchao
 *
 */
public class MyStringUtil {

	//日志
	private static final Logger log = Logger.getLogger(StringUtil.class);
    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumberic(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断一个字符串是否为字母
     * @param fstrData
     * @return
     */
    public static boolean checkLetter(String fstrData) {
        char c = fstrData.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为汉字
     * @param str
     * @return
     */
    public static boolean vd(String str) {

        char[] chars = str.toCharArray();
        boolean isUtf8 = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;

                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
                    isUtf8 = true;
                    break;
                }
            }
        }
        return isUtf8;
    }
    
    /**
     * 根据正则表达式判断字符是否为汉字
     */
	 public static boolean isContainChinese( String str) {
	     String regex = "[\u4e00-\u9fa5]";   //汉字的Unicode取值范围
	     Pattern pattern = Pattern.compile(regex);
	     Matcher match = pattern.matcher(str);
	     return match.find();
	 }
 
 	/**
	 * 判断字符串是数字类型(支持小数和整数)
	 * 
	 * @author zengchao
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){	
		boolean strResult = str.matches("-?[0-9]+.?[0-9]*");		
		return strResult;
	}
	
	/**
	 * 字符串类型小数转百分数
	 * 
	 * @author zengchao
	 * @param doubleStr
	 * @param integerDigits 小数点前保留几位
	 * @param fractionDigits 小数点后保留几位
	 * @return
	 */
	public static String getPercentFormat(String doubleStr,int integerDigits,int fractionDigits){
		if(MyStringUtil.isNumeric(doubleStr)){
			Double d = Double.valueOf(doubleStr);
			NumberFormat num = NumberFormat.getPercentInstance();
			num.setMaximumIntegerDigits(integerDigits);
			num.setMinimumFractionDigits(fractionDigits);
			String percent = num.format(d);		
			return percent;	
		}else{
			return "0%";
		}
		
	}

	public static boolean isjson(String string) {
		try {
			JSONObject jsonStr = JSONObject.parseObject(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断多个字段是否为空 若有空值存在，则返回的字符串不为空。
	 * 
	 * @param data
	 * @return
	 */
	public static String paramAllEmpty(String... data) {
		String desc = "";
		for (int i = 0; i < data.length; i = i + 2) {
			if (StringUtil.isEmpty(data[i])) {
				log.error(data[i + 1] + "字段为空");
				if(StringUtil.isEmpty(desc)) {
					desc = data[i + 1] + "字段为空";
				}else {
					desc = desc + "," + data[i + 1] + "字段为空";
				}
			}
		}
		return desc;
	}
}

