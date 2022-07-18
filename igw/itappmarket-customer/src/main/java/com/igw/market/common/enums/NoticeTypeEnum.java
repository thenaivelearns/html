package com.igw.market.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaotd
 * @date 2021/9/23
 */
public enum NoticeTypeEnum {

    //基金发售公告（发行时间）
    NOTICE_1("FA040010","基金发售公告（发行时间）"),
    //提前募集结束公告
    NOTICE_2("FC090010","提前募集结束公告"),
    //合同生效公告
    NOTICE_3("FA050010","合同生效公告"),
    //基金开放日常申购赎回等交易公告
    NOTICE_4("FC190090","基金开放日常申购赎回等交易公告"),
    //定期开放基金开放时间公告
    NOTICE_5("FC200030","定期开放基金开放时间公告"),
    //基金暂停交易公告
    NOTICE_6("FC190100","基金暂停交易公告"),
    //基金分红公告
    NOTICE_7("FC130050","基金分红公告"),
    //基金清盘/清算公告（剩余财产分配公告)
    NOTICE_8("FC030030","基金清盘/清算公告（剩余财产分配公告)"),
    //持有人大会提示性公告
    NOTICE_9("FC020020","持有人大会提示性公告"),
    //基金转型公告
    NOTICE_10("FC210090","基金转型公告"),
    //限制/恢复**（金额）申购公告
    NOTICE_11("FC190110","限制/恢复**（金额）申购公告");

    String value;
    String name;

    NoticeTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static List<Map<String,String>> getNoticeList(){
        List<Map<String,String>> list = new ArrayList<>();
        for (NoticeTypeEnum noticeTypeEnum : NoticeTypeEnum.values()) {
            Map<String,String> map = new HashMap<>();
            map.put("noticeCode",noticeTypeEnum.value);
            map.put("noticeName",noticeTypeEnum.name);
            list.add(map);
        }
        return list;
    }
}
