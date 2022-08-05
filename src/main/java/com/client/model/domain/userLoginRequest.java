package com.client.model.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录校验
 * @ClassName: userRegisterRequest
 * @author: mafangnian
 * @date: 2022/7/31 12:34
 * @Blog: null
 */
@Data
public class userLoginRequest implements Serializable {

    /**
     * 类的序列话ID
     */
    private static final long serialVersionUID = 7391845217012519734L;
    // 账号
    private String userAccount;
    // 账号密码
    private String userPassword;
}
