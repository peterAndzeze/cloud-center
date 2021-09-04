package com.cloud.cache.redis;

import com.cloud.cache.redis.lock.CustomerLettuceLock;
import com.cloud.cache.redis.lock.event.LockEvent;
import com.cloud.cache.redis.lock.handler.LockEventHandler;
import com.cloud.framework.spring.util.SpringUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @className: RedisConfiguration
 * @description: 初始化redis的配置 主要处理序列化问题
 * @author: sw
 * @date: 2021/9/1
 **/
@Configuration
public class RedisConfiguration implements EnvironmentAware {

    private Environment environment;
    /**
     * 初始化redis操作模版
     * @param connectionFactory  use lettuce core 初始化
     * @return
     */
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        serializer.setObjectMapper(mapper);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 初始化
     * @return
     */
    @Bean
    public RedisURI redisUri(){
        Integer port = environment.getProperty("spring.redis.port",Integer.class);
        String password=environment.getProperty("spring.redis.password");
        Integer database = environment.getProperty("spring.redis.database", Integer.class);
        RedisURI redisUri = RedisURI.create(
                environment.getProperty("spring.redis.host"),
                null==port?6379:port
        );
        if(null!=password){
            redisUri.setPassword(password);
        }
        redisUri.setDatabase(database);
        return redisUri;
    }
    @Bean
    public SpringUtils springUtils(){
        return new SpringUtils();
    }

    /**
     * 分布式锁工具类
     * @return
     */
    @Bean
    public CustomerLettuceLock customerLettuceLock(){
         return new CustomerLettuceLock();
    }

    /**
     * 启动异步线程
     */
    @Bean
    public Disruptor disruptor(){
        int bufferSize=1024;
        Disruptor<LockEvent> disruptor =
                new Disruptor<>( LockEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new LockEventHandler());
        disruptor.start();
        return disruptor;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }



}
