package cn.schedule.stand_alone;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description : 使用ScheduledExecutorService接口（多线程）
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2023/1/10 02:39:39
 * @Version : 1.0
 */

public class ScheduledExecutorDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

        //按照固定频率执行，如果本次任务时间超过period时，下一个任务会在此任务结束后立刻执行
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("【JavaTask】：ScheduledExecutorService-1， 【时间】：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
            }
        }, 1, 3, TimeUnit.SECONDS);

        //按照固定延迟，从上一个任务结束后开始计算延迟
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("【JavaTask】：ScheduledExecutorService-2， 【时间】：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
            }
        }, 1, 5, TimeUnit.SECONDS);
    }
}
