package Pattern.Singleton;


import java.util.ArrayList;
import java.util.List;

public class LazyDoubleCheckSingleton {
    private volatile static LazyDoubleCheckSingleton instance;

    private LazyDoubleCheckSingleton() {
    }

    public static LazyDoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            threads.add(new Thread(() -> {
                LazyDoubleCheckSingleton instance = getInstance();
                System.out.println("instance name " + instance);
            }));
        }
        for (int i = 0; i < n; i++) {
            threads.get(i).start();
        }
        for (int i = 0; i < n; i++) {
            threads.get(i).join();
        }
    }
}
