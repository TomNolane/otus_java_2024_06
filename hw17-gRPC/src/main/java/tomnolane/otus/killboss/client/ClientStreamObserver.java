package tomnolane.otus.killboss.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.killboss.NumberResponse;

public class ClientStreamObserver implements StreamObserver<NumberResponse> {
    private static final Logger logger = LoggerFactory.getLogger(ClientStreamObserver.class);

    private int currentValue;
    private boolean completed = false;

    @Override
    public void onNext(NumberResponse numberResponse) {
        synchronized (this) {
            currentValue = numberResponse.getValue();
            logger.info("newValue:{}", currentValue);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("error", throwable);
    }

    @Override
    public void onCompleted() {
        completed = true;
        logger.info("request completed");
    }

    public synchronized int getCurrentValue() {
        return currentValue;
    }

    public boolean isCompleted() {
        return completed;
    }
}