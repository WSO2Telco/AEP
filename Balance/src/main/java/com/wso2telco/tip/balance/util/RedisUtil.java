package com.wso2telco.tip.balance.util;

import com.wso2telco.tip.balance.conf.ConfigReader;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

public class RedisUtil {

	private static volatile JedisPool pool = null;
	final static Logger log = LoggerFactory.getLogger(RedisUtil.class);

	public static JedisPool getInstance() {
		if (pool == null) {
			synchronized (RedisUtil.class) {
				if (pool == null) {
					ConfigReader configReader = ConfigReader.getInstance();
					Map<Object,Object> redis = (Map<Object, Object>) configReader.getApplicationConfiguration().getRedis();
					String redisHost = (String) redis.get("host");
					int redisPort = (Integer) redis.get("port");
					String password = (String) redis.get("password");
					int timeout = (Integer) redis.get("timeout");
					int jedisPoolSize = (Integer) redis.get("poolsize");

					if(log.isInfoEnabled()){
						log.info("Initializing Redis");
						log.info("Redis Host : " + redisHost);
						log.info("Redis Port : " + redisPort);
						log.info("Redis timeout : " + timeout);
						log.info("Redis PoolSize : " + jedisPoolSize);
					}

					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxTotal(jedisPoolSize);
					pool = new JedisPool(new GenericObjectPoolConfig(), redisHost, redisPort, timeout, password);
				}
			}
		}
		return pool;
	}
}
