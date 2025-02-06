package tomnolane.otus.loganalytics.udp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UdpServerStarter implements CommandLineRunner {
    private final UdpLogServer udpLogServer;

    public UdpServerStarter(UdpLogServer udpLogServer) {
        this.udpLogServer = udpLogServer;
    }

    @Override
    public void run(String... args) {
        new Thread(udpLogServer::start).start();
    }
}
