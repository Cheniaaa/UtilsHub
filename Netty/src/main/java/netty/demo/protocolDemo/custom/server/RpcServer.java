package netty.demo.protocolDemo.custom.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import netty.demo.protocolDemo.custom.server.handler.init.RpcServerHandler;

/**
 * Description : rpc服务端
 * ProjectName : UtilsHub
 * Author : 聂晨
 * Time: 2022/9/7 15:23:23
 * Version : 1.0
 */
@Slf4j
public class RpcServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        RpcServerHandler RPC_SERVER = new RpcServerHandler();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .channel(NioServerSocketChannel.class)
                    .group(boss, worker)
                    .childHandler(RPC_SERVER);

            Channel channel = serverBootstrap.bind(8080).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
