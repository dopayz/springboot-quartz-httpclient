package com.yudu.face.myTest;

import com.yudu.face.model.InspectInfo;
import com.yudu.face.repository.InspectInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {

    @Autowired
    private InspectInfoRepository inspectInfoRepository;

    @Test
    public void testAdd(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        InspectInfo isp = new InspectInfo();
        isp.setId(UUID.randomUUID().toString());
        isp.setUserName("ccc");
        isp.setUserInfo("1");
        isp.setTimes(sdf.format(new Date()));
        isp.setSortTime(new Date());
        isp.setCountTime(new Date());
        isp.setYear(cal.get(Calendar.YEAR));
        isp.setMonth(cal.get(Calendar.MONTH)+1);
        isp.setDay(cal.get(Calendar.DATE));
        isp.setHour(cal.get(Calendar.HOUR_OF_DAY));
        isp.setMinute(cal.get(Calendar.MINUTE));
        isp.setSecond(cal.get(Calendar.SECOND));
        isp.setMip("用户");
        inspectInfoRepository.insert(isp);
    }

    @Test
    public void testSelect(){
        InspectInfo isp = inspectInfoRepository.getLastRecord();
    }
}
