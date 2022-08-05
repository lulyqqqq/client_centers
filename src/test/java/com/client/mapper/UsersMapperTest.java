package com.client.mapper;

import com.client.model.domain.Users;
import com.client.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UsersMapperTest {

    @Autowired
    private UsersService usersService;

    @Test
    void testInsert() {
        Users users = new Users();
        users.setUsername("02");
        users.setUserAccount("123");
        users.setUserPassword("nian0209");
        users.setAvatar("https://lulyqqqq.github.io/Gallery/leimu/index/02.png");
        users.setGender(0);
        users.setPhone("13129160209");
        users.setEmail("131@qq.com");
        boolean save = usersService.save(users);
        System.out.println(save + " " + users.getId());
        assertTrue(save);
    }
}