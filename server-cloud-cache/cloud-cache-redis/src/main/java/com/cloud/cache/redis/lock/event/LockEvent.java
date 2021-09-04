package com.cloud.cache.redis.lock.event;

import com.cloud.cache.redis.lock.CustomerLettuceLock;
import com.cloud.cache.redis.lock.LockContent;

/**
 * @className: LockEvent
 * @description: 加锁事件
 * @author: sw
 * @date: 2021/9/4
 **/
public class LockEvent {
    /**
     * 加锁对象
     */
    private LockContent lockContent;
    /**
     * 对象处理
     */
    private CustomerLettuceLock customerLettuceLock;


    /**
     * 放入加锁数据
     * @param lockContent 事件要处理数据
     */
    public void setData(LockContent lockContent) {
        this.lockContent=lockContent;
    }

    public void setCustomerLettuceLock(CustomerLettuceLock customerLettuceLock) {
        this.customerLettuceLock = customerLettuceLock;
    }

    public LockContent getLockContent() {
        return lockContent;
    }

    public CustomerLettuceLock getCustomerLettuceLock() {
        return customerLettuceLock;
    }

    /**
     * 清空数据
     * @param lockEvent
     */
    public void clear(LockEvent lockEvent) {
        System.out.println(lockEvent.getClass()+"我是被清空对象实例");
        lockEvent=null;
    }
}
