package com.hk.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.management.ThreadMXBean;
import java.net.InetSocketAddress;

/**
 * @author 胡冉
 * @ClassName WSServer
 * @Description: websocket服务
 * @Date 2019/5/14 11:03
 * @Version 2.0
 */
@Slf4j
@Component
public class WsServer {
    @PostConstruct
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ServerBootstrap b = new ServerBootstrap();
                        // 开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
                        b.group(bossGroup, workGroup).option(ChannelOption.TCP_NODELAY, true)
                                .childOption(ChannelOption.SO_KEEPALIVE, true)
                                //表示是否开始Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，
                                // 有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                                .childOption(ChannelOption.TCP_NODELAY, true)
                                // netty提供了2种接受缓存区分配器，FixedRecvByteBufAllocator是固定长度，但是拓展，AdaptiveRecvByteBufAllocator动态长度
                                .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048));
                        b.channel(NioServerSocketChannel.class);
                        b.childHandler(new VideoServerInit());
                        log.info("===>>>服务端已经开启,等待客户端连接");
                        ChannelFuture future = b.bind(new InetSocketAddress(8585)).sync();
                        future.channel().closeFuture().sync();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // 优雅的退出程序
                        bossGroup.shutdownGracefully();
                        workGroup.shutdownGracefully();
                    }
                }
            }).start();

    }

}


