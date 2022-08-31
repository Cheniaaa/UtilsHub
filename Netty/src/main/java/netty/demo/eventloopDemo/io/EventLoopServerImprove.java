package netty.demo.eventloopDemo.io;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @Description : 细化版server
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/19 02:42:42
 * @Version : 1.0
 */
@Slf4j
public class EventLoopServerImprove {
    public static void main(String[] args) {

        //当某一个任务耗时比较多，可以采用开辟其他的eventLoop用于处理
        DefaultEventLoop eventLoop = new DefaultEventLoop();

        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                nioSocketChannel.pipeline().addLast("handler1",new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf byteBuf = (ByteBuf) msg;
                                        log.debug(byteBuf.toString(StandardCharsets.UTF_8));
                                        ctx.fireChannelRead(msg);//传递消息
                                    }
                                }).addLast(eventLoop,"handler2",new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println("hhh");
                                    }
                                });
                            }
                        }
                )
                .bind(new InetSocketAddress(8080));
    }
}
