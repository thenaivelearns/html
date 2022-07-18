package com.igw.market.institutional.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.igw.market.common.util.HttpUtil;
import com.igw.market.common.util.ResultMessage;

import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class FileServerUtil {

    // 声明日志对象
    public static final Logger LOGGER = LoggerFactory.getLogger(FileServerUtil.class);
	/**
     * 	url下载文件
     * @param urlStr
     * @param savePath
     * @return
     */
    public static ResultMessage<Object> downLoadFromUrl(String urlStr, String savePath) {
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            // 获取下载的文件名
            String fileName = con.getHeaderField("Content-disposition");
            fileName = new String(fileName.getBytes("ISO-8859-1"),"GBK");
            fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("fileName=")+9), "UTF-8");
            InputStream inputStream = con.getInputStream();
            byte[] bufferOut = new byte[1024];
            int len = 0;
            bos = new ByteArrayOutputStream();
            while((len = inputStream.read(bufferOut)) != -1) {
                bos.write(bufferOut, 0, len);
            }
            byte[] getData = bos.toByteArray();
            // 文件保存地址
            File saveDir = new File(savePath);
            if(!saveDir.exists()) {
                saveDir.mkdirs();
            }
            File file = new File(saveDir+File.separator+fileName);
            fos = new FileOutputStream(file);
            fos.write(getData);
            return ResultMessage.ok(fileName);
        } catch (Exception e) {
            LOGGER.error("下载文件异常",e);
            return ResultMessage.fail(e.getMessage());
        } finally {
            if(bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    LOGGER.error("IO流异常",e);
                }
            }
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    LOGGER.error("IO流异常",e);
                }
            }
        }
    }
    
    
    /**
     * 	通过文件名称下载文件
     * @param fileUrl 文件服务器地址
     * @param fileName 文件名称
     * @param fileDate 上传时间
     * @param aggrPath 存放地址
     * @return
     */
    public static ResultMessage<Object> getFileServerList(String fileUrl,String fileName,String fileDate, String aggrPath) {
    	ResultMessage<Object> fileMsg = new ResultMessage<>();
    	try {
    		ResultMessage<Object> msg = HttpUtil.doGetHttp(fileUrl+"file_info/get_file_detail_by_fname/v1",
    				"fname=" +fileName+ 
    				"&fileUploadDate="+fileDate);
    		
    		if("Y".equals(msg.getMsgCode())) {
    			
    			JSONObject js=JSONObject.fromObject(msg.getData());
    			JSONArray ja = JSONArray.parseArray(js.get("obj")+"");
    			if(ja.size() > 0) {
    				//只获取最新一个文件
        			JSONObject json = JSONObject.fromObject(ja.get(0));
        			String loadUrl = fileUrl+"file_info/download/v1?fid=";
        			// 下载文件
        			fileMsg= downLoadFromUrl(loadUrl+json.getString("fid"),aggrPath);
    			}
    		}else {
    			ResultMessage.fail("文件下载失败.");
    		}
		} catch (Exception e) {
			ResultMessage.fail("文件下载异常.");
		}
    	return fileMsg;
    }
    
}
