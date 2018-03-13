package fr.istic.sit.rabbitMQ;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;


public class AndroidMissionSender {

    private static final String topicDrone = "drone";
    private static final String topicLocation = "location";
    private static final String topicState = "state";
    private static final String topicImage = "image";

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    private String missionId;

    public void sendLocation() {
        sendDroneAny(topicLocation,"This is drone location");
    }

    public void sendState() {
        sendDroneAny(topicState,"This is drone State");
    }

    public void sendImage(){
        sendMissionAny(topicImage,"This is image");
    }

    private void sendDroneAny(String key,String message) {
        sendMissionAny(missionId + "." + key, message);
    }

    private void sendMissionAny(String key,String message) {
        template.convertAndSend(topicDrone + "." + key , message);
    }


}
