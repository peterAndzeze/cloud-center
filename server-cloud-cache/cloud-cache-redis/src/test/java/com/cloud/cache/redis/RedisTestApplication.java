package com.cloud.cache.redis;

import com.cloud.cache.redis.serializer.FastJson2JsonRedisSerializer;
import com.cloud.cache.redis.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: RedisTest
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/9/1
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTestApplication {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Test
    public void setRedis(){
        UserVo userVo=new UserVo();
        userVo.setName("张三");
        userVo.setAge(10);
        redisTemplate.opsForValue().set("name",userVo);
    }

    public static void main(String[] args) {
        UserVo userVo=new UserVo();
        userVo.setName("张三");
        userVo.setAge(10);
        List<Object> list = new ArrayList<>();
        for(int i=0;i<200;i++){
            list.add(userVo);
        }
        //空间敏感，忽略可读性和效率影响
        JdkSerializationRedisSerializer j = new JdkSerializationRedisSerializer();
        //效率和空间性能最差
        GenericJackson2JsonRedisSerializer g = new GenericJackson2JsonRedisSerializer();
        //效率和可读性，牺牲部分空间
        Jackson2JsonRedisSerializer j2 = new Jackson2JsonRedisSerializer(List.class);
        //效率和可读性，空间 均是中间值
        FastJson2JsonRedisSerializer f=new FastJson2JsonRedisSerializer(List.class);

        Long j_s_start = System.currentTimeMillis();
        byte[] bytesJ = j.serialize(list);
        System.out.println("JdkSerializationRedisSerializer序列化时间："+(System.currentTimeMillis()-j_s_start) + "ms,序列化后的长度：" + bytesJ.length);
        Long j_d_start = System.currentTimeMillis();
        j.deserialize(bytesJ);
        System.out.println("JdkSerializationRedisSerializer反序列化时间："+(System.currentTimeMillis()-j_d_start));


        Long g_s_start = System.currentTimeMillis();
        byte[] bytesG = g.serialize(list);
        System.out.println("GenericJackson2JsonRedisSerializer序列化时间："+(System.currentTimeMillis()-g_s_start) + "ms,序列化后的长度：" + bytesG.length);
        Long g_d_start = System.currentTimeMillis();
        g.deserialize(bytesG);
        System.out.println("GenericJackson2JsonRedisSerializer反序列化时间："+(System.currentTimeMillis()-g_d_start));

        Long j2_s_start = System.currentTimeMillis();
        byte[] bytesJ2 = j2.serialize(list);
        System.out.println("Jackson2JsonRedisSerializer序列化时间："+(System.currentTimeMillis()-j2_s_start) + "ms,序列化后的长度：" + bytesJ2.length);
        Long j2_d_start = System.currentTimeMillis();
        j2.deserialize(bytesJ2);
        System.out.println("Jackson2JsonRedisSerializer反序列化时间："+(System.currentTimeMillis()-j2_d_start));



        Long f_start = System.currentTimeMillis();
        byte[] fs = f.serialize(list);
        System.out.println("Jackson2JsonRedisSerializer序列化时间："+(System.currentTimeMillis()-f_start) + "ms,序列化后的长度：" + fs.length);
        Long f2_start = System.currentTimeMillis();
        f.deserialize(fs);
        System.out.println("Jackson2JsonRedisSerializer反序列化时间："+(System.currentTimeMillis()-f2_start));
    }



}
