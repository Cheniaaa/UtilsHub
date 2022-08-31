package netty.demo.eventloopDemo.common;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Description : 普通，定时任务
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/19 01:58:58
 * @Version : 1.0
 */

@Slf4j
public class CommonEventLoop {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(2);//可执行io事件，普通任务，定时任务，参数为线程数
        //DefaultEventLoopGroup group = new DefaultEventLoopGroup();普通任务，定时任务

        EventLoop eventLoop = group.next();//获取事件循环对象


        /*
        执行普通任务
         */
        group.next().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("ok");
            }
        });
        log.debug("main");


        /*
        执行定时任务
         */
        group.next().scheduleAtFixedRate(() -> {
            log.debug("ok");
        }, 0, 1, TimeUnit.SECONDS);//可以说明一个任务由一个eventLoop执行
        log.debug("main");
    }
}
