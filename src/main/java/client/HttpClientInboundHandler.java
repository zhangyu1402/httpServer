package client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.Socket;
import java.net.URI;

public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.getClass());
        if( msg instanceof  HttpResponse) {
            ((HttpResponse) msg).headers().entries().forEach(e-> System.out.println(e.getKey()+":"+e.getValue()));
        }
        if(msg instanceof HttpContent) {
            System.out.println(System.currentTimeMillis());
            System.out.println(((HttpContent) msg).content().toString(io.netty.util.CharsetUtil.UTF_8));
        }

    }
}
