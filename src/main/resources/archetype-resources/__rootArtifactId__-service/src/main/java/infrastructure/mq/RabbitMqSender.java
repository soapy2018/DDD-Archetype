package ${package}.infrastructure.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMqSender implements IMqSender {

    @Autowired
    private AmqpTemplate template;

    @Override
    public void send(String exchange, String topic, Object object) {
        String text = null;
        try {
            text = JSON.toJSONString(object);
            log.info("send msg to topic[{}],message:{}", topic, text);
            template.convertAndSend(exchange, topic, text);
        } catch (Exception e) {
            log.error(String.format("Fail to send msg to topic[%s].Message:%s.", topic, text), e);
        }
    }
}
