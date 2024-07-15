package com.blake.instance.ratelimit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

@Slf4j
@Service
public class RedisRateLimiterServiceImpl implements RedisRateLimiterService {

    @Autowired
    private JedisPool jedisPool;

    private static final String LUA_LIMIT_SCRIPT =
            "local key=KEYS[1] " +
            "local limit = tonumber(ARGV[1]) " +
            "local ttl = tonumber(ARGV[2]) " +
            "local current_count = tonumber(redis.call('GET', key) or 0) " +
            "if current_count < limit then " +
            "    redis.call('INCR', key) " +
            "    if current_count == 0 then " +
            "        redis.call('EXPIRE', key, ttl) " +
            "    end " +
            "    return 1 " +
            "else " +
            "    return 0 " +
            "end";

    private static final String LUA_RELEASE_SCRIPT = "" +
            "local key = KEYS[1]" +
            "local current_count = tonumber(redis.call('DECR', key)) " +
            "if current_count <= 0 then " +
            "    redis.call('DEL', key)" +
            "end " +
            "return current_count";

    @Override
    public boolean acquire(String key, int limit, int ttl) {
        try (Jedis jedis = jedisPool.getResource()) {
            Object result = jedis.eval(LUA_LIMIT_SCRIPT, 1, key, String.valueOf(limit), String.valueOf(ttl));
            return result != null && Integer.parseInt(result.toString()) == 1;
        } catch (JedisException e) {
            throw e;
        }
    }

    @Override
    public long release(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            Object result = jedis.eval(LUA_RELEASE_SCRIPT, 1, key);
            return result != null ? Long.parseLong(result.toString()) : -1;
        } catch (JedisException e) {
            throw e;
        }
    }
}
