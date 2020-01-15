package timeScheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/14 10:25
 */
public class TimerScheduler {


    public static void main(String[] args) {
        timer();
    }

    /**
     * 不建议使用
     * 如果业务逻辑执行时间大于1秒，则按照业务逻辑执行结束时间进行轮训，不会确保每秒执行的间隔
     */
    private static void timer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("=============" + System.currentTimeMillis());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //延迟1秒，执行1次
//        timer.schedule(timerTask, 1000);
        //延迟1秒，每1秒执行1次
        timer.schedule(timerTask, 1000, 1000);
    }

    /**
     * 会确保时间间隔的执行，但任务时间长的话会不断开启线程
     */
    private static void crontab() {
        /**
         * * * * * * sh xxxx.sh
         */
    }

    /**
     * quartz方式，也可以保证时间间隔执行，不受业务逻辑的干扰
     *
     * @throws SchedulerException
     */
    private static void quartz() throws SchedulerException {
        //声明job
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class).withIdentity("Job1", "JGroup1").build();
        //声明trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "TGroup1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);

    }

    /**
     * quartz声明的job
     */
    static class QuartzJob implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("===============" + System.currentTimeMillis());
        }
    }
}
