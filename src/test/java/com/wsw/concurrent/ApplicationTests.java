package com.wsw.concurrent;

import com.wsw.concurrent.service.ReflexServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;

@SpringBootTest
class ApplicationTests {

    public boolean isTimeFormatCorrect(String time, String pattern) throws Exception {
        if (time == null || pattern == null) {
            return false;
        }

        boolean flag = true;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setLenient(false);
            format.parse(time);
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    @Test
    void contextLoads() {
        try {
            boolean result = isTimeFormatCorrect("2020-12-25 01:60", "yyyy-MM-dd HH:mm");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private ReflexServiceImpl reflexService;

    @Test
    void reflexTest() {
        String result = reflexService.toString("1", "2");
        System.out.println(result);
    }
}
