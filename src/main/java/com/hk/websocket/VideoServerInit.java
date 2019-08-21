package com.hk.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author 胡冉
 * @ClassName VideoServerInit
 * @Date 2019/5/14 11:08
 * @Version 2.0
 */
public class VideoServerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //websocket基于http协议 基于编解码器
        pipeline.addLast(new HttpServerCodec());
        //对于写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对http message进行聚合，聚合成FullHttpRequest或者ullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        //以上是用于支持http协议

        //指定客户端访问的一个路由 /ws 会帮你处理一些复杂的事情  握手动作 handshaking    close ping pong
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        /**
         * 自定义Handler
         */
        pipeline.addLast(new VideoHandler());

    }
}
