package com.yuqi.nettyclient.testdatareceiver;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2017/1/9
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */

public class TagOutFlowStatisticsMessage {

    private String tagName;
    private long   count;
    private long   size;
    private int    flowType;

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

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

    public int getFlowType() {
        return this.flowType;
    }

    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }

    public TagOutFlowStatisticsMessage(String tagName, long count, long size, int flowType) {
        this.tagName = tagName;
        this.count = count;
        this.size = size;
        this.flowType = flowType;
    }
}
