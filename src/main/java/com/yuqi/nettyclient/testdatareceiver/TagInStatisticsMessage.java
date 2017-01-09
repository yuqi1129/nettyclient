package com.yuqi.nettyclient.testdatareceiver;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2017/1/9
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */

public class TagInStatisticsMessage {

    private String tagname; // tag名称
    private long   count;  // 统计数
    private long   size;   // 日志大小

    public String getTagname() {
        return this.tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
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

    public TagInStatisticsMessage(String tagname, long count, long size) {
        this.tagname = tagname;
        this.count = count;
        this.size = size;
    }
}