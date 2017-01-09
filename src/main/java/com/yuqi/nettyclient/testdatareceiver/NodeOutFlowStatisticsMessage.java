package com.yuqi.nettyclient.testdatareceiver;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2017/1/9
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */

public class NodeOutFlowStatisticsMessage {
    private long hdfsFlowCount;
    private long hdfsFlowSize;
    private long mqFlowCount;
    private long mqFlowSize;
    private long hbaseFlowCount;
    private long hbaseFlowSize;

    public long getHdfsFlowCount() {
        return hdfsFlowCount;
    }

    public void setHdfsFlowCount(long hdfsFlowCount) {
        this.hdfsFlowCount = hdfsFlowCount;
    }

    public long getHdfsFlowSize() {
        return hdfsFlowSize;
    }

    public void setHdfsFlowSize(long hdfsFlowSize) {
        this.hdfsFlowSize = hdfsFlowSize;
    }

    public long getMqFlowCount() {
        return mqFlowCount;
    }

    public void setMqFlowCount(long mqFlowCount) {
        this.mqFlowCount = mqFlowCount;
    }

    public long getMqFlowSize() {
        return mqFlowSize;
    }

    public void setMqFlowSize(long mqFlowSize) {
        this.mqFlowSize = mqFlowSize;
    }

    public long getHbaseFlowCount() {
        return hbaseFlowCount;
    }

    public void setHbaseFlowCount(long hbaseFlowCount) {
        this.hbaseFlowCount = hbaseFlowCount;
    }

    public long getHbaseFlowSize() {
        return hbaseFlowSize;
    }

    public void setHbaseFlowSize(long hbaseFlowSize) {
        this.hbaseFlowSize = hbaseFlowSize;
    }
}
