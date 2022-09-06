package netty.demo.protocolDemo.custom.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import netty.demo.protocolDemo.custom.client.handler.ChatClientHandler;


/**
 * @Description : 聊天案例客户端
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/4 16:56:56
 * @Version : 1.0
 */
@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChatClientHandler CLIENT_HANDLER = new ChatClientHandler();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .channel(NioSocketChannel.class)
                    .group(group)
                    .handler(CLIENT_HANDLER);

            Channel channel = bootstrap.connect("localhost", 8080).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
