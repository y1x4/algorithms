import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 现在我们有一个类Calculator，可以进行加减乘除操作，需求：在每个方法执行前后打印日志。
 * 1.直接在每个方法里加。
 * 缺点：不符合开闭原则（对扩展开放，对修改关闭）、修改量大、重复代码、不利于后期维护。
 * 2.静态代理：将Calculator抽取为接口，创建目标类CalculatorImpl和代理类CalculatorProxy
 * 在不修改目标对象的前提下，对目标对象进行功能的扩展和拦截，仅解决缺点1。
 * 真正想要的还是根据接口一步到位生成代理对象。
 * 3.动态代理：
 * 的
 */

interface Calculator {
    int add(int a, int b);

    int subtract(int a, int b);
}

class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int subtract(int a, int b) {
        return a - b;
    }
}


/**
 * 静态代理：在编译时就已经实现，编译完成后代理类是一个实际的class文件
 */
class CalculatorProxy implements Calculator {
    private final Calculator target;

    public CalculatorProxy(Calculator target) {
        this.target = target;
    }

    @Override
    public int add(int a, int b) {
        System.out.println("add方法开始...");
        int result = target.add(a, b);
        System.out.println("add方法结束...");
        return result;
    }

    @Override
    public int subtract(int a, int b) {
        System.out.println("subtract方法开始...");
        int result = target.subtract(a, b);
        System.out.println("subtract方法结束...");
        return result;
    }
}

class Proxy1 {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        // 根据类加载器和一串不能生成对象实例的接口构建出包含这些接口功能的能生成对象实例的类
        Class<?> calculatorClazz = Proxy.getProxyClass(Calculator.class.getClassLoader(), Calculator.class);
        // 获取有参构造器 $Proxy(InvocationHandler h)
        Constructor<?> constructor = calculatorClazz.getConstructor(InvocationHandler.class);
        Calculator calculatorProxyImpl = (Calculator) constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                CalculatorImpl calculatorImpl = new CalculatorImpl();
                return method.invoke(calculatorImpl, args);
            }
        });
        calculatorProxyImpl.add(1, 2);
    }
}

class Proxy2 {
    public static void main(String[] args) throws Throwable {
        CalculatorImpl target = new CalculatorImpl();
        Calculator calculatorProxy = (Calculator) getProxy(target);
        calculatorProxy.add(1, 2);
    }

    // 根据类实现的接口生成代理对象
    private static Object getProxy(final Object target) throws Exception {
        Class<?> proxyClazz = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());
        Constructor<?> constructor = proxyClazz.getConstructor(InvocationHandler.class);
        Object proxy = constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName() + "方法开始执行...");
                Object result = method.invoke(target, args);
                System.out.println(result);
                System.out.println(method.getName() + "方法执行结束...");
                return result;
            }
        });
        return proxy;
    }
}

class Proxy3 {
    public static void main(String[] args) throws Throwable {
        CalculatorImpl target = new CalculatorImpl();
        Calculator calculatorProxy = (Calculator) getProxy(target);
        calculatorProxy.add(1, 2);
        Object proxy = getProxy(target);
        System.out.println(proxy.getClass().getName());   // com.sun.proxy.$Proxy0
        System.out.println(proxy instanceof Calculator);  // true
    }

    // 根据类实现的接口直接生成代理对象
    private static Object getProxy(final Object target) throws Exception {
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {/*代理对象的方法最终都会被JVM导向它的invoke方法*/
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println(method.getName() + "方法开始执行...");
                        Object result = method.invoke(target, args);
                        System.out.println(result);
                        System.out.println(method.getName() + "方法执行结束...");
                        return result;
                    }
                }
        );
        return proxy;
    }
}


/**
 * 动态代理：在运行时动态生成，即编译完成后没有实际的class文件，而是在运行时动态生成类字节码，并加载到JVM中
 * 1.JDK Proxy：目标对象必须实现接口
 */
class ProxyHandler implements InvocationHandler {
    private Object target;

    // 绑定委托对象，并返回代理类
    public Object bind(Object t) {
        this.target = t;
        // 绑定该类实现的所有接口，取得代理类
        return Proxy.newProxyInstance(t.getClass().getClassLoader(),
                t.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //这里就可以进行所谓的AOP编程了
        //在调用具体函数方法前，执行功能处理
        System.out.println(method.getName() + "方法开始执行...");
        Object result = method.invoke(target, args);
        //在调用具体函数方法后，执行功能处理
        System.out.println(result);
        System.out.println(method.getName() + "方法执行结束...");
        return result;
    }

    public static void main(String[] args) {
        ProxyHandler proxy = new ProxyHandler();
        Calculator calculator = (Calculator) proxy.bind(new CalculatorImpl());
        calculator.add(1, 2);
    }
}

/**
 * 动态代理
 * 2.CGLib：继承目标对象，底层基于ASM字节码框架实现
 */
//class ProxyFactory implements MethodInterceptor {
//
//    private Object target;//维护一个目标对象
//
//    public ProxyFactory(Object target) {
//        this.target = target;
//    }
//
//    //为目标对象生成代理对象
//    public Object getProxyInstance() {
//        //工具类
//        Enhancer en = new Enhancer();
//        //设置父类
//        en.setSuperclass(target.getClass());
//        //设置回调函数
//        en.setCallback(this);
//        //创建子类对象代理
//        return en.create();
//    }
//
//    @Override
//    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//        System.out.println("开启事务");
//        // 执行目标对象的方法
//        Object returnValue = method.invoke(target, args);
//        System.out.println("关闭事务");
//        return null;
//    }
//
//    public static void main(String[] args) {
//        UserDao target = new UserDao();
//        System.out.println(target.getClass());
//        //代理对象
//        UserDao proxy = (UserDao) new ProxyFactory(target).getProxyInstance();
//        System.out.println(proxy.getClass());
//        //执行代理对象方法
//        proxy.save();
//    }
//}

/**
 * 静态代理实现较简单，只要代理对象对目标对象进行包装，即可实现增强功能，但静态代理只能为一个目标对象服务，如果目标对象过多，则会产生很多代理类。
 * JDK动态代理需要目标对象实现业务接口，代理类只需实现InvocationHandler接口。
 * 静态代理在编译时产生class字节码文件，可以直接使用，效率高。
 * 动态代理必须实现InvocationHandler接口，通过反射代理方法，比较消耗系统性能，但可以减少代理类的数量，使用更灵活。
 * cglib代理无需实现接口，通过生成类字节码实现代理，比反射稍快，不存在性能问题，但cglib会继承目标对象，需要重写方法，所以目标对象不能为final类。
 */