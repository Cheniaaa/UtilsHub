package netty.demo.protocolDemo.custom.server.handler.init;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import netty.demo.protocolDemo.custom.protocol.MessageCodecSharable;
import netty.demo.protocolDemo.custom.protocol.ProtocolFrameDecoder;
import netty.demo.protocolDemo.custom.server.handler.*;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/6 01:27:27
 * @Version : 1.0
 */
@Slf4j
public class ChatServerHandler extends ChannelInitializer<SocketChannel> {
    LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
    MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
    LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
    ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();
    GroupCreateMessageHandler GROUP_CREATE = new GroupCreateMessageHandler();
    GroupChatMessageHandler GROUP_CHAT = new GroupChatMessageHandler();
    GroupMemberMessageHandler GROUP_MEMBER = new GroupMemberMessageHandler();
    GroupJoinMessageHandler GROUP_JOIN = new GroupJoinMessageHandler();
    GroupQuitMessageHandler GROUP_QUIT = new GroupQuitMessageHandler();
    QuitHandler QUIT_HANDLER = new QuitHandler();


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ProtocolFrameDecoder());
        ch.pipeline().addLast(LOGGING_HANDLER);
        ch.pipeline().addLast(MESSAGE_CODEC);
        //判断读空闲过长，会触发事件
        ch.pipeline().addLast(new IdleStateHandler(10, 0, 0));
        // ChannelDuplexHandler 可以同时作为入站和出站处理器
        ch.pipeline().addLast(new ChannelDuplexHandler() {
            // 用来触发特殊事件
            @Override
            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                IdleStateEvent event = (IdleStateEvent) evt;
                // 触发了读空闲事件
                if (event.state() == IdleState.READER_IDLE) {
                    log.debug("已经 10s 没有读到数据了");
                    ctx.channel().close();
                }
            }
        });
        ch.pipeline().addLast(LOGIN_HANDLER);
        ch.pipeline().addLast(CHAT_HANDLER);
        ch.pipeline().addLast(GROUP_CREATE);
        ch.pipeline().addLast(GROUP_MEMBER);
        ch.pipeline().addLast(GROUP_JOIN);
        ch.pipeline().addLast(GROUP_QUIT);
        ch.pipeline().addLast(GROUP_CHAT);
        ch.pipeline().addLast(QUIT_HANDLER);
    }
}
