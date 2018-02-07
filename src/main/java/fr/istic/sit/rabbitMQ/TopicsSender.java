package fr.istic.sit.rabbitMQ;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicsSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    public void sendDroneLocation() {
        sendDroneAny("location","This is drone location");

    }

    public void sendDroneState() {
        sendDroneAny("state","This is drone State");
    }

    private void sendDroneAny(String key,String message) {
        template.convertAndSend(topic.getName(), "drone." + key, message);
    }


}
