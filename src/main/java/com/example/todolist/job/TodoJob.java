package com.example.todolist.job;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TodoJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoJob.class);

    private final TodoRepository todoRepository;

    @Autowired
    public TodoJob(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @Scheduled(cron = "0/10 * * ? * *")
    public void execute(){
        LOGGER.info("Starting TodoJob");
        try{
            Todo todo = new Todo();
            todo.setTitle("Title");
            todo.setDescription("Description");
            todo.setFinish(new Date());
            todoRepository.save(todo);
        }catch (Exception e){
            LOGGER.error("Error found on TodoJob: ", e);
        }
    }

}
