package nio.nioDemo.nioSelector;

import nio.utils.ByteBufferUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Description : 服务器
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/12 00:13:13
 * @Version : 1.0
 */

public class Server {

    /**
     * nio是同步非阻塞
     * selector的作用是避免cpu性能的浪费
     * 1.accept：有客户端连接请求时触发
     * 2.connect：客户端建立连接后触发
     * 3.read：可读事件
     * 4.write：可写事件
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open(); //创建selector，管理多个channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//打开服务器端的连接
        serverSocketChannel.configureBlocking(false);//设置非阻塞

        /*
        建立selector和channel的联系（注册）
        selectionKey用于事件标识，知道哪个channel发生事件
         */
        SelectionKey sscKey = serverSocketChannel.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);//设置触发类型

        serverSocketChannel.bind(new InetSocketAddress(8080));

        while (true) {
            selector.select();//当有事件时线程恢复运行

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();//处理完一个key需要手动移出

                //key.cancel();取消事件（事件要么执行，要么取消，不能不处理）

                //通过selectorKey获取响应的事件channel
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();

                /*
                处理事件
                 */
                    socketChannel.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    SelectionKey scKey = socketChannel.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    scKey.attach(buffer);//关联附件（使得每一个都有相对应的buffer）
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();

                        ByteBuffer buffer = (ByteBuffer) key.attachment();

                        int read = channel.read(buffer);
                        if (read == -1) {//客户端正常断开
                            key.cancel();
                        } else {
                            //处理事件
                            splitData(buffer);

                            //扩容
                            if (buffer.position() == buffer.limit()) {
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {//客户端异常断开
                        e.printStackTrace();
                        key.cancel();//客户端断开，因此需要取消事件
                    }
                }
            }
        }
    }


    /*
    简单的数据操作
     */
    public static void splitData(ByteBuffer buffer) {
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            if (buffer.get(i) == '\n') {
                int length = i + 1 - buffer.position();
                ByteBuffer target = ByteBuffer.allocate(length);

                for (int i1 = 0; i1 < length; i1++) {
                    target.put(buffer.get());
                }
                ByteBufferUtils.debugAll(target);
            }
        }
        buffer.compact();
    }
}
