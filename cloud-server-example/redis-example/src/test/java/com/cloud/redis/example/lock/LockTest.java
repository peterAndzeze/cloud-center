package com.cloud.redis.example.lock;

import com.cloud.cache.redis.lock.CustomerLettuceLock;
import com.cloud.redis.example.RedisTestApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @className: LockTest
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/9/3
 **/
public class LockTest  extends RedisTestApplication {
    @Autowired
    private CustomerLettuceLock customerLettuceLock;

    @Test
    public void cusLockTest() throws InterruptedException {
        boolean lock = customerLettuceLock.lock("name", null, 10l, false, 0);
        System.out.println("加锁结果:"+lock);
        Thread.sleep(100000);

    }


}
