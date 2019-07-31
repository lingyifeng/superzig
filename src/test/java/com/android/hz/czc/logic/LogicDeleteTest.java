package com.android.hz.czc.logic;

import com.android.hz.czc.entity.User;
import com.android.hz.czc.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicDeleteTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void logicdelete() {
        int i = userMapper.deleteById(1L);
        System.out.println("i:" + i);
        List<User> user = userMapper.selectList(null);
        user.forEach(System.out::println);
    }

}
