package com.example.todolist.job;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

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
            int random = new Random().nextInt(3);
            LOGGER.info(String.valueOf(random));
//            if (random == 2){
//                throw new Exception("\nNumber 3 was generated.\n");
//            }
            readFile();
        }catch (Exception e){
            LOGGER.error("Error found on TodoJob: ", e);
        }
    }

    private void readFile() {
        LOGGER.info("Reading CSV: ");
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader("loggin.csv"))
                    .withSkipLines(1)
                    .build();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                LOGGER.info("Value1: {} | Value2: {}", nextLine[0], nextLine[1]);
                Todo todo = new Todo();
                todo.setTitle(nextLine[0]);
                todo.setDescription(nextLine[1]);
                todo.setFinish(new Date());
                Todo dbTodo = todoRepository.save(todo);
                LOGGER.info("Inserting Todo: {}", dbTodo);
            }
        }catch (IOException ex){
            LOGGER.error("Error reading CSV File", ex);
        }

    }

}
