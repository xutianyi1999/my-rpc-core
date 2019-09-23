package club.koumakan.rpc.template;

import club.koumakan.rpc.Future;
import club.koumakan.rpc.channel.Receiver;
import club.koumakan.rpc.commons.Context;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;

public class RpcServerTemplate {

    private ServerBootstrap serverBootstrap;

    public RpcServerTemplate(ServerBootstrap serverBootstrap) {
        this.serverBootstrap = serverBootstrap;
    }

    public void bind(int port, Future<Receiver> future) {
        serverBootstrap.bind(port).addListener((GenericFutureListener<ChannelFuture>) channelFuture ->
                future.execute(channelFuture.cause(), new Receiver(channelFuture.channel())));
    }

    public void bind(int port, String key, Future<Receiver> future) {
        serverBootstrap.bind(port).addListener((GenericFutureListener<ChannelFuture>) channelFuture -> {
            if (channelFuture.cause() != null) {
                future.execute(channelFuture.cause(), null);
            } else {
                Channel channel = channelFuture.channel();
                Context.addCipher(key, (InetSocketAddress) channel.localAddress());
                future.execute(null, new Receiver(channel));
            }
        });
    }
}
