package com.cloud.cache.redis.lock;

import com.cloud.cache.redis.RedisTestApplication;
import com.cloud.cache.redis.util.RedisCacheUtil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @className: LockTest
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/9/3
 **/
public class LockTest  extends RedisTestApplication {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    /*@Test
    public void testLock(){
        CountDownLatch latch = new CountDownLatch(10);

        for(int i=0;i<10;i++) {
            int finalI = i;
            new Thread(()->{
                if(finalI %2==0){
                    RedisLock nameLock = null;
                    try {
                        nameLock = new RedisLock(redisTemplate, "name", 30);
                        nameLock.lock();
                        Thread.currentThread().sleep(300000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {

                        nameLock.unlock();
                    }
                }else{
                    RedisLock nameLock=null;
                    try {
                        nameLock= new RedisLock(redisTemplate, "name", 30);
                    } finally {
                        nameLock.unlock();
                    }
                }
            }).start();
        }
        //等待主线程结束
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void testLock1(){

    }


    private static final Long RELEASE_SUCCESS = 1L;



    /**

     * 释放分布式锁

     * @param jedis Redis客户端

     * @param lockKey 锁

     * @param requestId 请求标识

     * @return 是否释放成功

     */

   /* public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {



        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));



        if (RELEASE_SUCCESS.equals(result)) {

            return true;

        }

        return false;
    }*/

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";



    /**

     * 尝试获取分布式锁

     * @param jedis Redis客户端

     * @param lockKey 锁

     * @param requestId 请求标识

     * @param expireTime 超期时间

     * @return 是否获取成功

     */

    /*public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {



        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);



        if (LOCK_SUCCESS.equals(result)) {

            return true;

        }

        return false;



    }*/
    @Autowired
    private RedisURI redisURI;
    @Autowired
    private RedisCacheUtil redisCacheUtil;
    @Autowired
    private CustomerLettuceLock customerLettuceLock;
    @Test
    public void cusLockTest() throws InterruptedException {
        boolean lock = customerLettuceLock.lock("name", null, 10l, false, 0);
        System.out.println("加锁结果:"+lock);
        Thread.sleep(100000);

    }


}
