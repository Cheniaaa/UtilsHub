package netty.demo.byteBufDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.logging.LoggingHandler;
import netty.demo.utils.ByteBufCustomUtil;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/25 01:08:08
 * @Version : 1.0
 */

public class CreateByteBuf {
    public static void main(String[] args) {

//        System.setProperty("io.netty.allocator.type", "unpooled");//关闭池化功能

        ByteBuf buffer1 = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buffer1.getClass());//可以看出默认开启池化功能
        buffer1.writeBytes("hello".getBytes());
        ByteBufCustomUtil.log(buffer1);


//        ByteBuf buffer2 = ByteBufAllocator.DEFAULT.heapBuffer(10);//分配堆内存
//        ByteBuf buffer3 = ByteBufAllocator.DEFAULT.directBuffer(10);//分配直接内存

    }
}
