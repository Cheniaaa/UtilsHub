package netty.demo.protocolDemo.custom.server.handler.init;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.demo.protocolDemo.custom.protocol.MessageCodecSharable;
import netty.demo.protocolDemo.custom.protocol.ProtocolFrameDecoder;
import netty.demo.protocolDemo.custom.server.handler.RpcRequestMessageHandler;

import java.lang.invoke.VarHandle;

/**
 * Description :
 * ProjectName : UtilsHub
 * Author : 聂晨
 * Time: 2022/9/7 20:08:08
 * Version : 1.0
 */

public class RpcServerHandler extends ChannelInitializer<SocketChannel> {

    LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
    MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
    RpcRequestMessageHandler RPC_HANDLER = new RpcRequestMessageHandler();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new ProtocolFrameDecoder())
                .addLast(LOGGING_HANDLER)
                .addLast(MESSAGE_CODEC)
                .addLast(RPC_HANDLER);
    }
}
