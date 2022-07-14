package com.igw.market.common.util;


import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author os-zhubg
 * @Description
 * @Date 2021/8/23 17:56
 * @Param
 * @return
 **/
@Slf4j
public class CommonWechatUtil {



    /**
     * @param wui 用户dto
     * @param map 发送参数
     * 模板一：
    标题：分红信息通知
    -------------------------
    关键字一：产品名称
    关键字二：分红方式
    概要
    -------------------------
    详情
     * @return
     */
    public static String dividendsSend(Map<String, String> map) {
        String start  = map.get("start")==null ?"": map.get("start").replace("[客户名]",map.get("userName"));

        String jsonBuffer = "{\"touser\":\"" + map.get("openId") + "\", \"template_id\":\"" + map.get("tempplateId");
        if(!"0".equals(map.get("url"))){
            jsonBuffer += "\",\"url\":\"" + map.get("url") ;
        }
        jsonBuffer +=  "\",\"data\":" + "{\"first\": {\"value\":\"" + start + "\",\"color\":\"#173177\" }, "
                + "\"publicproductname\": {\"value\":\"" + map.get("fundName") + "\", \"color\":\"#173177\"},"
                + "\"type\": {\"value\":\"" + map.get("shareType") + "\", \"color\":\"#173177\"},"
                + "\"remark\":{\"value\":\"" + map.get("end") + "\",\"color\":\"#173177\"}}}";
        return jsonBuffer;
    }

    /**
     * 业务提醒
     * @param map
     * @return
     */
    public static String businessSend(Map<String, String> map) {
        String start = map.get("start")==null ?"": map.get("start").replace("[客户名]",map.get("userName"));

        String jsonBuffer = "{\"touser\":\"" + map.get("openId") + "\", \"template_id\":\"" + map.get("tempplateId");

        if(!"0".equals(map.get("url"))){
            jsonBuffer += "\",\"url\":\"" + map.get("url") ;
        }
        jsonBuffer += "\",\"data\":" + "{\"first\": {\"value\":\"" + start + "\",\"color\":\"#173177\" }, "
                + "\"keyword1\": {\"value\":\"" + map.get("userName") + "\", \"color\":\"#173177\"},"
                + "\"keyword2\": {\"value\":\"" + map.get("message") + "\", \"color\":\"#173177\"},"
                + "\"remark\":{\"value\":\"" + map.get("end") + "\",\"color\":\"#173177\"}}}";
        return jsonBuffer;
    }


    public static void main(String[] args) throws FileNotFoundException {
    	
//    	Scanner in = new Scanner(new File("E:/liwen/test.txt"),"GBK");
//		while (in.hasNextLine()) {
//			String str = in.nextLine();
//			
//			String openId = str.split("-")[0];
//			String userName = str.split("-")[1];
//			System.out.println(openId +" == "+userName);
//			
//			//获取待发送消息
//	        Map<String, String> userMap = new HashMap<>();
//	        //客户信息
//	        userMap.put("userName", userName);
//	        userMap.put("openId", openId);
//	        userMap.put("tempplateId","YG-07lRfVMdpDSVkmZGQs2EKuxcv02jkQqfbHH3So6s");
//	        userMap.put("message", "暂停交易");
//	        userMap.put("url", "http://www.igwfmc.com/main/zxzx/jjgg/lsgg/a/20210927/1049354.html");
//	        userMap.put("start", "部分基金因非港股通交易日暂停申购、赎回、转换与定期定额投资等业务的提醒");
//	        userMap.put("end", "因2021年9月29日至9月30日非港股通交易日（国庆节），为保障基金平稳运作，维护基金份额持有人利益，我司旗下部分基金2021年9月29日起暂停办理申购、赎回、转换与定期定额投资等业务（具体业务类型和开放状态以各基金实际情况为准），并自2021年 10月8日起恢复办理上述业务。点详情查阅公告，基金投资需谨慎");	        
//	     	//待推送消息
//	        String sendStr = CommonWechatUtil.businessSend(userMap);
//	        JSONObject jsonObject = JSONObject.fromObject(sendStr);
//	        
//	        String result = HttpsUtil.sendPost(jsonObject, "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=49_JHR8QefrCSpWqBPD_ioCJzvhMlkMQ8UZZ8pfTfedo8Lyj2rTOky3qnLn1aWbwgBims7p_glKEQCh1C77V0u5GG33VdW2YuaJKPX-mEPxgIThVJeFbIO6rBjm8rCJcmdOji26fnj5lTQ-jvZ7LUHfCEATCP");
//	        System.out.println(result);
//		}

    }

}


