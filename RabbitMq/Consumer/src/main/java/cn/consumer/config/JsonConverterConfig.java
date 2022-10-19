package cn.consumer.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : json转换器
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/14 18:55:55
 * @Version : 1.0
 */
@Configuration
public class JsonConverterConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
