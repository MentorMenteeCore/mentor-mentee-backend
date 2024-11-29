package com.mentormentee.core.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> defaultRedisTemplate;
    private final RedisTemplate<String, Object> jsonRedisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;

    public void set(String key, Object o, int minutes) {
        defaultRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        defaultRedisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public RedisUtil(@Qualifier("defaultRedisTemplate")RedisTemplate<String, Object> defaultRedisTemplate,@Qualifier("redisBlackListTemplate")RedisTemplate<String, Object> redisBlackListTemplate, @Qualifier("jsonRedisTemplate") RedisTemplate<String, Object> jsonRedisTemplate) {
        this.defaultRedisTemplate = defaultRedisTemplate;
        this.redisBlackListTemplate = redisBlackListTemplate;
        this.jsonRedisTemplate = jsonRedisTemplate;
    }


    public String get(String key) {
        return (String) defaultRedisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(defaultRedisTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(defaultRedisTemplate.hasKey(key));
    }

    public void setBlackList(String key, String value, long milliseconds) {
        redisBlackListTemplate.opsForValue().set(key, value, milliseconds, TimeUnit.MILLISECONDS);
    }

    public String getBlackList(String key) {
        return (String) redisBlackListTemplate.opsForValue().get(key);
    }

    public boolean deleteBlackList(String key) {
        return Boolean.TRUE.equals(redisBlackListTemplate.delete(key));
    }

    public boolean hasKeyBlackList(String key) {
        return Boolean.TRUE.equals(redisBlackListTemplate.hasKey(key));
    }

    // 학과 및 과목 정보 저장에 사용되는 메서드 (jsonRedisTemplate 사용)
    public void setHashValue(String key, String hashKey, Object value) {
        jsonRedisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getHashValue(String key, String hashKey) {

            try {
                return jsonRedisTemplate.opsForHash().get(key, hashKey);
            } catch (Exception e) {
                System.out.println(key+hashKey+e+"에러");
                throw e;
            }

//        return jsonRedisTemplate.opsForHash().get(key, hashKey);
    }

    public Map<Object, Object> getHashEntries(String key) {
        return jsonRedisTemplate.opsForHash().entries(key);
    }
}
