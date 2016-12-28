package com.yuqi.nettyclient;


import com.yuqi.nettyclient.common.People;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/10/18
 * Time: 20:20
 * To change this template use File | Settings | File Templates.
 */

public class ChapterSix {

    public static void main(String [] args){

        People people = new People();

        people.setId(1);
        people.setName("like");

        ByteArrayOutputStream bos  = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(bos);
            os.writeObject(people);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte [] b = bos.toByteArray();
        System.out.println("The jdk serializable length is :" + b.length);

        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("----------------------------");
        System.out.println("The byte array serializable lenght is : " + people.codeC().length);


    }
}
