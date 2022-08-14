package nio.byteBufferDemo;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Description : ByteBuffer常见方法
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/8 21:25:25
 * @Version : 1.0
 */

public class ByteBufferMethod {

    public static void main(String[] args) throws Exception {

        /*
        分配空间
         */
        ByteBuffer allocate = ByteBuffer.allocate(16);//分配堆内存空间
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(16);//分配直接内存空间（不归jvm管理）



        /*
        向buffer写入数据
         */
        FileChannel channel = new FileInputStream("").getChannel();
        channel.read(allocate);//调用channel的read写入

        allocate.put((byte) 127);//调用buffer方法


        /*
        从buffer读数据
         */
        channel.write(allocate);

        allocate.get();//后移指针
        allocate.get(1);//不会后移指针

        allocate.rewind();//将position指针重置0
        allocate.mark();//标记
        allocate.reset();//重置到标记处


        /*
        读写切换
         */
        allocate.flip();//切换为读模式（指针指向0）
        allocate.clear();//切换为写模式（相当于清除）
        allocate.compact();//切换写模式（在已有数据后面继续写）


        /*
        字符串与bytebuffer互转（3种）
         */
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes(StandardCharsets.UTF_8));

        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");

        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));


        String str = StandardCharsets.UTF_8.decode(buffer3).toString();//bytebuffer转字符串



    }
}
