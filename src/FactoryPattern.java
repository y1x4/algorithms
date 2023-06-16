/**
 * https://www.cnblogs.com/yssjun/p/11102162.html
 */
public class FactoryPattern {

    // 1.简单工厂模式
    // 适用场景：（1）需要创建的对象较少；（2）客户端不关心对象的创建过程。
    // 统一的工厂类 ShapeFactory 根据 type 创建 Shape 实现类（CircleShape、RectShape）

    // 2.工厂方法模式
    // 适用场景：（1）客户端不需要知道它所创建的对象的类；（2）客户端可以通过子类来指定创建对应的对象。
    // 每个对象都有一个对应的工厂
    // ShapeFactory 实现类（CircleFactory、RectFactory）、Shape 实现类（CircleShape、RectShape）
    // ShapeFactory factory = new CircleFactory();
    // factory.getShape().draw();

    // 3.抽象工厂模式
    // 适用场景：（1）客户端不需要知道它所创建的对象的类；（2）系统结构稳定，不会频繁的增加对象。
    // AbstractFactory（createPhone()、createPC()）、ConcreteFactory（MiFactory、AppleFactory）、AbstractProduct（Phone、PC）、ConcreteProduct（MiPhone、MiPC、iPhone、MAC）


    public static void main(String[] args) {
        System.out.println((new Short((byte)127) == new Short((byte)127)));
    }
}



