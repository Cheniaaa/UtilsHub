package netty.demo.protocolDemo.custom.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import netty.demo.protocolDemo.custom.message.RpcRequestMessage;
import netty.demo.protocolDemo.custom.message.RpcResponseMessage;
import netty.demo.protocolDemo.custom.server.service.HelloService;
import netty.demo.protocolDemo.custom.server.service.ServicesFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description :
 * ProjectName : UtilsHub
 * Author : 聂晨
 * Time: 2022/9/7 20:23:23
 * Version : 1.0
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) {
        log.debug("{}", msg);
        //反射调用方法
        RpcResponseMessage response = new RpcResponseMessage();
        response.setSequenceId(msg.getSequenceId());
        try {
            Class<?> name = Class.forName(msg.getInterfaceName());
            HelloService service = (HelloService)ServicesFactory.getService(name);
            Method method = service.getClass().getMethod(msg.getMethodName(), msg.getParameterTypes());
            Object invoke = method.invoke(service, msg.getParameterValue());
            response.setReturnValue(invoke);
        } catch (Exception e) {
            String message = e.getMessage();
            response.setExceptionValue("远程调用出错" + message);
        }
        ctx.writeAndFlush(response);
    }
}
