package com.client.model.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册页面需要介绍的JSON数据类型
 * 前端传输的数据参数
 * @ClassName: userRegisterRequest
 * @author: mafangnian
 * @date: 2022/7/31 12:34
 * @Blog: null
 */
@Data
public class userRegisterRequest implements Serializable {

    /**
     * 类的序列话ID
     */
    private static final long serialVersionUID = 7391845217012519734L;
    // 账号
    private String userAccount;
    // 账号密码
    private String userPassword;
    // 校验密码
    private String checkPassword;
}
