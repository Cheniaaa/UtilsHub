package netty.demo.protocolDemo.custom.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.demo.protocolDemo.custom.message.GroupMembersRequestMessage;
import netty.demo.protocolDemo.custom.message.GroupMembersResponseMessage;
import netty.demo.protocolDemo.custom.server.session.GroupSessionFactory;

import java.util.Set;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/5 21:59:59
 * @Version : 1.0
 */
@ChannelHandler.Sharable
public class GroupMemberMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) throws Exception {
        Set<String> members = GroupSessionFactory.getGroupSession()
                .getMembers(msg.getGroupName());
        ctx.writeAndFlush(new GroupMembersResponseMessage(members));
    }

}
