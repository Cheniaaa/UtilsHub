package socket.dto;

import lombok.Data;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/31 02:08:08
 * @Version : 1.0
 */
@Data
public class Message {
    private String userName;
    private Object data;
}
