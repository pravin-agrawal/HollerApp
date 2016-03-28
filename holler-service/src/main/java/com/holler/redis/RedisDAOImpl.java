package com.holler.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

@Component
public class RedisDAOImpl implements RedisDAO {
	private static Logger log = LoggerFactory.getLogger(RedisDAOImpl.class);

	@Autowired
	RedisTemplate redisTemplate;

	
	public String set(final String key, final String value) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			
			public String doInRedis(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
		// log.info("Response from redis for set: " + result);
		return result;
	}

	
	public String get(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			
			public String doInRedis(Jedis jedis) {
				return jedis.get(key);
			}
		});
		// log.info("Response from redis for get: " + result);
		return result;
	}

	
	public Boolean exists(final String key) {
		Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			
			public Boolean doInRedis(Jedis jedis) {
				return jedis.exists(key);
			}
		});
		// log.info("Response from redis for exists: " + result);
		return result;
	}

	
	public String type(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long expire(final String key, final int seconds) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			
			public Long doInRedis(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
		// log.info("Response from redis for expire: " + result);
		return result;
	}

	
	public Long expireAt(final String key, final long unixTime) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			
			public Long doInRedis(Jedis jedis) {
				return jedis.expireAt(key, unixTime);
			}
		});
		// log.info("Response from redis for expireAt: " + result);
		return result;
	}

	
	public Long ttl(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean setbit(String key, long offset, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean getbit(String key, long offset) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public long setrange(String key, long offset, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public String getrange(String key, long startOffset, long endOffset) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getSet(final String key, final String value) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			
			public String doInRedis(Jedis jedis) {
				return jedis.getSet(key, value);
			}
		});
		return result;
	}

	
	public Long setnx(final String key, final String value) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			
			public Long doInRedis(Jedis jedis) {
				return jedis.setnx(key, value);
			}
		});
		return result;
	}

	
	public String setex(final String key, final int seconds, final String value) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			
			public String doInRedis(Jedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
		return result;
	}

	
	public Long decrBy(String key, long integer) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long decr(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long incrBy(final String key, final long integer) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			
			public Long doInRedis(Jedis jedis) {
				return jedis.incrBy(key, integer);
			}
		});
		// log.info("Response from redis for incrBy: " + result);
		return result;
	}

	
	public Long incr(String key) {
		return incrBy(key, 1l);
	}

	
	public Long append(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String substr(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long hset(final String key, final String field, final String value) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			
			public Long doInRedis(Jedis jedis) {
				return jedis.hset(key, field, value);
			}
		});
		return result;
	}

	
	public String hget(final String key, final String field) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			
			public String doInRedis(Jedis jedis) {
				return jedis.hget(key, field);
			}
		});
		return result;
	}

	
	public Long hsetnx(String key, String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String hmset(final String key,
			final Map<String, String> binMasterData) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			
			public String doInRedis(Jedis jedis) {
				return jedis.hmset(key, binMasterData);
			}
		});
		log.info("Response from redis for hmset: " + result);
		return result;
	}

	
	public List<String> hmget(final String key, final String... fields) {
		List<String> result = redisTemplate
				.execute(new RedisCallback<List<String>>() {
					
					public List<String> doInRedis(Jedis jedis) {
						return jedis.hmget(key, fields);
					}
				});
		// log.info("Response from redis for hmget: " + result);
		return result;
	}

	
	public String hmgetSingle(final String key, final String field) {
		List<String> result = this.hmget(key, field);
		return result == null || result.isEmpty() ? null : result.get(0);
	}

	
	public Long hincrBy(String key, String field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Boolean hexists(final String key, final String field) {
		Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			
			public Boolean doInRedis(Jedis jedis) {
				return jedis.hexists(key, field);
			}
		});
		// log.info("Response from redis for hexists: " + result);
		return result;
	}

	
	public Long hdel(final String key, final String field) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			
			public Long doInRedis(Jedis jedis) {
				return jedis.hdel(key, field);
			}
		});
		// log.info("Response from redis for hdel: " + result);
		return result;
	}

	
	public Long hlen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<String> hkeys(final String key) {
		Set<String> result = redisTemplate
				.execute(new RedisCallback<Set<String>>() {
					
					public Set<String> doInRedis(Jedis jedis) {
						return jedis.hkeys(key);
					}
				});
		return result;
	}

	
	public List<String> hvals(final String key) {
		List<String> result = redisTemplate
				.execute(new RedisCallback<List<String>>() {
					
					public List<String> doInRedis(Jedis jedis) {
						return jedis.hvals(key);
					}
				});
		return result;
	}

	
	public Map<String, String> hgetAll(final String key) {
		Map<String, String> result = redisTemplate
				.execute(new RedisCallback<Map<String, String>>() {
					
					public Map<String, String> doInRedis(Jedis jedis) {
						return jedis.hgetAll(key);
					}
				});
		// log.info("Response from redis for hgetAll: " + result);
		return result;
	}

	
	public Long rpush(String key, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long lpush(final String key, final String string) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			
			public Long doInRedis(Jedis jedis) {
				return jedis.lpush(key, string);
			}
		});
		return result;
	}

	
	public Long llen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<String> lrange(final String key, final long start,
			final long end) {
		List<String> result = redisTemplate
				.execute(new RedisCallback<List<String>>() {
					
					public List<String> doInRedis(Jedis jedis) {
						return jedis.lrange(key, start, end);
					}
				});
		return result;
	}

	
	public String ltrim(final String key, final long start, final long end) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			
			public String doInRedis(Jedis jedis) {
				return jedis.ltrim(key, start, end);
			}
		});
		return result;
	}

	
	public String lindex(String key, long index) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String lset(String key, long index, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long lrem(String key, long count, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String lpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String rpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long sadd(final String key, final String member) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			
			public Long doInRedis(Jedis jedis) {
				return jedis.sadd(key, member);
			}
		});
		return result;
	}

	
	public Long sadd(String key, List<String> values) {
		Long count = 0l;
		for (String value : values) {
			count += sadd(key, value);
		}
		return count;
	}

	
	public Set<String> smembers(final String key) {
		Set<String> result = redisTemplate
				.execute(new RedisCallback<Set<String>>() {
					
					public Set<String> doInRedis(Jedis jedis) {
						return jedis.smembers(key);
					}
				});
		return result;
	}

	
	public Long srem(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String spop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long scard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Boolean sismember(final String key, final String member) {
		Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			
			public Boolean doInRedis(Jedis jedis) {
				return jedis.sismember(key, member);
			}
		});
		return result;
	}

	
	public String srandmember(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long zadd(String key, double score, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<String> zrange(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long zrem(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Double zincrby(String key, double score, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long zrank(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long zrevrank(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<String> zrevrange(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<Tuple> zrangeWithScores(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long zcard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Double zscore(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<String> sort(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<String> sort(String key, SortingParams sortingParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long zcount(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<String> zrangeByScore(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<String> zrangeByScore(String key, double min, double max,
			int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<String> zrevrangeByScore(String key, double max, double min,
			int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<Tuple> zrangeByScoreWithScores(String key, double min,
			double max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long zremrangeByRank(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long zremrangeByScore(String key, double start, double end) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long linsert(String key, LIST_POSITION where, String pivot,
			String value) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void delete(final String key) {
		@SuppressWarnings("unused")
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			
			public Long doInRedis(Jedis jedis) {
				return jedis.del(key);
			}
		});
		// log.info("Response from redis for delete: " + result);
	}

	
	public Long delete(final String... keys) {
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			
			public Long doInRedis(Jedis jedis) {
				return jedis.del(keys);
			}
		});
		// log.info("Response from redis for delete: " + result);
		return result;
	}

	
	public Set<String> keysMatchingPattern(final String pattern) {
		Set<String> result = redisTemplate
				.execute(new RedisCallback<Set<String>>() {
					
					public Set<String> doInRedis(Jedis jedis) {
						return jedis.keys(pattern);
					}
				});
		return result;
	}
}