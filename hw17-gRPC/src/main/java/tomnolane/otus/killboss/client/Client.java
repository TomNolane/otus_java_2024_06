package tomnolane.otus.killboss.client;

import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.killboss.KillBossServiceGrpc;
import tomnolane.otus.killboss.RangeRequest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final String HOST = "localhost";
    private static final int PORT = 42;
    private static final int RANGE_START = 0;
    private static final int RANGE_END = 30;
    private static final long TIMEOUT = 1000;
    private static final int TIMES = 50;

    private final KillBossServiceGrpc.KillBossServiceStub asyncStub;
    private final ClientStreamObserver responseStreamObserver;

    public Client() {
        final var channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
        asyncStub = KillBossServiceGrpc.newStub(channel);
        responseStreamObserver = new ClientStreamObserver();
    }

    public static void main(String[] args) {
        var request = RangeRequest.newBuilder().setStart(RANGE_START).setEnd(RANGE_END).build();
        new Client().start(request);
    }

    private void start(RangeRequest rangeRequest) {
        asyncStub.getNumbers(rangeRequest, responseStreamObserver);
        logger.info("Your client is starting");
        increment();
    }

    private void increment() {
        AtomicInteger clientValue = new AtomicInteger(0), serverValue = new AtomicInteger(0);

        for (int i = 0; i < TIMES; i++) {
            if (responseStreamObserver.isCompleted()) {
                break;
            }

            int tempValue = responseStreamObserver.getCurrentValue();

            if(serverValue.get() != tempValue) {
                clientValue.addAndGet(serverValue.getAndSet(tempValue));
            }

            logger.info("currentValue:{}", clientValue);

            try {
                TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            clientValue.incrementAndGet();
        }
    }
}