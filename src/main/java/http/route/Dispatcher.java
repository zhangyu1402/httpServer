package http.route;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;

public interface Dispatcher {
    public DefaultFullHttpResponse route(FullHttpRequest request, DefaultFullHttpResponse response);
}
