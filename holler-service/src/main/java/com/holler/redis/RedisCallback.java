package com.holler.redis;

import redis.clients.jedis.Jedis;

public interface RedisCallback<R> {
	public R doInRedis(Jedis jedis);
}
