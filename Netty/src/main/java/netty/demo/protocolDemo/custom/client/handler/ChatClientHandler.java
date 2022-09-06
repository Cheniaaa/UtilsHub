package netty.demo.protocolDemo.custom.client.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import netty.demo.protocolDemo.custom.message.*;
import netty.demo.protocolDemo.custom.protocol.MessageCodecSharable;
import netty.demo.protocolDemo.custom.protocol.ProtocolFrameDecoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/6 01:11:11
 * @Version : 1.0
 */
@Slf4j
public class ChatClientHandler extends ChannelInitializer<SocketChannel> {
    MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
    Scanner scanner = new Scanner(System.in);
    AtomicBoolean IsLogin = new AtomicBoolean(false);
    CountDownLatch WAIT_LOGIN = new CountDownLatch(1);
    AtomicBoolean EXIT = new AtomicBoolean(false);

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ProtocolFrameDecoder());
        ch.pipeline().addLast(new LoggingHandler());
        ch.pipeline().addLast(MESSAGE_CODEC);
        //时长要比服务端短一些，作为心跳信号
        ch.pipeline().addLast(new IdleStateHandler(0, 7, 0));
        // ChannelDuplexHandler 可以同时作为入站和出站处理器
        ch.pipeline().addLast(new ChannelDuplexHandler() {
            // 用来触发特殊事件
            @Override
            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                IdleStateEvent event = (IdleStateEvent) evt;
                // 触发了写空闲事件
                if (event.state() == IdleState.WRITER_IDLE) {
                    ctx.writeAndFlush(new PingMessage());//心跳包
                }
            }
        });

        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.debug("接收到消息：{}", msg);
                if (msg instanceof LoginResponseMessage message) {
                    if (message.isSuccess()) {
                        IsLogin.set(true);
                    }
                    WAIT_LOGIN.countDown();
                }
            }

            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {

                new Thread(() -> {
                    System.out.println("请输入用户名：");
                    String username = scanner.nextLine();
                    if (EXIT.get()) {
                        return;
                    }
                    System.out.println("请输入密码：");
                    String password = scanner.nextLine();
                    if (EXIT.get()) {
                        return;
                    }
                    LoginRequestMessage loginMessage = new LoginRequestMessage(username, password);
                    ctx.writeAndFlush(loginMessage);
                    System.out.println("waiting....");


                    try {
                        WAIT_LOGIN.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!IsLogin.get()) {
                        ctx.channel().close();
                        return;
                    }
                    System.out.println("登录成功");

                    while (true) {
                        System.out.println("==================================");
                        System.out.println("send [username] [content]");
                        System.out.println("gSend [group name] [content]");
                        System.out.println("gCreate [group name] [m1,m2,m3...]");
                        System.out.println("gMembers [group name]");
                        System.out.println("gJoin [group name]");
                        System.out.println("gQuit [group name]");
                        System.out.println("quit");
                        System.out.println("==================================");
                        String command = null;
                        try {
                            command = scanner.nextLine();
                        } catch (Exception e) {
                            break;
                        }
                        if (EXIT.get()) {
                            return;
                        }

                        String[] s = command.split(" ");
                        switch (s[0]) {
                            case "send" -> ctx.writeAndFlush(new ChatRequestMessage(username, s[1], s[2]));
                            case "gSend" -> ctx.writeAndFlush(new GroupChatRequestMessage(username, s[1], s[2]));
                            case "gCreate" -> {
                                List<String> members = Arrays.stream(s[2].split(",")).toList();
                                HashSet<String> memberSet = new HashSet<>(members);
                                memberSet.add(username);
                                ctx.writeAndFlush(new GroupCreateRequestMessage(s[1], memberSet));
                            }
                            case "gMembers" -> ctx.writeAndFlush(new GroupMembersRequestMessage(s[1]));
                            case "gJoin" -> ctx.writeAndFlush(new GroupJoinRequestMessage(username, s[1]));
                            case "gQuit" -> ctx.writeAndFlush(new GroupQuitRequestMessage(username, s[1]));
                            case "quit" -> {
                                ctx.channel().close();
                                return;
                            }
                        }
                    }
                }, "login").start();
            }

            /*
            服务器端异常或是断开客户端
             */
            @Override
            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                log.debug("连接断开....");
                EXIT.set(true);
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                log.debug("异常断开...{}", cause.getMessage());
                EXIT.set(true);
            }
        });
    }
}
