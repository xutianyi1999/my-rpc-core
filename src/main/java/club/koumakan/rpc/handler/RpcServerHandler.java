package club.koumakan.rpc.handler;

import club.koumakan.rpc.message.entity.Call;
import club.koumakan.rpc.server.Channel;
import club.koumakan.rpc.server.Listener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;

import static club.koumakan.rpc.RpcContext.listenerMap;

@ChannelHandler.Sharable
public class RpcServerHandler extends SimpleChannelInboundHandler<Call> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Call msg) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().localAddress();
        Listener listener = listenerMap.get(inetSocketAddress.getPort());

        if (listener != null) {
            listener.read(msg.getData(), new Channel(ctx, msg));
        }
    }
}
