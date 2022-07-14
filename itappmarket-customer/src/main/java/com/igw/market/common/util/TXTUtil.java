package com.igw.market.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author zhaotd
 * @date 2021/12/28
 */
public class TXTUtil {

    private static final String NULL = "null";

    /**
     * @param jsonArr    数据集
     * @param fileName   新文件名
     * @param reportPath 导出路径
     * @return
     */
    public static boolean outputTXT(JSONArray jsonArr, String fileName, String reportPath) throws IOException {
        //文件夹不存在创建
        File destFile = new File(reportPath);
        if(!destFile.exists()) {
            destFile.mkdirs();
        }
        //存储的目标文件
        File file = new File(reportPath + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
        ) {
            //遍历导出数据
            Iterator<JSONObject> iterator = jsonArr.iterator();
            while (iterator.hasNext()) {
                JSONObject jsonObject = iterator.next();
                //删除多余键值对
                jsonObject.discard("marketCode");
                jsonObject.discard("fileName");
                jsonObject.discard("fileDate");
                jsonObject.discard("validFlag");
                jsonObject.discard("createdUser");
                jsonObject.discard("createdDate");
                jsonObject.discard("updateUser");
                jsonObject.discard("updateDate");
                jsonObject.discard("flabel");
                //根据文件名称写对应的格式
                if (fileName.toUpperCase().startsWith("ZQGH")) {
                    writerZqghTxt(bw, jsonObject);
                }
                bw.write("\r\n");
            }
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void writerZqghTxt(BufferedWriter bw, JSONObject jsonObject) throws IOException {

        StringBuilder sb = new StringBuilder();

        //证券账户 C13
        getAfterStringTxt(sb, jsonObject.getString("ghzqzh"), 13);
        //成交日期 C8
        getAfterStringTxt(sb, jsonObject.getString("ghcjrq"), 8);
        //成交编号 C16
        getAfterStringTxt(sb, jsonObject.getString("ghcjbh"), 16);
        //交易单元 C8
        getAfterStringTxt(sb, jsonObject.getString("ghjydy"), 8);
        //成交数量 N16(3)
        getFrontStringTxt(sb, jsonObject.getString("ghcjsl"), 16);
        //证券代码 C12
        getAfterStringTxt(sb, jsonObject.getString("ghzqdm"), 12);
        //申报时间 C6
        getAfterStringTxt(sb, jsonObject.getString("ghsbsj"), 6);
        //成交时间 C6
        getAfterStringTxt(sb, jsonObject.getString("ghcjsj"), 6);
        //成交价格 N16(5)
        getFrontStringTxt(sb, jsonObject.getString("ghcjjg"), 16);
        //成交金额 N19(5)
        getFrontStringTxt(sb, jsonObject.getString("ghcjje"), 19);
        //会员内部订单号 C10
        getAfterStringTxt(sb, jsonObject.getString("ghddh"), 10);
        //订单方向 C1
        getAfterStringTxt(sb, jsonObject.getString("ghddfx"), 1);
        //业务类型 C3
        getAfterStringTxt(sb, jsonObject.getString("ghywlx"), 3);
        //信用标签
        getAfterStringTxt(sb, jsonObject.getString("ghxybq"), 2);

        bw.write(sb.deleteCharAt(sb.length() - 1).toString());

    }

    private static void getAfterStringTxt(StringBuilder sb, String value, int num) {
        if (StringUtils.isBlank(value) || NULL.equals(value)) {
            for (int i = 0; i < num; i++) {
                sb.append(" ");
            }
        } else {
            sb.append(value);
            if (value.length() < num) {
                int i = num - value.length();
                for (int j = 0; j < i; j++) {
                    sb.append(" ");
                }
            }
        }
        sb.append("|");
    }

    private static void getFrontStringTxt(StringBuilder sb, String value, int num) {
        if (StringUtils.isBlank(value) || NULL.equals(value)) {
            for (int i = 0; i < num; i++) {
                sb.append(" ");
            }
        } else {
            if (value.length() < num) {
                int i = num - value.length();
                for (int j = 0; j < i; j++) {
                    sb.append(" ");
                }
            }
            sb.append(value);
        }
        sb.append("|");
    }
}
