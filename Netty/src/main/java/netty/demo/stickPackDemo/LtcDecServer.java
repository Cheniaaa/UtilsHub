package netty.demo.stickPackDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description : 基于字段的解码器
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/31 18:08:08
 * @Version : 1.0
 */

public class LtcDecServer {

    /**
     * lengthFieldOffset 长度字段偏移量
     * lengthFieldLength 长度字段长度
     * lengthAdjustment 长度适应（长度字节后还有几个字节是内容）
     * initialBytesToStrip 从头剥离几个字节
     */

    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 1, 4)
                , new LoggingHandler());

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer, "hello");
        send(buffer, "woc");
        channel.writeInbound(buffer);

    }


    public static void send(ByteBuf byteBuf, String content) {
        byte[] bytes = content.getBytes();


        byteBuf.writeInt(bytes.length);//存放长度
        byteBuf.writeByte(1);//添加自定义可变内容
        byteBuf.writeBytes(bytes);//存放内容


    }
}
