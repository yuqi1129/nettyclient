package com.yuqi.nettyclient.testdatareceiver;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/12/22
 * Time: 10:25
 * To change this template use File | Settings | File Templates.
 */

public class BaseMessage {

    private int type ;
    private String hostName ;
    private int nodeType;
    private long eventTime ;
    private String message ;

    public BaseMessage(int type, String hostName, int nodeType, long eventTime, String message) {
        this.type = type;
        this.hostName = hostName;
        this.nodeType = nodeType;
        this.eventTime = eventTime;
        this.message = message;
    }

    public BaseMessage() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
