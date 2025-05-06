package com.eventmanagement.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.eventmanagement.RabbitConfig;
import com.eventmanagement.dto.EventResponse;

@Service
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishEventCreated(EventResponse event) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, event);
    }
}
