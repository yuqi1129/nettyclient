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

        String [] tailFile = {"nrpt1.photo.163.org","nrpt0.photo.163.org"};
        String [] route = {"hzayq-sc2.server.163.org","nrpt1.photo.163.org"};
        String [] handler = {"hzayq-sc3.server.163.org","app72v1.photo.163.org"};

        String [] tagname = {"qatest_lanyx0108_10","qatest_lanyx0108_9","qatest_lanyx0108_8","qatest_lanyx0108_7"};


        for(;;){
            //for(int i = 0; i< 1000 ;i++){

            for (int i = 0 ;i < 10; i++) {

                int tagIndex = random.nextInt(4);
                int tailfileHostname = random.nextInt(2);

                //Tag weidu

                //Tailfile out
                TagOutStatisticsMessage message = new TagOutStatisticsMessage(tagname[tagIndex], "xxx", "10000", random.nextInt(5) + 1, (random.nextInt(5) + 1) * 100);
                BaseMessage baseMessage = new BaseMessage(8, tailFile[tailfileHostname], 1, new Date().getTime(), JSON.toJSONString(message));

                // dataroute in
                TagInStatisticsMessage message1 = new TagInStatisticsMessage(tagname[tagIndex],random.nextInt(5) + 1, (random.nextInt(5) + 1) * 100);
                BaseMessage baseMessage1 = new BaseMessage(7, route[tailfileHostname], 2, new Date().getTime(), JSON.toJSONString(message1));

                // data handler in
                TagInStatisticsMessage message2 = new TagInStatisticsMessage(tagname[tagIndex],random.nextInt(5) + 1, (random.nextInt(5) + 1) * 100);
                BaseMessage baseMessage2 = new BaseMessage(7, handler[tailfileHostname], 3, new Date().getTime(), JSON.toJSONString(message2));



                //hanler out flow
                TagOutFlowStatisticsMessage tagMq = new TagOutFlowStatisticsMessage(tagname[tagIndex],random.nextInt(5) + 1, (random.nextInt(5) + 1) * 100,5);
                BaseMessage baseTagMq = new BaseMessage(10, handler[tailfileHostname], 3, new Date().getTime(), JSON.toJSONString(tagMq));

                TagOutFlowStatisticsMessage tagHbase = new TagOutFlowStatisticsMessage(tagname[tagIndex],random.nextInt(5) + 1, (random.nextInt(5) + 1) * 100,4);
                BaseMessage baseTagHbase = new BaseMessage(10, handler[tailfileHostname], 3, new Date().getTime(), JSON.toJSONString(tagHbase));

                TagOutFlowStatisticsMessage tagHDFS = new TagOutFlowStatisticsMessage(tagname[tagIndex],random.nextInt(5) + 1, (random.nextInt(5) + 1) * 100,3);
                BaseMessage baseTagHDFS = new BaseMessage(10, handler[tailfileHostname], 3, new Date().getTime(), JSON.toJSONString(tagHDFS));

                // node 维度

                //route in
                NodeInStatisticsMessage routein = new NodeInStatisticsMessage(random.nextInt(5) + 1, (random.nextInt(5) + 1) * 100);
                BaseMessage baseRouteIn = new BaseMessage(5,route[tailfileHostname],2,new Date().getTime(),JSON.toJSONString(routein));
                //rout out
                NodeInStatisticsMessage routeout = new NodeInStatisticsMessage(random.nextInt(5) + 1, (random.nextInt(5) + 1) * 100);
                BaseMessage baseRodeOut = new BaseMessage(6,route[tailfileHostname],2,new Date().getTime(),JSON.toJSONString(routeout));


                // hadnler in
                NodeInStatisticsMessage hanlerin = new NodeInStatisticsMessage(random.nextInt(5) + 1, (random.nextInt(5) + 1) * 100);
                BaseMessage baseHandleIn = new BaseMessage(5,handler[tailfileHostname],3,new Date().getTime(),JSON.toJSONString(hanlerin));

                //handler out flow
                NodeOutFlowStatisticsMessage nodeOutFlowStatisticsMessage = new NodeOutFlowStatisticsMessage();
                nodeOutFlowStatisticsMessage.setHbaseFlowCount(random.nextInt(5) + 1);
                nodeOutFlowStatisticsMessage.setMqFlowCount(random.nextInt(5) + 1);
                nodeOutFlowStatisticsMessage.setHdfsFlowCount(random.nextInt(5) + 1);
                nodeOutFlowStatisticsMessage.setHbaseFlowSize((random.nextInt(5) + 1) * 100);
                nodeOutFlowStatisticsMessage.setHdfsFlowSize((random.nextInt(5) + 1) * 100);
                nodeOutFlowStatisticsMessage.setMqFlowSize((random.nextInt(5) + 1) * 100);
                BaseMessage handlerout = new BaseMessage(9,handler[tailfileHostname],3,new Date().getTime(),JSON.toJSONString(nodeOutFlowStatisticsMessage));

                try {
                    Thread.currentThread().sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseMessage))); // tailfile out
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseMessage1))); // tag dataroute in
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseMessage2))); // tag handle in

                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(tagMq)));  // tag mq
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(tagHbase))); // tag hbase
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(tagHDFS))); // tag hdfs

                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(routein))); // node route in
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(routeout))); // node route out
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseHandleIn))); // node handle in

                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(nodeOutFlowStatisticsMessage))); // node handler flow

                NettyClient.atomicLong.getAndAdd(12);
                NettyClient.total.getAndAdd(12);
            }


            try {
                Thread.currentThread().sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //context.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(baseMessage)));
        }

    }




}
