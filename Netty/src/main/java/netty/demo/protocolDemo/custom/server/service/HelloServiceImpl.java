package netty.demo.protocolDemo.custom.server.service;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/8 14:46:46
 * @Version : 1.0
 */

public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello() {
        return "成功连接啦！！！！";
    }
}
