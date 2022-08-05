package com.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.client.common.BaseResponse;
import com.client.common.ErrorCode;
import com.client.common.ResultUtils;
import com.client.exception.BusinessException;
import com.client.model.domain.Users;
import com.client.model.domain.userLoginRequest;
import com.client.model.domain.userRegisterRequest;
import com.client.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.client.constant.userConstant.ADMIN_ROLE;
import static com.client.constant.userConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @ClassName: UserController
 * @author: mafangnian
 * @date: 2022/7/31 12:07
 * @Blog: null
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UsersService usersService;


    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        //鉴权
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        Users user = (Users) userObj;
        if (user == null || user.getUserRole() != ADMIN_ROLE) {
            return false;
        }
        return true;
    }

    @GetMapping("/current")
    public  BaseResponse<Users> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        Users currentUser = (Users) userObj;
        if (currentUser == null){
            return null;
        }
        long userId = currentUser.getId();
        // todo 用户校验 用户是否合法
        Users user = usersService.getById(userId);
        Users safetyUser = usersService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody userRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long result = usersService.usersRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);

    }

    @PostMapping("/login")
    public BaseResponse<Users> login(@RequestBody userLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Users users = usersService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(users);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        int result = usersService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/search")
    public BaseResponse<List<Users>> searchUsers(String username, HttpServletRequest request) {

        //鉴权
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username",username);
        }
        List<Users> usersList = usersService.list(queryWrapper);

        List<Users> list = usersList.stream().map(users -> usersService.getSafetyUser(users)).collect(Collectors.toList());
        return ResultUtils.success(list);

    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {

        //鉴权
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = usersService.removeById(id);
        return ResultUtils.success(b);
    }

}
