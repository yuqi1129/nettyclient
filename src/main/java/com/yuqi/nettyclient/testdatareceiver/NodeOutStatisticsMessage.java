package com.yuqi.nettyclient.testdatareceiver;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2017/1/9
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */

public class NodeOutStatisticsMessage {
    private long count; // 统计数
    private long size; // 日志大小

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
