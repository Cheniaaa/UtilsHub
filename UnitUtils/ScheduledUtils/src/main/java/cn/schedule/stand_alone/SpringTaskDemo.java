package cn.schedule.stand_alone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description : spring task是spring3以上版本自带的定时任务
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2023/1/10 03:05:05
 * @Version : 1.0
 */
@EnableScheduling
@Component
public class SpringTaskDemo {
    private final Logger logger = LoggerFactory.getLogger(SpringTaskDemo.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "HH:mm:ss");

    /**
     * fixedRate：固定速率执行。每5秒执行一次。
     */
    @Scheduled(fixedRate = 5000)
    public void invokeTaskWithFixedRate() {
        logger.info("Fixed Rate Task :  Current Time  is  {}",
                dateFormat.format(new Date()));
    }

    /**
     * fixedDelay：固定延迟执行。距离上一次调用成功后2秒才执。
     */
    @Scheduled(fixedDelay = 2000)
    public void invokeTaskWithFixedDelay() {
        try {
            TimeUnit.SECONDS.sleep(3);
            logger.info("Fixed Delay Task : Current Time  is  {}",
                    dateFormat.format(new Date()));
        } catch (InterruptedException e) {
            logger.error("invoke task error", e);
        }
    }

    /**
     * initialDelay:初始延迟。任务的第一次执行将延迟5秒，然后将以5秒的固定间隔执行。
     */
    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void invokeTaskWithInitialDelay() {
        logger.info("Task with Initial Delay : Current Time is  {}",
                dateFormat.format(new Date()));
    }


    /*
      spring4以上的版本中，cron表达式包含6个参数：[秒] [分] [时] [日] [月] [周] [年]

      支持几个常用的特殊符号：
          *：表示任何时间触发任务
          ,：表示指定的时间触发任务
          -：表示一段时间内触发任务
          /：表示从哪一个时刻开始，每隔多长时间触发一次任务。
          ?：表示用于月中的天和周中的天两个子表达式，表示不指定值。

      cron表达式参数具体含义：
          秒，取值范围：0-59，支持*、,、-、/。
          分，取值范围：0-59，支持*、,、-、/。
          时，取值范围：0-23，支持*、,、-、/。
          日期，取值范围：1-31，支持*、,、-、/。比秒多了?，表示如果指定的星期触发了，则配置的日期变成无效。
          月，取值范围：1-12，支持*、,、-、/。
          星期，取值范围：1~7，1 代表星期天，6 代表星期六，其他的以此类推。支持*、,、-、/、?。比秒多了?，表示如果指定的日期触发了，则配置的星期变成无效。

      常见cron表达式使用举例：
          0 0 0 1 * ?  每月1号零点执行
          0 0 2 * * ?  每天凌晨2点执行
          0 0/5 11 * * ? 每天11点-11点55分，每隔5分钟执行一次
          0 0 18 ? * WED 每周三下午6点执行
          0 0-5 14 * * ?  在每天下午2点到下午2:05期间的每1分钟触发
          0 10,44 14 ? 3 WED  每年三月的星期三的下午2:10和2:44触发

          网址：https://cron.qqe2.com/
     */

    /**
     * cron：使用Cron表达式,每隔5秒执行一次
     */
    @Scheduled(cron = "0/5 * * * * ? ")
    public void invokeTaskWithCronExpression() {
        logger.info("Task Cron Expression:  Current Time  is  {}",
                dateFormat.format(new Date()));
    }

}
