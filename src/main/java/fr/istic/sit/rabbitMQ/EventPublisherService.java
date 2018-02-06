package fr.istic.sit.rabbitMQ;

import fr.istic.sit.domain.LocationDrone;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventPublisherService {

    @Autowired
    AmqpTemplate template;

    public void publishMessage(String message) {
        template.convertAndSend(Constants.androidQueueName,message);
    }

    public void publishDroneLocalisation(LocationDrone location){

        LocationDrone loc  = new LocationDrone();
        loc.setTime(LocalDateTime.now());
        loc.setAltitude(10.2);
        loc.setLatitude(10.2);
        template.convertAndSend(Constants.androidQueueName,loc,
            m -> {
                //m.getMessageProperties().setPriority(1);
                return m;
            });

    }

}
