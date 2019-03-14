package com.example.todolist.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TodoConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoConsumer.class);

    @RabbitListener(queues = "com.example.todolist.todo.johnnes")
    public void receiveMesssage(Message message){
        String messageBody = new String(message.getBody());
        LOGGER.info(messageBody);
    }

}
