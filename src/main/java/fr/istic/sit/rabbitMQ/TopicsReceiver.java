package fr.istic.sit.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class TopicsReceiver {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String in) throws InterruptedException {
        System.out.println("Location : " + in);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String in) throws InterruptedException {
        System.out.println("State : " + in);
    }

    @RabbitListener(queues = "#{autoDeleteQueue3.name}")
    public void receive3(String in) throws InterruptedException {
        System.out.println("All : " + in);
    }
}
