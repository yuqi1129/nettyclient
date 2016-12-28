package com.yuqi.nettyclient.testdatareceiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Random;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/12/22
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private String threadName ;

    public ClientHandler(String threadName) {
        this.threadName = threadName;
    }

    public ClientHandler() {
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public void channelActive(ChannelHandlerContext context){

        ByteBuf byteBuf = null;
        Random random = new Random();
        for(int i = 0; i< 100 ;i++){
            TagOutStatisticsMessage message = new TagOutStatisticsMessage("me","xxx","10000",random.nextInt(5) +1 ,(random.nextInt(5) + 1) * 100);
            BaseMessage baseMessage = new BaseMessage(8,"xxx_test",1,new Date().getTime(), JSON.toJSONString(message));
            //byte [] req = JSON.toJSONString(baseMessage).getBytes();
            //byteBuf = Unpooled.buffer(req.length);
            //byteBuf.writeBytes(req);
            //context.writeAndFlush(byteBuf);
            try{
                Thread.currentThread().sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(JSON.toJSONString(baseMessage));
            context.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseMessage)));
            //context.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseMessage)));
        }
        System.out.println("send data success!");

    }

    @Override
    public void  channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName() + " channelRegistered");

    }


}
