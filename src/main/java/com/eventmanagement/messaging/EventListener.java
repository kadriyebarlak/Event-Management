package com.eventmanagement.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventmanagement.RabbitConfig;
import com.eventmanagement.dto.EventResponse;

@Component
public class EventListener {

	@RabbitListener(queues = RabbitConfig.QUEUE_NAME)
	public void handleEventCreated(EventResponse event) {
		System.out.println("Received Event: " + event.getName());
	}
}
