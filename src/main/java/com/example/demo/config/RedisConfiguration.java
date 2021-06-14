package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter.EnableKeyspaceEvents;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(enableKeyspaceEvents = EnableKeyspaceEvents.ON_STARTUP)
public class RedisConfiguration {

	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
		return new JedisConnectionFactory(config);
	}
	
	@Bean
	  public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

	    RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
	    template.setConnectionFactory(redisConnectionFactory);
	    return template;
	  }
	
}
