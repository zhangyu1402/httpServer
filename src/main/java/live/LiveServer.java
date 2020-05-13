package live;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class LiveServer {
    private final int port;
    public LiveServer(int port) {
        this.port = port;
    }
    public void start() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup bossgroup = new NioEventLoopGroup();
        NioEventLoopGroup workgroup = new NioEventLoopGroup();
        b.group(bossgroup,workgroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
                        System.out.println("initChannel ch:" + ch);
                        ch.pipeline()
                                .addLast("decoder", new LiveDecoder())   // 1
                                .addLast("encoder", new LiveEncoder())  // 2
//                                .addLast("aggregator", new HttpObjectAggregator( 1)) ;   // 3
                                .addLast("handler", new LiveHandler());        // 4
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        b.bind(port).sync();
    }

    public static void main(String[] args) throws Exception {
        new LiveServer(8080).start();
    }
}
