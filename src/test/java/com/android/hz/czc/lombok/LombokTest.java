package com.android.hz.czc.lombok;

import com.android.hz.czc.entity.TMeter;
import com.android.hz.czc.entity.User;
import com.android.hz.czc.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LombokTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void UserEntityTestByLombok() {
        User user = new User();
        user.setUsername("用户1").setPassword("123456").setAlias("用户1").setTelephone("1234567890");

        User user1 = new User();
        user1.setUsername("abc").setPassword("123").setAlias("z").setTelephone("123456").setId(1L);

        System.out.println(user);
        System.out.println(user1);
        System.out.println(user == user1);
        System.out.println(user.equals(user1));
        TMeter meter = new TMeter();
    }
}
