package com.github.vinfolhu.config.redis;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * redis配置类
 *
 * @author zcc ON 2018/3/19
 **/
@Configuration
@EnableCaching // 开启注解
public class RedisConfig {

	public static final FastJson2JsonRedisSerializer<Object> serializer = new FastJson2JsonRedisSerializer<Object>(
			Object.class);

	// 以下两种redisTemplate自由根据场景选择
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		template.setValueSerializer(serializer);
		template.setKeySerializer(serializer);
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.afterPropertiesSet();
		return template;
	}
}