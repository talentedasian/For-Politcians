package com.example.demo.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisExpirationUtils {

	private final RedisTemplate<byte[], byte[]> template;

	public RedisExpirationUtils(RedisTemplate<byte[], byte[]> template) {
		super();
		this.template = template;
	}

	public Long getTimeToLiveOfKey(String key) {
		return template.getConnectionFactory().getConnection().ttl(("rate_limit:" + key).getBytes());
	}
}
