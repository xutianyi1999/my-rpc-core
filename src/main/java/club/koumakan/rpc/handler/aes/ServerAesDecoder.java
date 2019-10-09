package club.koumakan.rpc.handler.aes;

import club.koumakan.rpc.handler.AesDecoder;
import io.netty.channel.ChannelHandler;

@ChannelHandler.Sharable
public class ServerAesDecoder extends AesDecoder {

    public static final ServerAesDecoder INSTANCE = new ServerAesDecoder();

    private ServerAesDecoder() {
        super(true);
    }
}
