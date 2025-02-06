package tomnolane.otus.loganalytics.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UdpLogServer {
    private static final int PORT = 9999;

    private final UdpLogHandler udpLogHandler;

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(udpLogHandler);

            Channel channel = bootstrap.bind(PORT).sync().channel();
            log.info("UDP Server started on port {}", PORT);
            channel.closeFuture().await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        group.shutdownGracefully();
    }
}
