package com.luke.luke_blog.utils;

/**
 * 雪花算法,生成ID
 *
 * @author zhang
 * @date 2020/07/21
 */
public class IdWorker {

    /**
     * 开始时间戳
     */
    private final long twepoch = 1420041600000L;

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private final long datacenterIdBits = 5L;

    /**
     * 支持最大机器id  结果是31
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持最大机器id  结果是31
     */
    private final long maxDatacenterID = -1L ^ (-1L << datacenterIdBits);


    /**
     * 位序列
     */
    private final long sequenceBits = 12L;

    /**
     * 机器左移12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据左移17位
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间戳左移22位
     */
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 序列的面具  这里为4095
     */
    private final long sequenceMask = -1L ^ (-1L<<sequenceBits);

    /**
     * 职工身份证
     */
    private long workerId;

    /**
     * 数据中心id
     */
    private long datacenterId;

    /**
     * 序列
     */
    private long sequence = 0L;

    /**
     * 最后一个时间戳
     */
    private long lastTimestamp = -1L;

    /**
     * 构造方法
     *
     * @param workerId     职工身份证
     * @param datacenterId 数据中心id
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId>maxWorkerId||workerId<0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                    maxWorkerId));
        }
        if (datacenterId>maxDatacenterID||datacenterId<0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0",
                    maxDatacenterID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp<lastTimestamp) {
            throw new RuntimeException(String.format("clock moved backwards: Refusing to generate id " +
                    "for %d milliseconds",lastTimestamp-timestamp));
        }

        if (lastTimestamp==timestamp) {
            sequence = (sequence+1)&sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp-twepoch)<<timestampLeftShift)
                |(datacenterId<<datacenterIdShift)
                |(workerId<<workerIdShift)
                |sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

}
