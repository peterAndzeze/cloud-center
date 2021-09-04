package com.cloud.cache.redis.lock.handler;

import com.cloud.cache.redis.lock.CustomerLettuceLock;
import com.cloud.cache.redis.lock.LockContent;
import com.cloud.cache.redis.lock.event.LockEvent;
import com.lmax.disruptor.EventHandler;

/**
 * @className: LockEventHandler
 * @description: 加锁事件处理器
 * @author: sw
 * @date: 2021/9/4
 **/
public class LockEventHandler implements EventHandler<LockEvent> {
    @Override
    public void onEvent(LockEvent event, long sequence, boolean endOfBatch) {
        System.out.println("是否执行");
        LockContent lockContent = event.getLockContent();
        CustomerLettuceLock customerLettuceLock = event.getCustomerLettuceLock();
        boolean renew = customerLettuceLock.renew(lockContent);
        if (renew) {
            long expireTimeNew = lockContent.getStartTime() + (lockContent.getExpireTime() - lockContent.getStartTime()) * 2 - CustomerLettuceLock.TIME_SECONDS_FIVE * 1000;
            lockContent.setExpireTime(expireTimeNew);
        } else {
            // 续约失败，说明已经执行完 OR redis 出现问题
            customerLettuceLock.getLockContentMap().remove(lockContent.getLockKey());
        }

    }
}
