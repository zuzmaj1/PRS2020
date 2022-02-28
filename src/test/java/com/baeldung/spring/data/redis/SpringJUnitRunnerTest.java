package com.baeldung.spring.data.redis;

import executor.MyExecutor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.embedded.RedisServerBuilder;

import com.baeldung.spring.data.redis.config.RedisConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class SpringJUnitRunnerTest {

    private static redis.embedded.RedisServer redisServer;

    @Autowired
    ParallelProcessTester tester;

    @BeforeClass
    public static void startRedisServer() {
        redisServer = new RedisServerBuilder().port(7999).setting("maxmemory 256M").build();
        redisServer.start();
    }

    @AfterClass
    public static void stopRedisServer() {
        redisServer.stop();
    }

    @Test(timeout = 30000)
    public void testOnMessage() throws Exception {
        tester.testAllTaskFinish(10, new MyExecutor());
    }
}
