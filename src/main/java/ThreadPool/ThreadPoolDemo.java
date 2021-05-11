package ThreadPool;

import lombok.SneakyThrows;

import java.sql.Time;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池 (如果workerCount < 线程池corePoolSize，则创建并启动一个线程来执行新提交的任务
 * 所以线程池初始化配置corePoolSize为3，依次提交3个任务执行后，线程池里有3个线程)
 */
public class ThreadPoolDemo {
    @SneakyThrows
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("name = " + Thread.currentThread().getName());
            }
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3));
        System.out.println("无任务提交时");
        System.out.println("核心线程数 = " + threadPoolExecutor.getCorePoolSize());
        System.out.println("当前线程池线程数 = " + threadPoolExecutor.getPoolSize());
        System.out.println("maxPoolNum = " + threadPoolExecutor.getMaximumPoolSize());
        System.out.println("当前队列中的任务数 queue size = " + threadPoolExecutor.getQueue().size());
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        System.out.println("依次提交处理2个任务后");
        System.out.println("当前线程池线程数 = " + threadPoolExecutor.getPoolSize());
        threadPoolExecutor.execute(runnable);
        System.out.println("依次提交处理3个任务后");
        System.out.println("当前线程池线程数 = " + threadPoolExecutor.getPoolSize());
        threadPoolExecutor.execute(runnable);
        System.out.println("依次提交处理4个任务后");
        System.out.println("当前线程池线程数 = " + threadPoolExecutor.getPoolSize());
        for (int i = 0; i < 10; i++) {
            Future<?> future = threadPoolExecutor.submit(runnable);
            future.get(10,TimeUnit.SECONDS);
        }
        System.out.println("依次提交处理14个任务后");
        System.out.println("当前线程池线程数 = " + threadPoolExecutor.getPoolSize());

    }


}
