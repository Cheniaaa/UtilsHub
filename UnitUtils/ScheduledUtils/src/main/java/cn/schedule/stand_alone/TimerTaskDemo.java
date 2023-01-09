package cn.schedule.stand_alone;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description : JDK专门提供的定时器工具（单线程）
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2023/1/10 02:02:02
 * @Version : 1.0
 */

public class TimerTaskDemo {
    public static void main(String[] args) throws InterruptedException {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                System.out.println("当前时间: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) + "\t线程名称: " + Thread.currentThread().getName());
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 5000L;
        Date currentDate = new Date();

        /*
        测试延迟任务，此情况下两种方法区别较小（第三个参数为循环周期，缺省则代表不循环）
         */
//        timer.schedule(task, delay, 3000L);
//        timer.scheduleAtFixedRate(task, delay, 3000L);


        /*
        测试到达指定时间执行的任务
        如果指定的时间已经过去，scheduleAtFixedRate会执行完指定时间到当前时间的任务，而schedule则只从当前时间开始
         */
        Thread.sleep(3000);
        timer.scheduleAtFixedRate(task, currentDate, 3000L);
//        timer.schedule(task, currentDate, 3000L);
        System.out.println("定时任务启动.....");
    }
}
