package exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author fangjie
 * @Description: 两个线程进行信息交换,两者的状态要一致，一个超时或中途退出，另外一个可能会一直等待
 *               交换的数据为同一个对象，所以会有线程安全问题
 * @date 2020/1/6 15:45
 */
public class ExchangerExample {

    public static void main(String[] args) {
        Exchanger exchanger = new Exchanger();

        new Thread("T1") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start ");
                try {
                    Object exchange = exchanger.exchange("i am from " + Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + " get info [" + exchange + "]");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end ");
            }
        }.start();

        new Thread("T2") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start ");
                try {
                    //休息一段时间再发送信息，其他会进入等待状态
                    TimeUnit.SECONDS.sleep(10);
                    Object exchange = exchanger.exchange("i am from " + Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + " get info [" + exchange + "]");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end ");
            }
        }.start();

    }
}
