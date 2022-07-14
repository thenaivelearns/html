package com.igw.market.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * http请求工具
 */
public class HttpsUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    
    /**
   	 * 	发送post请求
     * @param json
     * @param URL
     * @return
     */
    public static String sendPost(JSONObject json,String URL) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(URL);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        StringBuffer result = new StringBuffer();
        try {
            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);
            // 发送请求
            logger.info("发送POST请求");
            HttpResponse httpResponse = client.execute(post);
            logger.info("发送POST请求:完成");
            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
            	result.append(line);
            }
            inStream.close();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功，做相应处理");
            } else {
                System.out.println("请求服务端失败");
            }
        } catch (Exception e) {
            logger.error("请求异常："+e.getMessage());
            throw new RuntimeException(e);
        }
        return result.toString();
    }
    
    
    public static JSONArray getInterfaceData(JSONObject json,String URL) {
    	JSONArray returnArr=new JSONArray();
    	try {
    		String result = sendPost(json,URL);
    		returnArr = JSONArray.fromObject(result);
		} catch (Exception e) {
			
		}
    	return returnArr;
    }
    
    
    public static void main(String[] args) {
    	JSONObject json =new JSONObject();
    	json.put("fileDate","20210407");
    	json.put("zqzh","B883683131");
        String result = sendPost(json,"http://10.86.130.231:8080/ItApp-Invest-CommonData/file2_db/get_jsmx02_sh_list/v1");
        JSONArray jsonArr = JSONArray.fromObject(result);
        Boolean is = DBFUtil.outputDBF(jsonArr, "jsmx02_jsnq3.dbf", "E:\\ceshi\\igwzd\\jsmx02.dbf" , "e:/liwen1/20210407/");

	}
}