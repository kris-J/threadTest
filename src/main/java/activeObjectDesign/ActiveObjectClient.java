package activeObjectDesign;

/**
 * @author fangjie
 * @Description: 主动对象模式，将方法的调用使用多线程的方式变为异步的调用，不发生阻塞
 * @date 2019/12/18 17:04
 */
public class ActiveObjectClient {

    public static void main(String[] args) {
        //factory获取activeObject
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        //创建makeString的请求
        new MakeStringClientThread("alax", activeObject).start();
        //创建display的请求
        new DisplayClientThread("kris", activeObject).start();
    }
}
