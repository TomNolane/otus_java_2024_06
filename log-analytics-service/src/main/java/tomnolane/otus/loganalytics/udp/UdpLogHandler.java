package tomnolane.otus.loganalytics.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tomnolane.otus.loganalytics.model.IncomingLogEntry;
import tomnolane.otus.loganalytics.model.LogEntry;
import tomnolane.otus.loganalytics.repository.LogRepository;
import tomnolane.otus.loganalytics.utils.LogEntryMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class UdpLogHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private final LogRepository logRepository;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        String message = packet.content().toString(CharsetUtil.UTF_8);
        log.info("Received UDP log: {}", message);

        String[] parts = message.split(",");
        if (parts.length == 3) {
            IncomingLogEntry incomingLog = new IncomingLogEntry();
            incomingLog.setSource(parts[0]);
            incomingLog.setLevel(parts[1]);
            incomingLog.setMessage(parts[2]);
            incomingLog.setTimestamp(System.currentTimeMillis());

            LogEntry logEntry = LogEntryMapper.mapToLogEntry(incomingLog);
            logRepository.save(logEntry).subscribe(saved -> log.info("Log saved: {}", saved));
        } else {
            log.warn("Не удалось распарсить сообщение: {}", message);
        }
    }
}
