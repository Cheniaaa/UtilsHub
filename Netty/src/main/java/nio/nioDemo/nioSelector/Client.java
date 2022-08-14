package nio.nioDemo.nioSelector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Description : 客户端
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/12 01:16:16
 * @Version : 1.0
 */

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8080));
        socketChannel.write(Charset.defaultCharset().encode("hello15888962237\n122121223123132132132123123"));
        socketChannel.write(Charset.defaultCharset().encode("h112\n"));
        System.in.read();
    }
}
