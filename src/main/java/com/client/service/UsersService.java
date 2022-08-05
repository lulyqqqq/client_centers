package com.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.client.model.domain.Users;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 29769
 * @description 针对表【users】的数据库操作Service
 * @createDate 2022-07-28 23:30:22
 */
public interface UsersService extends IService<Users> {


    /**
     * 用户注册
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户ID
     */
    long usersRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      获得session数据
     * @return 脱敏后的用户信息
     */
    Users userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param user
     * @return
     */
    Users getSafetyUser(Users user);

    int userLogout(HttpServletRequest request);
}

