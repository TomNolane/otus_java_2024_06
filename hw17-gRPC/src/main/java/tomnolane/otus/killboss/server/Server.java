package tomnolane.otus.killboss.server;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private static final int PORT = 42;
    private static final long TIMEOUT = 2000;
    private final io.grpc.Server server;

    public Server() {
        server = ServerBuilder.forPort(PORT)
                .addService(new NumberService(TIMEOUT))
                .build();
    }

    private void start() throws IOException, InterruptedException {
        server.start();
        logger.info("Server started");
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Server().start();
    }
}