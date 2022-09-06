package netty.demo.protocolDemo.custom.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.demo.protocolDemo.custom.message.LoginRequestMessage;
import netty.demo.protocolDemo.custom.protocol.MessageCodec;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/2 19:21:21
 * @Version : 1.0
 */

public class TestMessageProtocol {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024,12,4,0,0)
                ,new LoggingHandler(LogLevel.DEBUG)
                ,new MessageCodec());

        //encode
        LoginRequestMessage message = new LoginRequestMessage("NieChen", "12345");
        channel.writeOutbound(message);

        //decode
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buffer);
        channel.writeInbound(buffer);
    }
}
