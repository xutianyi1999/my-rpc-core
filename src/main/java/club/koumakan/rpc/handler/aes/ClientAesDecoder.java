package club.koumakan.rpc.handler.aes;

import club.koumakan.rpc.handler.AesDecoder;
import io.netty.channel.ChannelHandler;

@ChannelHandler.Sharable
public class ClientAesDecoder extends AesDecoder {

    public static final ClientAesDecoder INSTANCE = new ClientAesDecoder();

    private ClientAesDecoder() {
        super(false);
    }
}
