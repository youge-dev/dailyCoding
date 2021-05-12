package Pattern.Singleton;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 枚举单例，（避免反射和序列化问题 因为反射机制调用私有构造器）
 * @author yqzhou
 * @Data 20210512 13:28
 */
public class EnumSingleton {
    private EnumSingleton() {
    }

    public static EnumSingleton getInstance() {
        return Singleton.Holder.instance;
    }

    private enum Singleton {
        Holder;
        private EnumSingleton instance;

        Singleton() {
            instance = new EnumSingleton();
        }
    }
    @SneakyThrows
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for(int i=0;i<10;i++){
            threads.add(new Thread(()->{
                EnumSingleton instance = getInstance();
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
