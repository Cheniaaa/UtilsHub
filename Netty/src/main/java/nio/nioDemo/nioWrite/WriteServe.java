package nio.nioDemo.nioWrite;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @Description : 服务器处理可写事件
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/15 01:54:54
 * @Version : 1.0
 */

public class WriteServe {
    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        SelectionKey sscKey = serverSocketChannel.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);//设置触发类型

        serverSocketChannel.bind(new InetSocketAddress(8080));

        while (true) {
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();

                    socketChannel.configureBlocking(false);
                    SelectionKey scKey = socketChannel.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);

                    /*
                    向客户端发送大量的数据
                     */
                    StringBuffer str = new StringBuffer();
                    str.append("a".repeat(5000000));
                    ByteBuffer buffer = Charset.defaultCharset().encode(str.toString());
                    int write = socketChannel.write(buffer);
                    System.out.println(write);

                    //每次发送的数量有限，判断是否还有剩余
                    if (buffer.hasRemaining()) {
                        scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        scKey.attach(buffer);
                        System.out.println(key.interestOps());
                    }

                } else if (key.isWritable()) {
                    //接收到可读事件
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(buffer);
                    System.out.println(write);

                    if (!buffer.hasRemaining()) {
                        key.attach(null);
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                        System.out.println(key.interestOps());

                    }

                }
            }
        }
    }
}
