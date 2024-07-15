package com.blake.instance.ratelimit.service;

public interface RedisRateLimiterService {

    boolean acquire(String key, int limit, int ttl);

    long release(String key);
}
