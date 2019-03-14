package com.example.todolist.message;

import com.example.todolist.model.Todo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TodoProducer(RabbitTemplate rabbitTemplater){
        this.rabbitTemplate = rabbitTemplater;
    }

    public void sendTodoMessage(Todo todo){
        rabbitTemplate.convertAndSend("com.example.todolist.todo.johnnes", todo);
    }


}
