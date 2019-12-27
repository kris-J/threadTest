package threadWorker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author fangjie
 * @Description: 限制线程同时运行数量
 * @date 2018/9/14 13:34
 */
public class ThreadWorkerLimit {

    private static final Integer MAX_WORKER_SIZE = 2;

    private static final Object LOCK = new Object();

    private static Integer workerSize = 1;

    public static void main(String[] args) {
        List<String> threadNameList = Arrays.asList("T1", "T2", "T3", "T4", "T5");
        List<Thread> threadList = new ArrayList<>();
        //创建线程并启动
        for (String each : threadNameList) {
            Thread eachThread = new Thread(each) {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " prepare to work");
                    //同步代码块
                    synchronized (LOCK) {
                        //如果当前工作数量已超过最大限制，则进行等待
                        while (workerSize > MAX_WORKER_SIZE) {
                            try {
                                System.out.println(Thread.currentThread().getName() + " into wait");
                                LOCK.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        workerSize++;
                    }

                    System.out.println(Thread.currentThread().getName() + " start work");
                    try {
                        Thread.sleep(2_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //执行完毕，进行释放
                    synchronized (LOCK) {
                        System.out.println(Thread.currentThread().getName() + " work done");
                        workerSize--;
                        LOCK.notifyAll();
                    }
                }
            };
            eachThread.start();
            threadList.add(eachThread);
        }
        //所有线程执行完成后在进行下一步操作
        for(Thread eachThread : threadList){
            try {
                eachThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("all work is done");

    }
}
