package com.cloud.cache.redis.lock;

import org.junit.Test;

import java.util.Random;

/**
 * @className: ThreadTest
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/9/4
 **/
public class ThreadTest {
    final Random random;

    public ThreadTest(Random random) {
        this.random = random;
    }

    @Test
    public void testSleep() throws InterruptedException {
        int i=100;
        while(i>0){
            System.out.println("为企鹅我");
        }


        Thread.sleep(10, this.random.nextInt(50000));
    }
}
