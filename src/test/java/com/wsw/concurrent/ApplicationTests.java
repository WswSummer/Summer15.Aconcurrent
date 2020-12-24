package com.wsw.concurrent;

import com.wsw.concurrent.thread.MyThreadPool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
        MyThreadPool.fixedThreadPool();
    }

}
