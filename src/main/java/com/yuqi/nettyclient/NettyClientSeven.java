package com.yuqi.nettyclient;

import com.yuqi.nettyclient.common.ClientSevenHanler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/10/23
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */

public class NettyClientSeven {

    private int port = 9001 ;
    public void connect(String host,int port){

        EventLoopGroup client = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(client).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel ch){
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new ClientSevenHanler());
                        }
            });

            ChannelFuture future = bootstrap.connect(host,port).sync();
            future.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            client.shutdownGracefully();
        }
    }


    public static void main(String [] args){
        String host = "127.0.0.1";
        int  port = 9001;

        new NettyClientSeven().connect(host,port);
    }
}
