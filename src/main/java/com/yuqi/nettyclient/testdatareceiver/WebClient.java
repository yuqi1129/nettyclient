package com.yuqi.nettyclient.testdatareceiver;

import com.alibaba.fastjson.JSON;

import java.net.URI;
import java.util.Date;
import java.util.Random;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/12/22
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */

public class WebClient {

    private final DefaultThreadFactory  defaultThreadFactory = new DefaultThreadFactory(WebClient.class,false);
    private String hostname ;
    private int port ;

    public WebClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public WebClient() {
    }

    public Channel getChannel() throws Exception{

        EventLoopGroup group = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,defaultThreadFactory);

        final DefaultClientHandler handler = new DefaultClientHandler(WebSocketClientHandshakerFactory.newHandshaker(
                new URI("ws://" + hostname + ":" + String.valueOf(port) + "/websocket"), WebSocketVersion.V13,null,false,new DefaultHttpHeaders()
        ));

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000).handler(new
                                                                                                                                 ChannelInitializer<SocketChannel>() {
                                                                                                                                     @Override
                                                                                                                                     public void initChannel(SocketChannel ch){
                                                                                                                                         ch.pipeline().addLast(new HttpClientCodec(),
                                                                                                                                                 new HttpObjectAggregator(8192),handler);
                                                                                                                                     }
                                                                                                                                 }
        );

        Channel channel = bootstrap.connect(hostname,port).sync().channel();

        /*
        ByteBuf byteBuf = null;
        Random random = new Random();
        for(;;){
        //for(int i = 0; i< 1000 ;i++){
            TagOutStatisticsMessage message = new TagOutStatisticsMessage("me","xxx","10000",random.nextInt(5) +1 ,(random.nextInt(5) + 1) * 100);
            BaseMessage baseMessage = new BaseMessage(8,"xxx_test",1,new Date().getTime(), JSON.toJSONString(message));
            //byte [] req = JSON.toJSONString(baseMessage).getBytes();
            //byteBuf = Unpooled.buffer(req.length);
            //byteBuf.writeBytes(req);
            //context.writeAndFlush(byteBuf);
            try{
                Thread.currentThread().sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
            //System.out.println(JSON.toJSONString(baseMessage));
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseMessage)));
            NettyClient.atomicLong.getAndIncrement();

            //context.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseMessage)));
        }
        //return channel ;
        */
        NettyClient.connectionNumber.getAndIncrement();
        return channel;

    }


    public void sendMessage(Channel channel){
        ByteBuf byteBuf = null;
        Random random = new Random();
        for(;;){
            //for(int i = 0; i< 1000 ;i++){

            for (int i = 0 ;i < 1; i++) {
                TagOutStatisticsMessage message = new TagOutStatisticsMessage("me", "xxx", "10000", random.nextInt(5) + 1, (random.nextInt(5) + 1) * 100);
                BaseMessage baseMessage = new BaseMessage(8, "xxx_test", random.nextInt(3) + 1, new Date().getTime(), JSON.toJSONString(message));
                //byte [] req = JSON.toJSONString(baseMessage).getBytes();
                //byteBuf = Unpooled.buffer(req.length);
                //byteBuf.writeBytes(req);
                //context.writeAndFlush(byteBuf);
                try {
                    //Thread.currentThread().sleep(0.1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseMessage)));
                NettyClient.atomicLong.getAndIncrement();
                NettyClient.total.getAndIncrement();
            }
            try {
                Thread.currentThread().sleep(400);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //context.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseMessage)));
        }

    }




}
