package com.cloud.cache.redis.lock.handler;

import com.cloud.cache.redis.lock.event.LockEvent;
import com.lmax.disruptor.EventHandler;

/**
 * @className: ClearingEventHandler
 * @description: 清除上下文
 * @author: sw
 * @date: 2021/9/4
 **/
public class ClearingEventHandler  implements EventHandler {



    @Override
    public void onEvent(Object o, long l, boolean b) throws Exception {
        LockEvent lockEvent=(LockEvent) o;
        lockEvent.clear(lockEvent);
    }
}
