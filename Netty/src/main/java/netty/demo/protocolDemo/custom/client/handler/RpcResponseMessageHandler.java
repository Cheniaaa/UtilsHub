package netty.demo.protocolDemo.custom.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import netty.demo.protocolDemo.custom.message.RpcResponseMessage;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/8 01:41:41
 * @Version : 1.0
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    public static final ConcurrentHashMap<Integer, Promise<Object>> PROMISES = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        log.debug("{}", msg);
        // 拿到空的 promise
        Promise<Object> promise = PROMISES.remove(msg.getSequenceId());
        if (promise != null) {
            Object returnValue = msg.getReturnValue();
            Object exceptionValue = msg.getExceptionValue();
            if (exceptionValue != null) {
                promise.setFailure(new Throwable((String) exceptionValue));
            } else {
                promise.setSuccess(returnValue);
            }
        }
    }
}

