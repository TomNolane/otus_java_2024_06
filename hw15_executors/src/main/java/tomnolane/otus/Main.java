package tomnolane.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static List<Integer> array = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    private boolean isNeedToReverse = true;
    private static Integer lastFirst = -1;
    private static Integer lastSecond = -1;

    public static void main(String[] args) {
        Main pingPong = new Main();

        new Thread(() -> pingPong.action(lastFirst), "Thread 1").start();
        new Thread(() -> pingPong.action(lastSecond), "Thread 2").start();
    }

    private synchronized void action(Integer threadIndex) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if(threadIndex == -1) { //for init counting
                    threadIndex = 0;
                } else if (threadIndex == array.size()) {
                    threadIndex = 1;

                    if(isNeedToReverse) {
                        Collections.reverse(array);
                        isNeedToReverse = false;
                    } else {
                        isNeedToReverse = true;
                    }
                }

                this.notifyAll();
                logger.info("{} - {}", Thread.currentThread().getName(), array.get(threadIndex));
                this.wait();

                threadIndex += 1;

                Thread.sleep(1_000);

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
