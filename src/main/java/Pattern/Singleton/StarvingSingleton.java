package Pattern.Singleton;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class StarvingSingleton {
    private StarvingSingleton() {
    }

    private static final StarvingSingleton instance = new StarvingSingleton();

    public static StarvingSingleton getInstance() {
        return instance;
    }

    @SneakyThrows
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for(int i=0;i<10;i++){
            threads.add(new Thread(()->{
                StarvingSingleton instance = getInstance();
                System.out.println("instance name " + instance);
            }));
        }
        for (int i = 0; i < 10; i++) {
            threads.get(i).start();
        }
        for (int i = 0; i < 10; i++) {
            threads.get(i).join();
        }
    }
}
