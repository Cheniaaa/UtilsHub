import nio.utils.ByteBufferUtils;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/10 23:47:47
 * @Version : 1.0
 */

public class Test2 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());
        ByteBufferUtils.debugAll(buffer);
        System.out.println(ByteBufferUtils.getUnsignedChar(buffer, 0));
    }
}
