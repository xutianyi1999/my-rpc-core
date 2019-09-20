package club.koumakan.rpc.template;

import club.koumakan.rpc.Future;
import club.koumakan.rpc.channel.Receiver;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

public class RpcServerTemplate {

    private ServerBootstrap serverBootstrap;

    public RpcServerTemplate(ServerBootstrap serverBootstrap) {
        this.serverBootstrap = serverBootstrap;
    }

    public void bind(int port, Future<Receiver> future) {
        serverBootstrap.bind(port).addListener((GenericFutureListener<ChannelFuture>) channelFuture ->
                future.execute(channelFuture.cause(), new Receiver(channelFuture.channel())));
    }
}
