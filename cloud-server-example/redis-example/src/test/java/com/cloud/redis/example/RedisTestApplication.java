package com.cloud.redis.example;

import com.cloud.cache.redis.util.RedisCacheUtil;
import com.cloud.redis.example.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private RedisCacheUtil redisCacheUtil;
    @Test
    public void setRedis(){
        UserVo userVo=new UserVo();
        userVo.setName("张三");
        userVo.setAge(10);
        redisCacheUtil.setCacheObject("user",userVo);
    }




}
