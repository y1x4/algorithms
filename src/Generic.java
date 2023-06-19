import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author yixu
 * <p>
 * 编译时类型擦除
 * 类型擦除后保留的原始类型，如Object、T extends XX 的 XX
 * 在调用泛型方法时，可指定泛型（编译时会做泛型检查）、也可不指定（最小公约父类）
 * 类型擦除引起的问题和解决方法：
 * 先检查再编译、类型检查在引用和引用传递阶段
 * 自动类型转换
 * 类型擦除与多态的冲突和解决方法（桥方法）
 * 泛型类型变量不能是基本数据类型（int等）
 * 类型擦除后只能 x instanceof List
 * 泛型类中的静态方法和静态变量不可以使用泛型类声明的泛型类型参数，但泛型方法可以使用自己定义的T
 */
public class Generic {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.getClass().getMethod("add", Object.class).invoke(list, "asd");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(7);



        // 迭代器遍历修改，fail-fast，直接抛ConcurrentModificationException异常；JUC下的容器是fail-safe，复制原集合进行迭代
//        for (int i = 0; i < list.size(); i++) {
////            if (list.get(i) == 5) {
//                System.out.println(i);
//                list.remove(i);
////            }
//        }

        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            Integer value = it.next();
            System.out.println(value);
//            if (value == 5) {
                list.remove(value);
//            }
        }

//        for (Integer i : list) {
////            if (i == 5) {
//                System.out.println(i);
//                list.remove(i);
////            }
//        }

        System.out.println(list);
    }
}



