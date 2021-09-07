package com.sw.cloud.center.common.dailytools.idbuilder;

import com.sw.cloud.center.common.dailytools.ip.IpUtil;

/**
 * @className: IdGenerator
 * @description: id 生成器，解决时钟回拨问题
 * 订单号生成器，snowflake算法改进版，解决了时间回拨的问题
 * {1:正数}{1:是否回拨}{41:时间戳}{10:机器号}{11:序列号}
 * @author: sw
 * @date: 2021/9/7
 **/
public class IdGenerator {
    // 本机ip最后一个ip段，如果系统没有设置主机id则该数字为默认主机id
    private static final int IP_SUFFIX = IpUtil.getLocalIp()[3] & 0xff;
    private static final int HOST = Integer.valueOf(System.getProperty("hostid", String.valueOf(IP_SUFFIX)));
    private static final long START = 1546272000000L; // 2019-01-01 00:00:00.000

    private static short queue = -1;
    private static long currQueue = System.currentTimeMillis();

    public static synchronized long nextId() {
        if (++queue == 2048) {
            while (System.currentTimeMillis() == currQueue) {}
        }

        long c = System.currentTimeMillis();
        long back = c < currQueue ? 1 : 0;
        long t = c - START;

        if (c != currQueue) {
            currQueue = c;
            queue = 0;
        }

        return back << 62 | t << 21 | HOST << 11 | queue;
    }
}
