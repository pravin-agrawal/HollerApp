package com.holler.redis;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

	//@Value("${jedis.pool.host}")
	private String jedisHost = "redis://:@localhost:6379/";

	//@Value("${jedis.pool.max.active}")
	private String maxActive = "300";

	//@Value("${jedis.pool.max.idle}")
	private String maxIdle = "10";

	//@Value("${jedis.pool.max.wait}")
	private String maxWait = "30000";

	//@Value("${jedis.pool.default.timeout}")
	private String defaultTimeout = "20000";

	public @Bean
	JedisPool getJedisPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(Integer.valueOf(maxActive));
			config.setMaxIdle(Integer.valueOf(maxIdle));
			config.setMinIdle(1);
			config.setMaxWaitMillis(Integer.valueOf(maxWait));
			config.setNumTestsPerEvictionRun(3);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			config.setTestWhileIdle(true);
			config.setTimeBetweenEvictionRunsMillis(30000);

			URI jedisURI = new URI(jedisHost);
			return new JedisPool(config, jedisURI.getHost(),
					jedisURI.getPort(), Integer.valueOf(defaultTimeout), null);
		} catch (URISyntaxException e) {
			throw new RuntimeException(
					"Redis couldn't be configured from URL in REDISTOGO_URL env var:"
							+ jedisHost);
		}
	}
}
