package netty.demo.utils;

import io.netty.buffer.ByteBuf;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/25 01:12:12
 * @Version : 1.0
 */

public class ByteBufCustomUtil {

    public static void log(ByteBuf byteBuf) {
        int length = byteBuf.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(byteBuf.readerIndex())
                .append("  write index:").append(byteBuf.writerIndex())
                .append("  capacity:").append(byteBuf.capacity())
                .append(NEWLINE);
        io.netty.buffer.ByteBufUtil.appendPrettyHexDump(buf, byteBuf);
        System.out.println(buf.toString());

    }
}
