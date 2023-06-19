/**
 * 懒汉，饿汉，双重校验锁，枚举和静态内部类。
 *
 * @author yixu
 */
public class Singleton {

    private static final Singleton s = new Singleton();

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        return s;
    }

    public static void main(String[] args) {
        Singleton s = Singleton.getInstance();
    }
}

class Singleton3 {
    private static Singleton3 instance;

    private Singleton3() {
    }

    public static synchronized Singleton3 getInstance() {
        if (instance == null) {
            instance = new Singleton3();
        }
        return instance;
    }
}

enum Singleton7 {
    /**
     * 注释
     */
    instance;
}


/**
 * spring使用 singletonCache HashMap存储单例
 * 获取bean实例时，synchronized从map中取，为空、且为单例bean则进入，再次synchronized检查，创建
 * */