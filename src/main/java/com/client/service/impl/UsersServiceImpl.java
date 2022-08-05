package com.client.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.client.common.ErrorCode;
import com.client.exception.BusinessException;
import com.client.mapper.UsersMapper;
import com.client.model.domain.Users;
import com.client.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.client.constant.userConstant.USER_LOGIN_STATE;

/**
 * 用户中心实现类
 *
 * @author 02
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2022-07-28 23:30:22
 */
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {


    /**
     * 盐值  混淆密码  加密
     */
    private static final String salt = "yupi";

    @Resource
    private UsersMapper usersMapper;

    /**
     * 用户注册
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 用户id
     */
    @Override
    public long usersRegister(String userAccount, String userPassword, String checkPassword) {

        //1.校验注册
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword)) {
            // todo 修改为自定义异常
            throw new BusinessException(ErrorCode.NULL_ERROR,"参数为空");

        }
        if (userAccount.length() < 4) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }


        // .*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*
        //账号不能包含特殊字符
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*()——+|{}‘；：”“’。， 、？]";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }

        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码校验不匹配");
        }

        //账号不能重复
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = usersMapper.selectCount(queryWrapper);
        if (count > 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
            return -1;
        }

        //2.对代码进行加密
        String newPassword = DigestUtils.md5DigestAsHex((salt + userPassword).getBytes());
        //3.插入数据
        Users users = new Users();
        users.setUserAccount(userAccount);
        users.setUserPassword(newPassword);
        boolean result = this.save(users);
        if (!result) {
            return -1;
        }
        return users.getId();
    }

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      获得session数据
     * @return 用户基本信息
     */
    @Override
    public Users userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        //1.校验登录
        if (StringUtils.isAllBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }


        // .*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*
        //账号不能包含特殊字符
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*()——+|{}‘；：”“’。， 、？]";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户包含特殊字符");
        }

        //2.对代码进行加密
        String newPassword = DigestUtils.md5DigestAsHex((salt + userPassword).getBytes());
        //查询用户是否存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", newPassword);
        Users user = usersMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            log.info("user login failed;userAccount cannot match userPassword!");
            throw new BusinessException(ErrorCode.NO_LOGIN,"用户不存在");
        }

        //3.用户脱敏
        Users safetyUser = getSafetyUser(user);

        //4.记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);


        return safetyUser;
    }


    /**
     * 用户脱敏
     *
     * @param user
     * @return
     */
    public Users getSafetyUser(Users user) {

        if (user == null){
            throw new BusinessException(ErrorCode.NO_LOGIN,"用户不存在");
        }
        Users safetyUser = new Users();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatar(user.getAvatar());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        return safetyUser;
    }

    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        //移除用户登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

}




