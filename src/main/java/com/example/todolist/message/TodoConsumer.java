package com.example.todolist.message;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TodoConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoConsumer.class);
    private final TodoRepository todoRepository;

    @Autowired
    public TodoConsumer(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @RabbitListener(queues = "com.example.todolist.todo.johnnes")
    public void receiveMesssage(Message message){
        String messageBody = new String(message.getBody());
        LOGGER.info(messageBody);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Todo todo = objectMapper.readValue(message.getBody(), Todo.class);
            Todo dbTodo = todoRepository.save(todo);
            LOGGER.info("Todo Created: {}", dbTodo);
        }catch (IOException e){
            LOGGER.error("Error converting message: ", e);
        }
    }

}
