package com.blake.instance.ratelimit;

import com.blake.instance.FunctionInstanceApplication;
import com.blake.instance.ratelimit.service.RedisRateLimiterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootTest(classes = FunctionInstanceApplication.class)
public class RateLimitTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RedisRateLimiterService redisRateLimiterService;

    @Test
    public void testRedisRateLimiter() throws InterruptedException {
        String key = "concurrent_limit:resource:123";
        int limit = 10;
        int ttl = 60;

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                if (this.redisRateLimiterService.acquire(key, limit, ttl)) {
                    log.info("Thread={} Request allowed", Thread.currentThread().getId());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        long remaining = this.redisRateLimiterService.release(key);
                        log.info("Remaining count: {}", remaining);
                    }
                } else {
                    log.error("Thread={} Request limited", Thread.currentThread().getId());
                }
            });
        }

        Thread.sleep(50000);

        executorService.shutdown();
    }
}
