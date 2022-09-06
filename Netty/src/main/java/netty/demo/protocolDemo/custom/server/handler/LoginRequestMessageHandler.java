package netty.demo.protocolDemo.custom.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.demo.protocolDemo.custom.message.LoginRequestMessage;
import netty.demo.protocolDemo.custom.message.LoginResponseMessage;
import netty.demo.protocolDemo.custom.server.service.UserServiceFactory;
import netty.demo.protocolDemo.custom.server.session.SessionFactory;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/5 00:31:31
 * @Version : 1.0
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean login= UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage loginResponseMessage;
        if (login) {
            SessionFactory.getSession().bind(ctx.channel(), username);
            loginResponseMessage = new LoginResponseMessage(true, "登录成功");
        } else {
            loginResponseMessage = new LoginResponseMessage(false, "账号密码错误");
        }
        ctx.writeAndFlush(loginResponseMessage);
    }
}
