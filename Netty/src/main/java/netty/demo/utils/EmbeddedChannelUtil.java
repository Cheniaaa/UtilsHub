package netty.demo.utils;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description :handler测试工具
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/25 00:51:51
 * @Version : 1.0
 */
@Slf4j
public class EmbeddedChannelUtil {
    public static void main(String[] args) {
        ChannelInitializer<Channel> h1 = new ChannelInitializer<>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.debug("111");
                        super.channelRead(ctx, msg);
                    }
                }).addLast(new ChannelOutboundHandlerAdapter() {
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        log.debug("222");
                    }
                });
            }

        };

        /*
        该类可以用于测试handler
         */
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(h1);
        //embeddedChannel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("hello".getBytes()));
        embeddedChannel.writeOutbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("world".getBytes()));

    }
}
