package netty.demo.protocolDemo.custom.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;
import netty.demo.protocolDemo.custom.client.handler.RpcClientHandler;
import netty.demo.protocolDemo.custom.client.handler.RpcResponseMessageHandler;
import netty.demo.protocolDemo.custom.message.RpcRequestMessage;
import netty.demo.protocolDemo.custom.protocol.SequenceIdGenerator;
import netty.demo.protocolDemo.custom.server.service.HelloService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description : rpc客户端
 * ProjectName : UtilsHub
 * Author : 聂晨
 * Time: 2022/9/7 15:23:23
 * Version : 1.0
 */
@Slf4j
public class RpcClient {

    private static Channel channel = null;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        HelloService service = getProxyService(HelloService.class);
        service.sayHello();
    }


    //创建动态代理方法
    public static <T> T getProxyService(Class<T> serviceClass) {
        ClassLoader classLoader = serviceClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{serviceClass};
        Object instance = Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            int sequenceId = SequenceIdGenerator.nextId();
            RpcRequestMessage msg = new RpcRequestMessage(
                    sequenceId,
                    serviceClass.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args);
            getChannel().writeAndFlush(msg);


            //准备空的Promise对象用来异步接收结果
            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());
            RpcResponseMessageHandler.PROMISES.put(sequenceId, promise);

            promise.await();

            if (promise.isSuccess()) {
                return promise.getNow();
            } else {
                throw new Exception(promise.cause());
            }

        });
        return (T) instance;
    }


    private static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        RpcClientHandler RPC_CLIENT = new RpcClientHandler();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .channel(NioSocketChannel.class)
                    .group(group)
                    .handler(RPC_CLIENT);

            channel = bootstrap.connect("localhost", 8080).sync().channel();
            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (Exception e) {
            log.error("client error", e);
        }
    }


    //获取唯一的channel,使用单例模式dcl双重判空检测
    private static Channel getChannel() {
        if (channel != null) {
            return channel;
        } else {
            synchronized (LOCK) {
                if (channel == null) {
                    initChannel();
                }
                return channel;

            }
        }
    }
}
