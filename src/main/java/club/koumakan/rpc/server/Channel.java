package club.koumakan.rpc.server;

import club.koumakan.rpc.ChannelFutureContainer;
import club.koumakan.rpc.Future;
import club.koumakan.rpc.message.entity.Call;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

public class Channel {

    private ChannelHandlerContext ctx;

    private Call call;

    public Channel(ChannelHandlerContext ctx, Call call) {
        this.ctx = ctx;
        this.call = call;
    }

    public void response(Object responseMessage, Future future) {
        call.setData(responseMessage);
        ChannelFuture channelFuture = ctx.writeAndFlush(call);

        if (future != null) {
            channelFuture.addListener(new ChannelFutureContainer(future));
        }
    }

    public void response(Object responseMessage) {
        response(responseMessage, null);
    }

    public void close(Future future) {
        ChannelFuture channelFuture = ctx.close();

        if (future != null) {
            channelFuture.addListener(new ChannelFutureContainer(future));
        }
    }

    public void close() {
        close(null);
    }
}
