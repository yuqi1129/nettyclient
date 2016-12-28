package com.yuqi.nettyclient.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslContext;

import java.io.UnsupportedEncodingException;
import java.util.Date;
/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/10/23
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */

public class ClientSevenHanler extends ChannelInboundHandlerAdapter {
    //

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        //send some message to server

        ByteBuf byteBuf = Unpooled.copiedBuffer((new Date(System.currentTimeMillis()).toString() + "\n").getBytes());

        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){

        ByteBuf byteBuf = (ByteBuf)msg;
        byte [] b = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(b);

        String message = null;
        try {
            message = new String(b, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.print("received message from server:" + message);

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.close();
        System.out.println("\nRead completed,just close the ctx");
    }
}
