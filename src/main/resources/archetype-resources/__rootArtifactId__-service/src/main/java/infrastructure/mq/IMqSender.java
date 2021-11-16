package ${package}.infrastructure.mq;

public interface IMqSender {

    void send(String exchange, String topic, Object object);
}
