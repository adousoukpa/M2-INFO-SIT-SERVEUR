package fr.istic.sit.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventPublisherService {

    @Autowired
    AmqpTemplate template;

    public void publishMessage(String message) {
        template.convertAndSend(Constants.androidQueueName,message);
    }

}
