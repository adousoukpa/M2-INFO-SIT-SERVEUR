package fr.istic.sit.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class ListenerService {
    @RabbitListener(queues = Constants.androidQueueName)
    public void processQueue(Object message) {
        System.out.println("Message from AndroidQueue : " + message);
    }
}
