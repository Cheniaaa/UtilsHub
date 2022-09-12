package netty.demo.protocolDemo.custom.client.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LoggingHandler;
import netty.demo.protocolDemo.custom.protocol.MessageCodecSharable;
import netty.demo.protocolDemo.custom.protocol.ProtocolFrameDecoder;

/**
 * Description :
 * ProjectName : UtilsHub
 * Author : 聂晨
 * Time: 2022/9/7 20:13:13
 * Version : 1.0
 */

public class RpcClientHandler extends ChannelInitializer<SocketChannel> {
    LoggingHandler LOGGING_HANDLER = new LoggingHandler();
    MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
    RpcResponseMessageHandler RPC_HANDLER = new RpcResponseMessageHandler();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new ProtocolFrameDecoder())
                .addLast(LOGGING_HANDLER)
                .addLast(MESSAGE_CODEC)
                .addLast(RPC_HANDLER);
    }
}
