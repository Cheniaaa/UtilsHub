package nio.demo.demo1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description : 测试byteBuffer
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/7/24 02:15:15
 * @Version : 1.0
 */

public class TestByteBuffer {
    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream(new File("E:\\ProjectData\\Study_Material\\StudyTestDemo\\JavaProject\\UtilsHub\\Netty\\src\\main\\java\\nio\\demo1\\data.txt")).getChannel()) {
            //准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (channel.read(buffer) != -1) {
                buffer.flip();//切换读模式
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.print((char) b);
                }
                buffer.clear();//切换写模式（position指针回到起点）（重头开始写）
                //buffer.compact()//切换写模式（position指针到未读数据的后面继续写）
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
