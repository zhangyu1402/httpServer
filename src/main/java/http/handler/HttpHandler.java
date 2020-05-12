package http.handler;

import http.route.Dispatcher;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private  AsciiString contentType = HttpHeaderValues.APPLICATION_JSON;
    private Dispatcher router;
    public HttpHandler(){

    }

    public HttpHandler(Dispatcher router) {
        this.router = router;
    }

    private void buildHeader(DefaultFullHttpResponse response) {
        HttpHeaders heads = response.headers();
        if( !heads.contains(HttpHeaderNames.CONTENT_TYPE))heads.add(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=UTF-8");
        if(!heads.contains(HttpHeaderNames.CONTENT_LENGTH))heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); // 3
        if(!heads.contains(HttpHeaderNames.CONNECTION))heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
    }

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK);
        //default header
        response = router.Route(msg,response);
        buildHeader(response);
        ctx.write(response);
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}
