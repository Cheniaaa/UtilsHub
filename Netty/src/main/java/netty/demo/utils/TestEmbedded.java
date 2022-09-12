package netty.demo.utils;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/10 02:22:22
 * @Version : 1.0
 */

public class TestEmbedded {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler());
        channel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("HelloWorld".getBytes()));
    }
}
