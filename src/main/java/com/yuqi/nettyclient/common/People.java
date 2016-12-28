package com.yuqi.nettyclient.common;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/10/18
 * Time: 20:14
 * To change this template use File | Settings | File Templates.
 */

public class People implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id ;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public byte [] codeC(){

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte [] value = this.name.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);

        buffer.putInt(this.id);
        buffer.flip();

        value = null;
        byte [] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;

    }
}
