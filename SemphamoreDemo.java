package daily.learn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SemphamoreDemo {

    public static void main(String[] args) {
        int N = 8;
        int machineNum = 5;
        Semaphore semaphore = new Semaphore(machineNum);
        for (int i = 0; i < N; i++) {
            new Thread(new Worker(i, semaphore)).start();
        }
    }


    @Data
    @AllArgsConstructor
    static class Worker implements Runnable {
        private int number;
        private Semaphore semaphore;

        @Override
        @SneakyThrows
        public void run() {
            semaphore.acquire();
            log.info("worker:{} is start working...", number);
            TimeUnit.MILLISECONDS.sleep(100);
            log.info("worker:{} is finish working...", number);
            semaphore.release();
        }
    }
}
