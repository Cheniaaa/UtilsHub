package netty.demo.protocolDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @Description : http协议
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/31 23:26:26
 * @Version : 1.0
 */
@Slf4j
public class HttpProtocol {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .channel(NioServerSocketChannel.class)
                    .group(boss, worker)
                    .childHandler(new ChannelInitializer<NioServerSocketChannel>() {
                        @Override
                        protected void initChannel(NioServerSocketChannel sc) throws Exception {
                            sc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            sc.pipeline().addLast(new HttpServerCodec());

//                            sc.pipeline().addLast(new ChannelInboundHandlerAdapter() {
//                                @Override
//                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                    log.debug("{}", msg);
//                                }
//                            });


                            //可以使用SimpleChannelInboundHandler指定需要的类型
                            sc.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                    log.debug(msg.uri());
                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                    byte[] bytes = "<h1>hello</h1>".getBytes();
                                    response.content().writeBytes(bytes);
                                    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
                                    ctx.writeAndFlush(response);
                                }
                            });
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8080)).sync();
            Channel channel = channelFuture.channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
