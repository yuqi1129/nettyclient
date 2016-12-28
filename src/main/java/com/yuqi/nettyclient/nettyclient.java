package com.yuqi.nettyclient;

import com.yuqi.nettyclient.common.TimeServerHandler;
import com.yuqi.nettyclient.common.TimeServerHandler4_2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/10/9
 * Time: 20:56
 * To change this template use File | Settings | File Templates.
 */

public class nettyclient {

    public void connect(int port,String host) throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).option(ChannelOption.TCP_NODELAY,true).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>(){

                        @Override
                        public void initChannel(SocketChannel ch){
                            //ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ByteBuf de = Unpooled.copiedBuffer("$_".getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,de));
                            //ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new TimeServerHandler4_2());
                        }
                    });

            ChannelFuture futrue = bootstrap.connect(host,port).sync();
            futrue.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String [] args){
        int port = 9001;
        if (args != null && args.length > 0){
            port = Integer.valueOf(args[0]);
        }
        System.out.println("Line separator:" + System.getProperty("line.separator"));
        try {
            new nettyclient().connect(port,"127.0.0.1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
