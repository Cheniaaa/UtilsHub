package netty.demo.channelFutureDemo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/22 01:27:27
 * @Version : 1.0
 */
@Slf4j
public class JdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<Object> future = pool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(2000);
                return 50;
            }
        });

        log.debug("等待结果");
        log.debug("结果是：{}",future.get());//jdk的future的get方法是阻塞的
        log.debug("结束");
        pool.shutdown();
    }
}
