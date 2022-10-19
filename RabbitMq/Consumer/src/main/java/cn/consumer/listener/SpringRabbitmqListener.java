package cn.consumer.listener;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/14 02:27:27
 * @Version : 1.0
 */
@Component
public class SpringRabbitmqListener {

    @RabbitListener(queues = "simple.queue")
    public void listenFromQueueMsg(String msg) {
        System.out.println("收到消息：【" + msg + "】");
    }

    @RabbitListener(queues = "fanout.queue")
    public void listenFanoutQueueMsg(String msg) {
        System.out.println("收到消息：【" + msg + "】");
    }


    //使用注解配置交换机和队列
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue"),
            exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}//对应routingKey
    ))
    public void listenDirectQueueMsg1(String msg) {
        System.out.println("queue1收到消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue"),
            exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
            key = {"yellow", "blue"}
    ))
    public void listenDirectQueueMsg2(String msg) {
        System.out.println("queue2收到消息：【" + msg + "】");
    }



    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue"),
            exchange = @Exchange(name = "topic.exchange", type = ExchangeTypes.TOPIC),
            key = "china.#"  //对应bindingKey
    ))
    public void listenTopicQueueMsg1(String msg) {
        System.out.println("queue1收到消息：【" + msg + "】");
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue"),
            exchange = @Exchange(name = "topic.exchange", type = ExchangeTypes.TOPIC),
            key = "#.news"  //对应bindingKey
    ))
    public void listenTopicQueueMsg2(String msg) {
        System.out.println("queue1收到消息：【" + msg + "】");
    }
}
