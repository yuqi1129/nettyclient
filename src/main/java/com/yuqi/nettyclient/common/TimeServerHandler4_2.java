package com.yuqi.nettyclient.common;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/10/17
 * Time: 21:19
 * To change this template use File | Settings | File Templates.
 */

public class TimeServerHandler4_2 extends ChannelInboundHandlerAdapter {


    private static final Logger logger = Logger.getLogger(TimeServerHandler4_2.class.getName());

    private int counter;

    private byte [] req;

    public TimeServerHandler4_2(){
        //req = ("Query time order" + System.getProperty("line.separator")).getBytes();
        req = ("Query time order" + "$_").getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ByteBuf byteBuf = null;
        for(int i = 0; i<100 ;i++){
            byteBuf = Unpooled.buffer(req.length);
            byteBuf.writeBytes(req);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf byteBuf = (ByteBuf)msg;

        byte [] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);

        String body = null;
        try {
             body = new String(req,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            //todo
        }

        System.out.println("Now is: " + body + " ; the counter is : " + ++counter );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        logger.warning("get exception:" + cause.getCause());
        ctx.close();
    }
}
