package netty.demo.protocolDemo.custom.protocol;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/8 01:16:16
 * @Version : 1.0
 */

public class SequenceIdGenerator {
    private static final AtomicInteger id = new AtomicInteger();

    public static int nextId() {
        return id.incrementAndGet();
    }
}
