package com.cloud.cache.redis.lock;

/**
 * @className: LockContent
 * @description: 锁内容信息
 * @author: sw
 * @date: 2021/9/4
 **/
public class LockContent {
    /**
     * 加锁key
     */
    private String lockKey;
    /**
     * 开始枷锁时间
     */
    private long startTime;
    /**
     * 加锁线程
     */
    private Thread lockThread;
    /**
     * 业务设置锁过期时长
     */
    private long bizExpire;
    /**
     * 枷锁时长
     */
    private long lockExpire;
    /**
     * 枷锁时间
     */
    private long expireTime;
    /**
     * 请求标识 解锁时验证
     */
    private String requestId;
    /**
     * 枷锁次数
     */
    private int lockCount;


    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Thread getLockThread() {
        return lockThread;
    }

    public void setLockThread(Thread lockThread) {
        this.lockThread = lockThread;
    }

    public long getBizExpire() {
        return bizExpire;
    }

    public void setBizExpire(long bizExpire) {
        this.bizExpire = bizExpire;
    }

    public long getLockExpire() {
        return lockExpire;
    }

    public void setLockExpire(long lockExpire) {
        this.lockExpire = lockExpire;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getLockCount() {
        return lockCount;
    }

    public void setLockCount(int lockCount) {
        this.lockCount = lockCount;
    }
}
