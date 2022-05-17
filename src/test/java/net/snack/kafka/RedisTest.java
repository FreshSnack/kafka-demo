package net.snack.kafka;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testJdkSerialize() {
        User user = new User();
        user.setName("Jack");
        user.setAge(20);
        user.setBirth(LocalDate.of(1990, 9, 14));
        user.setBirthTime(LocalDateTime.now());
        user.setEntryDate(new Date());
        user.setSalary(5000d);
        redisTemplate.opsForValue().set("user", user);
        Object deserialize = redisTemplate.opsForValue().get("user");
        System.out.println(deserialize);
    }

    @Test
    public void testJsonSerialize() {
        User user = new User();
        user.setName("Jack");
        user.setAge(20);
        user.setBirth(LocalDate.of(1990, 9, 14));
        user.setBirthTime(LocalDateTime.now());
        user.setEntryDate(new Date());
        user.setSalary(5000d);
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        jacksonSerializer.setObjectMapper(mapper);
        redisTemplate.setValueSerializer(jacksonSerializer);
        redisTemplate.opsForValue().set("user", user);
        Object deserialize = redisTemplate.opsForValue().get("user");
        System.out.println((User) deserialize);
    }

    @Test
    public void testGenericSerialize() {
        User user = new User();
        user.setName("Jack");
        user.setAge(20);
        user.setBirth(LocalDate.of(1990, 9, 14));
        user.setBirthTime(LocalDateTime.now());
        user.setEntryDate(new Date());
        user.setSalary(5000d);
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));
        redisTemplate.opsForValue().set("user", user);
        Object deserialize = redisTemplate.opsForValue().get("user");
        System.out.println(deserialize);
    }

}
