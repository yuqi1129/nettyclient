package com.yuqi.nettyclient.testdatareceiver;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/12/22
 * Time: 10:20
 * To change this template use File | Settings | File Templates.
 */

public class TagOutStatisticsMessage {
    private String tagname;  // tag名称
    private String filename; // 文件名称
    private String fileInode; // 文件的inode
    private long   count;    // 统计数
    private long   size;     // 日志大小

    public TagOutStatisticsMessage(String tagname, String filename, String fileInode, long count, long size) {
        this.tagname = tagname;
        this.filename = filename;
        this.fileInode = fileInode;
        this.count = count;
        this.size = size;
    }

    public TagOutStatisticsMessage() {
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileInode() {
        return fileInode;
    }

    public void setFileInode(String fileInode) {
        this.fileInode = fileInode;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
