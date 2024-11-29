package com.mentormentee.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    private final RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    @Bean(name = "redisBlackListTemplate")
    public RedisTemplate<String, Object> redisBlackListTemplate() {
        RedisTemplate<String, Object> redisBlackListTemplate = new RedisTemplate<>();
        redisBlackListTemplate.setConnectionFactory(redisConnectionFactory());
        redisBlackListTemplate.setKeySerializer(new StringRedisSerializer());
        redisBlackListTemplate.setValueSerializer(new StringRedisSerializer());

        return redisBlackListTemplate;
    }


    @Bean(name = "defaultRedisTemplate")
    public RedisTemplate<String, Object> defaultRedisTemplate() {
        RedisTemplate<String, Object> defaultRedisTemplate = new RedisTemplate<>();
        defaultRedisTemplate.setConnectionFactory(redisConnectionFactory());
        defaultRedisTemplate.setKeySerializer(new StringRedisSerializer());
        defaultRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return defaultRedisTemplate;
    }


    @Bean(name = "jsonRedisTemplate")
    public RedisTemplate<String, Object> jsonRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

}
