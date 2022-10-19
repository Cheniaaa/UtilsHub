package demo;

import cn.publisher.PublisherApplication;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/14 02:04:04
 * @Version : 1.0
 */

@SpringBootTest(classes = PublisherApplication.class)
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //测试简单模型和工作模型
    @Test
    public void simpleSendMsg() {
        String queueName = "simple.queue";
        String message = "hello_world";
        rabbitTemplate.convertAndSend(queueName, message);
    }

    //测试广播模型(发送到所有绑定交换机的队列)
    @Test
    public void fanoutSendMsg() {
        String exchangeName = "fanout.exchange";
        String message = "hello,everyone";
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }


    //测试路由模型（发送到绑定交换机且符合路由key的队列）
    @Test
    public void directSendMsg() {
        String exchangeName = "direct.exchange";
        String message = "hello,blue";
        rabbitTemplate.convertAndSend(exchangeName, "blue", message);
    }


    //测试话题模型
    @Test
    public void topicSendMsg() {
        String exchangeName = "topic.exchange";
        String message = "hello,topic";
        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);//key为【china.#】和【#.news】都能接收到
    }


}
