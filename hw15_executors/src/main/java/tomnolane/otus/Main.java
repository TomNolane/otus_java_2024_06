package tomnolane.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private boolean isNeedToReverse = true;
    private final static int lastFirst = 0;
    private final static int lastSecond = 0;
    private String currentTurn = "Thread 1";

    public static void main(String[] args) {
        Main pingPong = new Main();

        new Thread(() -> pingPong.action(lastFirst), "Thread 1").start();
        new Thread(() -> pingPong.action(lastSecond), "Thread 2").start();
    }

    private synchronized void action(int threadIndex) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (!Thread.currentThread().getName().equals(currentTurn)) {
                    this.wait();
                }

                if (threadIndex == 0) {
                    isNeedToReverse = false;
                } else if (threadIndex == 10) {
                    isNeedToReverse = true;
                }

                if(isNeedToReverse) {
                    threadIndex -= 1;
                } else {
                    threadIndex += 1;
                }

                logger.info("{} - {}", Thread.currentThread().getName(), threadIndex);

                currentTurn = Thread.currentThread().getName().equals("Thread 1") ? "Thread 2" : "Thread 1";

                this.notifyAll();
                Thread.sleep(500);

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
