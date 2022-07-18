package com.igw.market.push.enums;

import com.igw.market.query.domain.dto.UserFund;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: TestUserNum
 * Description： TODO
 * Author: zhubengang
 * Date: Created in 2021/9/17 14:11
 * Version: 1.0.0
 */
public enum TestUserEnum {
    //生成测试用户
    TEST_USER_1("ofg0Kj-LlyOoRh4v8jOCofzEu09Y","刘超"),
    TEST_USER_2("ofg0Kj0cM21X-zRRZ5KlRYQhGbF4","肖希希"),
    TEST_USER_3("ofg0Kj4ZGFVhemk5HQKL-1CBiTOk","黄舒芸"),
    TEST_USER_8("ofg0KjxkcwOBOq0vCCjjUZJeZllc","李文"),
    //测试 测试用户
    TEST_USER_4("o9SnItzQLJI4kjlU8u5jLA4QZ5-M","刘超"),
    TEST_USER_5("o9SnIt9z8kDMQerUYlT71ghEy01E","黄舒芸"),
    TEST_USER_6("o9SnIt1yyxbjcUdTlMa4ufnW4EdI","肖希希"),
    TEST_USER_7("o9SnIt3nT9jO0bsuQqMOgVxyjnKY","曾爱媚");

    String value;
    String name;

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    TestUserEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static List<UserFund> toList(List<String> openIds) {
        List<UserFund> enumsList = new ArrayList<>();
        for (TestUserEnum item : TestUserEnum.values()) {
            if (openIds.contains(item.getValue())){
                UserFund userFund = new UserFund();
                userFund.setOpenId(item.getValue());
                userFund.setUserName(item.getName());
                enumsList.add(userFund);
            }
        }
        return enumsList;
    }

}
