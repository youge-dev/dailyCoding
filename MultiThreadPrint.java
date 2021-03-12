package daily.learn;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MultiThreadPrint {
    private static List<Thread> list;
    static volatile ShareObject[] obects;

    @SneakyThrows
    public static void print(int n, int threadNum) {
        AtomicInteger counter = new AtomicInteger(0);

        obects = new ShareObject[n];
        for (int i = 0; i < n; i++) {
            obects[i] = new ShareObject(0, new Object());
        }
        obects[0].setFlag(1);
        int curI = 0, nextI = 0;
        list = new ArrayList<>(threadNum);
        for (int i = 0; i < threadNum; i++) {
            curI = i % n;
            nextI = curI + 1;
            if (curI == n - 1) {
                nextI = 0;
            }
            list.add(new Thread(new PrintThread(obects[curI], obects[nextI], curI, threadNum, counter)));
        }
        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }
    }

    public static void main(String[] args) {
        log.info("daily learning start ...");
        int n = 3;
        int maxLoopCount = 12;
        print(n, maxLoopCount);
    }


    @Data
    private static class ShareObject {
        // 1表示可以竞争，0 关闭
        private volatile int flag;
        private Object object;

        public ShareObject(int i, Object o) {
            this.flag = i;
            this.object = o;
        }
    }

    @Data
    private static class PrintThread implements Runnable {
        private final ShareObject cur;
        private final ShareObject next;
        private int val;
        private int maxLoop;
        private AtomicInteger counter;

        public PrintThread(ShareObject cur, ShareObject next, int val, int maxLoop, AtomicInteger counter) {
            this.cur = cur;
            this.next = next;
            this.val = val;
            this.maxLoop = maxLoop;
            this.counter = counter;
        }

        @Override
        @SneakyThrows
        public void run() {
            while (true) {
                synchronized (this.cur.getObject()) {
                    while (cur.getFlag() == 0) {
                        cur.getObject().wait();
                    }
                    log.info("number is :{}", val);
                    cur.setFlag(0);
                    synchronized (this.next.getObject()) {
                        next.setFlag(1);
                        next.getObject().notifyAll();

                        int running = counter.getAndIncrement();
                        if (running > maxLoop) {
                            break;
                        }
                    }
                }

            }
        }
    }
}
