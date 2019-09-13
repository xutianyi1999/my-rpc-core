package club.koumakan.rpc.client;

import club.koumakan.rpc.message.entity.ResponseMessage;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

public interface Callback extends GenericFutureListener<ChannelFuture> {

    void response(ResponseMessage responseMessage);
}
