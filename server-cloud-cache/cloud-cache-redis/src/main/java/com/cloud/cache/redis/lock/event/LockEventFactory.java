package com.cloud.cache.redis.lock.event;

import com.lmax.disruptor.EventFactory;

/**
 * @className: EventFactory
 * @description: 事件工厂
 * @author: sw
 * @date: 2021/9/4
 **/
public class LockEventFactory implements EventFactory<LockEvent> {

    @Override
    public LockEvent newInstance() {
        return new LockEvent();
    }
}
