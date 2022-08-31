package netty.demo.channelFutureDemo;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/22 01:41:41
 * @Version : 1.0
 */
@Slf4j
public class NettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();

        /*
        promise可以指定成功和失败后的值，在future的基础上可以更加灵活
         */
        DefaultPromise<Object> promise = new DefaultPromise<>(eventLoop);

        new Thread(() ->{
            log.debug("开始计算");
            try {

                int n = 1 / 0;
                Thread.sleep(2000);
                promise.setSuccess(80);
            } catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }

        }).start();

        log.debug("等待结果");
        log.debug("结果是：{}", promise.get());
    }
}
