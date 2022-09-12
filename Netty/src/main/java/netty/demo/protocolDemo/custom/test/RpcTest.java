package netty.demo.protocolDemo.custom.test;

import netty.demo.protocolDemo.custom.server.service.HelloService;
import netty.demo.protocolDemo.custom.server.service.HelloServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/8 15:09:09
 * @Version : 1.0
 */

public class RpcTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> name = Class.forName("netty.demo.protocolDemo.custom.server.service.HelloServiceImpl");
        Object instance = name.getDeclaredConstructor().newInstance();
        Method[] methods = name.getMethods();

        Method method = name.getMethod("sayHello");
        System.out.println(method.getReturnType().getName());
        System.out.println(method.invoke(instance));
        System.out.println(HelloServiceImpl.class);
    }
}
