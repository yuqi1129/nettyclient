package com.yuqi.nettyclient.testdatareceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * Created with nettyclient.
 * User: hzyuqi1
 * Date: 2016/12/22
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */

public class DefaultClientHandler extends AbstractClientHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractClientHandler.class.getName());

    public DefaultClientHandler(WebSocketClientHandshaker handshaker) {
        super(handshaker);
    }

    @Override
    public void resultHandle(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            logger.info("WebSocket Client received message: {}", textFrame.text());
        } else if (frame instanceof PongWebSocketFrame) {
            logger.info("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            logger.info("WebSocket Client received closing");
            ctx.channel().close();
        }
    }
}