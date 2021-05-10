package daily.learn;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        new Thread(() -> {
            log.info("thread {} start ...", Thread.currentThread().getName());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thread {} over ...", Thread.currentThread().getName());
            latch.countDown();
        }).start();

        new Thread(() -> {
            log.info("thread {} start ...", Thread.currentThread().getName());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thread {} over ...", Thread.currentThread().getName());
            latch.countDown();
        }).start();
        log.info("等待2个子线程执行完毕...");
        latch.await();
        log.info("main thread continue");
    }
}
