package com.cloud.cache.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.commands.JedisCommands;
import redis.clients.jedis.params.SetParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @className: RedisLock
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/9/2
 **/
public class RedisLock {

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);
    private RedisTemplate redisTemplate;
    public static final String NX = "NX";
    public static final String EX = "EX";
    public static final String OK = "OK";
    private static final long TIME_OUT = 100L;
    public static final int EXPIRE = 60;
    public static final String UNLOCK_LUA;
    private String lockKey;
    private String lockKeyLog;
    private String lockValue;
    private int expireTime;
    private long timeOut;
    private volatile boolean locked;
    final Random random;

    public RedisLock(RedisTemplate redisTemplate, String lockKey) {
        this.lockKeyLog = "";
        this.expireTime = 60;
        this.timeOut = 100L;
        this.locked = false;
        this.random = new Random();
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, int expireTime) {
        this(redisTemplate, lockKey);
        this.expireTime = expireTime;
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, long timeOut) {
        this(redisTemplate, lockKey);
        this.timeOut = timeOut;
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, int expireTime, long timeOut) {
        this(redisTemplate, lockKey, expireTime);
        this.timeOut = timeOut;
    }

    public boolean tryLock() {
        this.lockValue = UUID.randomUUID().toString();
        long timeout = this.timeOut * 1000000L;
        long nowTime = System.nanoTime();

        while(System.nanoTime() - nowTime < timeout) {
            if ("OK".equalsIgnoreCase(this.set(this.lockKey, this.lockValue, (long)this.expireTime))) {
                this.locked = true;
                return this.locked;
            }

            this.seleep(10L, 50000);
        }

        return this.locked;
    }

    public boolean lock() {
        logger.info("加锁线程:"+Thread.currentThread().getName());

        this.lockValue = UUID.randomUUID().toString();
        String result = this.set(this.lockKey, this.lockValue, (long)this.expireTime);
        this.locked = "OK".equalsIgnoreCase(result);
        return this.locked;
    }

    public boolean lockBlock() {
        this.lockValue = UUID.randomUUID().toString();

        while(true) {
            String result = this.set(this.lockKey, this.lockValue, (long)this.expireTime);
            if ("OK".equalsIgnoreCase(result)) {
                this.locked = true;
                return this.locked;
            }

            this.seleep(10L, 50000);
        }
    }

    public Boolean unlock() {
        return this.locked ? (Boolean)this.redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                logger.info("释放锁线程:"+Thread.currentThread().getName());
                Object nativeConnection = connection.getNativeConnection();
                Long result = 0L;
                List<String> keys = new ArrayList();
                keys.add(RedisLock.this.lockKey);
                List<String> values = new ArrayList();
                values.add(RedisLock.this.lockValue);
                if (nativeConnection instanceof JedisCluster) {
                    result = (Long)((JedisCluster)nativeConnection).eval(RedisLock.UNLOCK_LUA, keys, values);
                }

                if (nativeConnection instanceof Jedis) {
                    result = (Long)((Jedis)nativeConnection).eval(RedisLock.UNLOCK_LUA, keys, values);
                }

                if (result == 0L && !StringUtils.isEmpty(RedisLock.this.lockKeyLog)) {
                    RedisLock.logger.info("Redis分布式锁，解锁{}失败！解锁时间：{}", RedisLock.this.lockKeyLog, System.currentTimeMillis());
                }

                RedisLock.this.locked = result == 0L;
                return result == 1L;
            }
        }) : true;
    }

    private String set(final String key, final String value, final long seconds) {
        Assert.isTrue(!StringUtils.isEmpty(key), "key不能为空");
        return (String)this.redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                String result = null;
                if (nativeConnection instanceof JedisCommands) {
                    SetParams setParams=new SetParams();
                    setParams.ex(seconds);
                    setParams.nx();
                    result = ((JedisCommands)nativeConnection).set(key, value, setParams);
                }

                if (!StringUtils.isEmpty(RedisLock.this.lockKeyLog) && !StringUtils.isEmpty(result)) {
                    RedisLock.logger.info("获取锁{}的时间：{}", RedisLock.this.lockKeyLog, System.currentTimeMillis());
                }

                return result;
            }
        });
    }

    private void seleep(long millis, int nanos) {
        try {
            Thread.sleep(millis, this.random.nextInt(nanos));
        } catch (InterruptedException var5) {
            logger.info("获取分布式锁休眠被中断：", var5);
        }

    }

    public String getLockKeyLog() {
        return this.lockKeyLog;
    }

    public void setLockKeyLog(String lockKeyLog) {
        this.lockKeyLog = lockKeyLog;
    }

    public int getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public long getTimeOut() {
        return this.timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }
}
