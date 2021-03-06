package com.igw.market.systemInfo.domain;

import javax.validation.constraints.NotBlank;

/**
 * IgwUserDto传输对象
 *
 * @author chenzy1 陈志誉
 * <p>
 * Create at 2020/01/17
 * @since 1.0.0
 */
public class IgwUserDto {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "IgwUserDto [" +
                "userName=" + userName +
                ", password=" + password +
                ']';
    }
}
