package cn.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : 广播消息队列
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/14 18:11:11
 * @Version : 1.0
 */
@Configuration
public class FanoutExchangeConfig {

    //声明交换机
    @Bean
    public FanoutExchange fanoutExchange1() {
        return new FanoutExchange("fanout.exchange");
    }

    //声明队列
    @Bean
    public Queue queue1() {
        return new Queue("fanout.queue");
    }

    //绑定交换机和队列
    @Bean
    public Binding bindingQueue1(FanoutExchange fanoutExchange1, Queue queue1) {
        return BindingBuilder.bind(queue1).to(fanoutExchange1);
    }

    //声明交换机
    @Bean
    public FanoutExchange fanoutExchange2() {
        return new FanoutExchange("fanout.exchange");
    }

    //声明队列
    @Bean
    public Queue queue2() {
        return new Queue("fanout.queue");
    }

    //绑定交换机和队列
    @Bean
    public Binding bindingQueue2(FanoutExchange fanoutExchange1, Queue queue1) {
        return BindingBuilder.bind(queue1).to(fanoutExchange1);
    }
}
