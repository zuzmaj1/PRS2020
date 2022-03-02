package com.baeldung.spring.data.redis;

import executor.MyExecutor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;
import redis.embedded.RedisServerBuilder;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(webEnvironment = org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringRedisApplication.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class SpringBootTest {

    private static redis.embedded.RedisServer redisServer;

    @Autowired
    ParallelProcessTester tester;

    @BeforeClass
    public static void startRedisServer() {
        redisServer = new RedisServerBuilder().port(8744).setting("maxmemory 256M").build();
        redisServer.start();
    }

    @AfterClass
    public static void stopRedisServer() {
        redisServer.stop();
    }

    @Test
    public void testOnMessage() throws Exception {
        tester.testAllTaskFinish(50, new MyExecutor());
    }
}
