package tomnolane.otus.killboss.server;

import io.grpc.stub.StreamObserver;
import tomnolane.otus.killboss.KillBossServiceGrpc;
import tomnolane.otus.killboss.NumberResponse;
import tomnolane.otus.killboss.RangeRequest;

import java.util.concurrent.TimeUnit;

public class NumberService extends KillBossServiceGrpc.KillBossServiceImplBase {
    private final long delay;

    public NumberService(long delay) {
        this.delay = delay;
    }

    @Override
    public void getNumbers(RangeRequest request, StreamObserver<NumberResponse> responseObserver) {
        int start = request.getStart();
        int end = request.getEnd();

        for (int i = start; i <= end; i++) {
            responseObserver.onNext(NumberResponse.newBuilder().setValue(i).build());

            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        responseObserver.onCompleted();
    }
}