package com.client.service.impl;

import com.client.service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UsersServiceImplTest {


    @Autowired
    private UsersService usersService;

    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        long result = usersService.usersRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu";
        result = usersService.usersRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        userPassword = "123456";
        result = usersService.usersRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu     pi";
        userPassword = "12345678";
        result = usersService.usersRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        checkPassword = "12345678";
        result = usersService.usersRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "dogYupi";
        userPassword = "12345678";
        result = usersService.usersRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi1";
        result = usersService.usersRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result > 0);

    }

}