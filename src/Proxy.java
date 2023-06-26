import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 现在我们有一个类Calculator，可以进行加减乘除操作，需求：在每个方法执行前后打印日志。
 * 1.直接在每个方法里加。
 *     缺点：不符合开闭原则（对扩展开放，对修改关闭）、修改量大、重复代码、不利于后期维护。
 * 2.静态代理：将Calculator抽取为接口，创建目标类CalculatorImpl和代理类CalculatorProxy
 *     在不修改目标对象的前提下，对目标对象进行功能的扩展和拦截，仅解决缺点1。
 *     真正想要的还是根据接口一步到位生成代理对象。
 * 3.动态代理：
 *     的
 *
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

class ProxyHandler implements InvocationHandler {
    private Object target;

    // 绑定委托对象，并返回代理类
    public Object bind(Object t)
    {
        this.target = t;
        // 绑定该类实现的所有接口，取得代理类
        return Proxy.newProxyInstance(t.getClass().getClassLoader(),
                t.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy , Method method , Object[] args)throws Throwable
    {
        Object result = null;
        //这里就可以进行所谓的AOP编程了
        //在调用具体函数方法前，执行功能处理
        System.out.println(method.getName() + "方法开始执行...");
        result = method.invoke(target, args);
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