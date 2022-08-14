package nio.nioDemo.nioWrite;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Description : 可写事件用户端
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/15 02:11:11
 * @Version : 1.0
 */

public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8080));

        int count = 0;
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        while (true) {
            count += socketChannel.read(buffer);
            System.out.println(count);
            buffer.clear();
        }

    }


}
