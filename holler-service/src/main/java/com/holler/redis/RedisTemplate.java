package com.holler.redis;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class RedisTemplate extends RedisBase{
	//private static Logger log = LoggerFactory.getLogger(RedisTemplate.class);
	public <R> R execute(RedisCallback<R> callback){
		Jedis jedis = this.jedisPool.getResource();
		try{
			return callback.doInRedis(jedis);
		} finally{
			//log.debug("Releasing resource");
			this.jedisPool.returnResource(jedis);
		}
	}
}
