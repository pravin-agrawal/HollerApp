package com.holler.redis;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author AbidK
 * 
 */
public class RedisBase implements DisposableBean {
	
	//private static Logger log = LoggerFactory.getLogger(RedisBase.class);
	
	@Autowired
	protected JedisPool jedisPool;

	public Long deleteKey(String key) {
		Jedis jedis = null;
		Long count = null;
		try {
			jedis = jedisPool.getResource();
			count = jedis.del(key);
			jedis.bgrewriteaof();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jedisPool.returnResource(jedis);
		}

		return count;
	}

	public void destroy() {
		if (jedisPool != null) {
			//log.debug("destroying jedis pool as container shutdown process");
			jedisPool.destroy();
		}
	}
}
