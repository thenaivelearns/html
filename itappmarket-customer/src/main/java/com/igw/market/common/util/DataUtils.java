package com.igw.market.common.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataUtils {
	
	private static final int SIZE = 6;
	private static final String SYMBOL = "*";

	public static String commonDisplay(String value) {
        if (null == value || "".equals(value)) {
            return value;
        }
        int len = value.length();
        int pamaone = len / 2;
        int pamatwo = pamaone - 1;
        int pamathree = len % 2;
        StringBuilder stringBuilder = new StringBuilder();
        if (len <= 2) {
            if (pamathree == 1) {
                return SYMBOL;
            }
            stringBuilder.append(SYMBOL);
            stringBuilder.append(value.charAt(len - 1));
        } else {
            if (pamatwo <= 0) {
                stringBuilder.append(value.substring(0, 1));
                stringBuilder.append(SYMBOL);
                stringBuilder.append(value.substring(len - 1, len));

            } else if (pamatwo >= SIZE / 2 && SIZE + 1 != len) {
                int pamafive = (len - SIZE) / 2;
                stringBuilder.append(value.substring(0, pamafive));
                for (int i = 0; i < SIZE; i++) {
                    stringBuilder.append(SYMBOL);
                }
                if ((pamathree == 0 && SIZE / 2 == 0) || (pamathree != 0 && SIZE % 2 != 0)) {
                    stringBuilder.append(value.substring(len - pamafive, len));
                } else {
                    stringBuilder.append(value.substring(len - (pamafive + 1), len));
                }
            } else {
                int pamafour = len - 2;
                stringBuilder.append(value.substring(0, 1));
                for (int i = 0; i < pamafour; i++) {
                    stringBuilder.append(SYMBOL);
                }
                stringBuilder.append(value.substring(len - 1, len));
            }
        }
        return stringBuilder.toString();
    }
	
	/**
	 * 字符转2位小数数字 千分位显示
	 */
	public static String toBigDecimal(Object d) {
		if(d == null) {
			return "0";
		}
		 //保留两位小数并展示千分位符
        DecimalFormat df = new DecimalFormat("##,##0.00");
        return df.format(d);
	}
	
	/**
	 * 字符转4位小数数字 千分位显示
	 */
	public static String toBigDecimal2(Object d) {
		if(d == null) {
			return "0";
		}
		 //保留两位小数并展示千分位符
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(d);
	}
	

    /**
     * 	求差集
     * 
     * @param m
     * @param n
     * @return
     */
    private static String[] getChaj(String[] m, String[] n)
    {
        // 将较长的数组转换为set
        Set<String> set = new HashSet<String>(Arrays.asList(m.length > n.length ? m : n));

        // 遍历较短的数组，实现最少循环
        for (String i : m.length > n.length ? n : m)
        {
            // 若是集合里有相同的就删掉，若是没有就将值添加到集合
            if (set.contains(i))
            {
                set.remove(i);
            } else
            {
                set.add(i);
            }
        }

        String[] arr = {};
        return set.toArray(arr);
    }
    
}
