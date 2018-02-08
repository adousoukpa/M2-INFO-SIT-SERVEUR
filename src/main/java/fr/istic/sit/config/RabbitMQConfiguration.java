package fr.istic.sit.config;

import fr.istic.sit.rabbitMQ.TopicsReceiver;
import fr.istic.sit.rabbitMQ.TopicsSender;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    /*************************************ConfigWorkingTopics******************************/

    @Bean
    public TopicExchange androidExchange() {
        return new TopicExchange("sitProjet.android");
    }


    @Bean
    public TopicsSender sender() {
        return new TopicsSender();
    }

    /*@Bean
    public Queue histoQueue() {
        return new Queue("histoQueue");
    }

    @Bean
    public Binding binding(TopicExchange androidExchange, Queue histoQueue) {
        return BindingBuilder.bind(histoQueue)
            .to(androidExchange)
            .with("#");
    }*/

    private static class ReceiverConfig {

        @Bean
        public TopicsReceiver receiver() {
            return new TopicsReceiver();
        }

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue3() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1(TopicExchange topic,
                                 Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1)
                .to(topic)
                .with("drone.location");
        }

        @Bean
        public Binding binding2(TopicExchange topic,
                                 Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2)
                .to(topic)
                .with("drone.state");
        }

        @Bean
        public Binding binding3(TopicExchange topic,
                                 Queue autoDeleteQueue3) {
            return BindingBuilder.bind(autoDeleteQueue3)
                .to(topic)
                .with("#");
        }
    }



    /******************************************ConfigWorkingQueue********************/

    /*@Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
            new CachingConnectionFactory("localhost",5672);
        System.out.println("ConnectionFactory : " + connectionFactory.toString());
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());

        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue myQueue() {
        return new Queue(Constants.androidQueueName);
    }

    @Bean
    public Queue mysecondQueue() {
        return new Queue(Constants.SecondandroidQueueName);
    }

    @Bean(name="rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerlistenerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }*/
}
