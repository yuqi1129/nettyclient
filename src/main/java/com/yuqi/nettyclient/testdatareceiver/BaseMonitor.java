package com.yuqi.nettyclient.testdatareceiver;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2017/1/9
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */

public class BaseMonitor {

    private int nodeTye;
    private int messageType;

    public BaseMonitor(int nodeTye, int messageType) {
        this.nodeTye = nodeTye;
        this.messageType = messageType;
    }

    public int getNodeTye() {
        return nodeTye;
    }

    public void setNodeTye(int nodeTye) {
        this.nodeTye = nodeTye;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
