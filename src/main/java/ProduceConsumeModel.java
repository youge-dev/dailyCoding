import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模型
 *
 * @author yqzhou
 */
public class ProduceConsumeModel {
    ReentrantLock lock = new ReentrantLock();
    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();
    Object[] queue = new Object[20];
    int putpos, takepos, count;

    @SneakyThrows
    public void put(Object x) {
        lock.lock();
        try {
            while (count == queue.length) {//如果队列满了
                notFull.await();//阻塞写线程
            }
            queue[putpos++] = x;
            if (putpos == queue.length) {//如果写索引写到队列的最后一个位置了，那么置为0
                putpos = 0;
            }
            count++;
            notEmpty.signal();//唤醒读线程
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public Object take() {
        lock.lock();
        try {
            while (count == 0) {//如果队列为空
                notEmpty.await();//阻塞读线程
            }
            Object x = queue[takepos++];
            if (putpos == queue.length) {//如果读索引读到队列的最后一个位置了，那么置为0
                putpos = 0;
            }
            count--;
            notFull.signal();//唤醒写线程
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ProduceConsumeModel produceConsumeModel = new ProduceConsumeModel();
        List<Thread> threadList = new ArrayList<>();
        int n = 20;
        Thread producer = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                produceConsumeModel.put(i);
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                System.out.println("consumer: " + produceConsumeModel.take());
            }
        });
        threadList.add(producer);
        threadList.add(consumer);
        threadList.forEach(Thread::start);
        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
