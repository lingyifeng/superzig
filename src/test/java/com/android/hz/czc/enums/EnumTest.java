package com.android.hz.czc.enums;

import com.android.hz.czc.entity.TMeter;
import com.android.hz.czc.mapper.TMeterMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnumTest {

    @Autowired
    private TMeterMapper meterMapper;

    @Test
    public void SizeEnumTest() {
        TMeter meter = new TMeter();
        meter.setMeterName("111").setMeterRange("fanwei").setMeterSize(SizeEnum.BIG).setMeterType("3").setDefNum(20);
        Assert.assertEquals(1, meterMapper.insert(meter));
        Assert.assertNotNull(meter.getId());
        System.out.println(meterMapper.selectById(meter.getId()));
    }

}
