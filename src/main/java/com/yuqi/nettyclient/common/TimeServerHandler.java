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
 * Date: 2016/10/16
 * Time: 20:51
 * To change this template use File | Settings | File Templates.
 */


public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(TimeServerHandler.class.getName());

    private final ByteBuf firstMessage;

    public TimeServerHandler(){
        byte [] req = "Query time order".getBytes();
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf buff = (ByteBuf) msg;
        byte [] req = new byte [buff.readableBytes()];
        buff.readBytes(req);
        String body = null;
        try {
            body = new String(req,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Now is :" + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        logger.warning("Unexpected exception from downstream :" + cause.getMessage());
        ctx.close();
    }
}
