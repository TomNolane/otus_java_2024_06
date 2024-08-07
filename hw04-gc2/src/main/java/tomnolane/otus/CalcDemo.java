package tomnolane.otus;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalcDemo {
    private static final Logger log = LoggerFactory.getLogger(CalcDemo.class);

    public static void main(String[] args) {
        final long counter = 500_000_000;
        final var summator = new Summator();
        final long startTime = System.currentTimeMillis();

        for (var idx = 0; idx < counter; idx++) {
            final var data = new Data(idx);
            summator.calc(data);

            if (idx % 10_000_000 == 0) {
                log.info("{} current idx:{}", LocalDateTime.now(), idx);
            }
        }

        final long delta = System.currentTimeMillis() - startTime;
        log.info("PrevValue:{}", summator.getPrevValue());
        log.info("PrevPrevValue:{}", summator.getPrevPrevValue());
        log.info("SumLastThreeValues:{}", summator.getSumLastThreeValues());
        log.info("SomeValue:{}", summator.getSomeValue());
        log.info("Sum:{}", summator.getSum());
        log.info("spend msec:{}, sec:{}", delta, (delta / 1000));
    }
}
/*
-Xms2g
-Xmx2g
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=C:\Users\tomnolane\IdeaProjects\otus_java_2024_06\hw04-gc\build\logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=C:\Users\tomnolane\IdeaProjects\otus_java_2024_06\hw04-gc\build\logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
 */