package com.ml;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.example.socksproxy.SocksServerInitializer;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Panda.HuangWei.
 * @since 2018-09-05 22:34.
 */
public final class CustomProxyStart {
    static final int PORT = Integer.parseInt(System.getProperty("port", "1080"));


    public CustomProxyStart() {
    }

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        int port = PORT;
        if (args != null && args.length >= 1) {
            port = Integer.valueOf(args[0]);
        }
        try {
            ServerBootstrap b = new ServerBootstrap();
            ((ServerBootstrap) ((ServerBootstrap) b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)).handler(new LoggingHandler(LogLevel.INFO))).childHandler(new SocksServerInitializer());
            b.bind(port).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
