package netty.demo.byteBufDemo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

/**
 * @Description : 零拷贝方法
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/29 01:14:14
 * @Version : 1.0
 */

public class ByteBufZeroCopy {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();

       buffer.writeBytes(new byte[]{'a', 'b', 'c'});

       /*
       slice()分片
       duplicate()
       compositeBuffer()使用该Buffer
        */
        ByteBuf buffer1 = ByteBufAllocator.DEFAULT.buffer();
        ByteBuf buffer2 = ByteBufAllocator.DEFAULT.buffer();
        CompositeByteBuf byteBuffers = ByteBufAllocator.DEFAULT.compositeBuffer();
        byteBuffers.addComponents(true, buffer1, buffer2);

    }
}
