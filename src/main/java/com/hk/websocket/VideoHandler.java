package com.hk.websocket;


import com.google.common.collect.Maps;
import com.hk.common.ObjectMaping;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


/**
 * @author 胡冉
 * @ClassName VideoHandler
 * @Date 2019/5/14 11:04
 * @Version 2.0
 */
@Slf4j
@ChannelHandler.Sharable
public class VideoHandler extends SimpleChannelInboundHandler<Object> {
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final int STATUS_CODE_200 = 200;
    //  private static final  <>
    private static Map<String, String> USER_ID_VIDEO = Maps.newConcurrentMap();
    private WebSocketServerHandshaker handshake;
    private static final String WEB_SOCKET_STRING = "websocket";
    private static final String UPGRADE_STRING = "Upgrade";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 处理客户端websocket连接业务
        if (msg instanceof WebSocketFrame) {
            handWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
        // 处理客户端向服务端发起的Http握手请求的业务
        if (msg instanceof FullHttpRequest) {
            handHttpRequest(ctx, (FullHttpRequest) msg);
        }
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /**
         * 当客户端连接 后 获取客户端的channel 放到group中进行管理
         */
        System.out.println("收到连接:" + ctx.channel().id().asLongText());
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("关闭连接:" + ctx.channel().id().asLongText());
        String userId = USER_ID_VIDEO.get(ctx.channel().id().asLongText());
        if (ObjectMaping.mapVeidoIdAndProcess.containsKey(userId)) {
            Process process = ObjectMaping.mapVeidoIdAndProcess.get(userId);
            process.destroy();
            process.getErrorStream().close();
            process.getInputStream().close();
            process.getOutputStream().close();
        }
        USER_ID_VIDEO.remove(ctx.channel().id().asLongText());
        clients.remove(ctx.channel());
    }

    private void handWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws InterruptedException {
        // 判断是否是关闭的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshake.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
        }
        // 判断是否为ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
        }
        // 判断是否为二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            log.error("[{}]:目前暂不支持二进制消息", this.getClass().getName());
        }
        // 获取客户端向服务端发送的消息
        String data = ((TextWebSocketFrame) frame).text();
        System.out.println(((TextWebSocketFrame) frame).text());
        USER_ID_VIDEO.put(ctx.channel().id().asLongText(), data);
        log.info("服务端收到客户端的消息==========>>>[{}]", data);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    private void handHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.getDecoderResult().isSuccess()
                || !(WEB_SOCKET_STRING.equals(request.headers().get(UPGRADE_STRING)))) {
            sendHttpResponse(ctx, request,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("", null, false);
        handshake = wsFactory.newHandshaker(request);
        if (null == handshake) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshake.handshake(ctx.channel(), request);
        }

    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request,
                                  DefaultFullHttpResponse response) {
        if (response.getStatus().code() != STATUS_CODE_200) {
            ByteBuf byteBuf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(byteBuf);
            //  byteBuf.release();
        }
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(request);
        if (response.getStatus().code() != STATUS_CODE_200) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
