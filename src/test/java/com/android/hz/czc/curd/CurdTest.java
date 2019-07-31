package com.android.hz.czc.curd;

import com.android.hz.czc.entity.User;
import com.android.hz.czc.entity.VersionMessage;
import com.android.hz.czc.mapper.UserMapper;
import com.android.hz.czc.service.VersionMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurdTest {

    @Resource
    private UserMapper mapper;
    @Autowired
    private VersionMessageService versionMessageService;

    @Test
    public void aInsert() {
        User user = new User();
        user.setUsername("小羊");
        user.setPassword("123456");
        user.setAlias("abc@mp.com");
        user.setIsDelete(0);
        Assert.assertTrue(mapper.insert(user) > 0);
        // 成功直接拿会写的 ID
        System.err.println("\n插入成功 ID 为：" + user.getId());
    }

    @Test
    public void aInsert1() {
        VersionMessage versionMessage = versionMessageService.getById(1);
        if ("1.01".equals(versionMessage.getVersion())) {
            versionMessage.setMessage("11");
            System.out.println(versionMessage);
        } else {
            versionMessage.setMessage("22");
            System.out.println(versionMessage);

        }

    }

    /*@Test
    public void bDelete() {
        Assert.assertTrue(mapper.deleteById(1080293765925015553L) > 0);
        Assert.assertTrue(mapper.delete(new QueryWrapper<User>()
                .lambda().eq(User::getUsername, "小羊2")) > 0);
    }*/


    /*@Test
    public void cUpdate() {
//        Assert.assertTrue(mapper.updateById(new User().setId(1080294084843134978L).setAlias("ab@c.c")) > 0);
        Assert.assertTrue(mapper.update(new User().setUsername("小羊311"),
                new UpdateWrapper<User>().lambda()
                        .set(User::getPassword, 123456)
                        .eq(User::getId, 1080294084843134978L)) > 0);
    }*/


   /* @Test
    public void dSelect() {
        Assert.assertEquals("ab@c.c", mapper.selectById(1080294084843134978L).getAlias());
        User user = mapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 1080294084843134978L));
        Assert.assertEquals("小羊311", user.getUsername());
        Assert.assertTrue("123456".equals(user.getPassword()));
    }*/
}
