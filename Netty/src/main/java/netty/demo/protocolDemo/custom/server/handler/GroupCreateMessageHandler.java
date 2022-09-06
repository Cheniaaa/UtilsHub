package netty.demo.protocolDemo.custom.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.demo.protocolDemo.custom.message.GroupCreateRequestMessage;
import netty.demo.protocolDemo.custom.message.GroupCreateResponseMessage;
import netty.demo.protocolDemo.custom.server.session.Group;
import netty.demo.protocolDemo.custom.server.session.GroupSession;
import netty.demo.protocolDemo.custom.server.session.GroupSessionFactory;

import java.util.List;
import java.util.Set;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/9/5 21:24:24
 * @Version : 1.0
 */
@ChannelHandler.Sharable
public class GroupCreateMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(groupName, members);
        if (group == null) {
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, groupName + "创建成功"));
            List<Channel> membersChannel = groupSession.getMembersChannel(groupName);
            for (Channel channel : membersChannel) {
                channel.writeAndFlush(new GroupCreateResponseMessage(true, "已加入" + groupName));
            }
        } else {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, groupName + "已经存在"));
        }
    }
}
