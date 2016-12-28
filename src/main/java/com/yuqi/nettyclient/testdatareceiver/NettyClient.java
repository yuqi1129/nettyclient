package com.yuqi.nettyclient.testdatareceiver;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/12/22
 * Time: 9:56
 * To change this template use File | Settings | File Templates.
 */

public class NettyClient {

    public static AtomicLong atomicLong = new AtomicLong(0L);
    public static AtomicLong total = new AtomicLong(0L);
    public static AtomicInteger connectionNumber = new AtomicInteger(0);

    public static void connect(String host,int port){
        EventLoopGroup client = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(client).channel(NioSocketChannel.class).option(
                ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch){
                    ByteBuf de = Unpooled.copiedBuffer("\n".getBytes());
                    //ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,de));
                    ch.pipeline().addLast(new HttpClientCodec());
                    ch.pipeline().addLast(new HttpObjectAggregator(8192));
                    ch.pipeline().addLast(new ClientHandler());
                }
        });

        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().close().sync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String [] args){

        //final String host = "127.0.0.1" ;
        //final int port = 30000 ;               //db-40的转发

        //final String host = "127.0.0.1" ;
        //final int port = 20001 ;

        final String host = "hz.datastream.netease.com" ;
        final int port = 9228 ;
        int threadNumber = 800;

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        //NettyClient.connect(host,9228);



        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    System.out.println(System.currentTimeMillis()/1000 + "s,flow = " + atomicLong.getAndSet(0L) + ",total = " + total.get() + " , connection = " + connectionNumber);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },1,1, TimeUnit.SECONDS);


        for(int i = 0 ;i < threadNumber ; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    //NettyClient.connect(host,port);
                    try {
                        Random random = new Random();
                        Thread.currentThread().sleep(random.nextInt(1000));
                        WebClient webClient = new WebClient(host,port);
                        Channel ch = webClient.getChannel();
                        webClient.sendMessage(ch);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            });
            t.setName(Integer.valueOf(i).toString());
            t.start();
        }
    }



}
