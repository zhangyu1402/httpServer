package live;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.Arrays;
import java.util.List;

public class LiveDecoder extends ReplayingDecoder<LiveDecoder.LiveState> {


    public enum LiveState {
        TYPE,
        LENGTH,
        CONTENT
    }

    private LiveMessage message;

    public LiveDecoder() {
        super(LiveState.TYPE);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("call decode");
        LiveState state = state();
//        System.out.println(state);
        byte type = byteBuf.readByte();
        int length = byteBuf.readInt();
        message = new LiveMessage();
        message.setLength(length);
        byte[] bytes = new byte[message.getLength()];
        byteBuf.readBytes(bytes);
        String content = new String(bytes);
        message.setContent(content);
        message.setType((byte) 2);
        list.add(message);
        System.out.println(message);
//        switch (state) {
//            case TYPE:
//                message = new LiveMessage();
//                byte type = byteBuf.readByte();
//                message.setType(type);
//                checkpoint(LiveState.LENGTH);
//                break;
//            case LENGTH:
//                int length = byteBuf.readInt();
//                message.setLength(length);
//                if (length > 0) {
//                    checkpoint(LiveState.CONTENT);
//                } else {
//                    list.add(message);
//                    checkpoint(LiveState.TYPE);
//                }
//                break;
//            case CONTENT:
//                byte[] bytes = new byte[message.getLength()];
//                byteBuf.readBytes(bytes);
//                String content = new String(bytes);
//                message.setContent(content);
//                list.add(message);
//                checkpoint(LiveState.TYPE);
//                break;
//            default:
//                throw new IllegalStateException("invalid state:" + state);
//        }
    }
}