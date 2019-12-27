package classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/27 10:26
 */
public class MyClassloaderTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        System.out.println(System.getProperty("user.dir"));

        MyClassloader classloader = new MyClassloader("MyClassloader");
        Class<?> aClass = classloader.loadClass("classloader.MyObject");
        System.out.println(aClass);
        System.out.println(aClass.getClassLoader());
        //实例化对象
        Object obj = aClass.newInstance();
        //获取方法
        Method method = aClass.getMethod("hello", new Class[]{});
        //调用方法
        Object result = method.invoke(obj, new Object[]{});
        System.out.println(result);
    }
}
