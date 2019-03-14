package com.example.todolist.controller;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;


    private List<Todo> todoList = new ArrayList<>();

    @GetMapping()
    public ResponseEntity findAll(@RequestParam(value = "title",required = false) String title,
                                  @RequestParam(value = "id", required = false) Long id,
                                  @RequestParam(value = "offset", required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "limit", required = false, defaultValue = "5") int size,
                                  Sort sort){

//        LOGGER.trace("Trace");
//        LOGGER.debug("Debug");
//        LOGGER.info("Info");
//        LOGGER.warn("Warn");
//        LOGGER.error("Error");
//        LOGGER.info("findAll: title: {} | id: {} | offset: {} | limit: {} | sort: {}", title, id, page, size, sort);
//
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if(id != null) {
            return new ResponseEntity(todoRepository.findById(id), HttpStatus.OK);
        }

        if(title != null) {
            return new ResponseEntity(todoRepository.findByTitle(title, pageRequest), HttpStatus.OK);
        }
        return new ResponseEntity(todoRepository.findAll(pageRequest), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
        Todo dbTodo = todoRepository.save(todo);
        return new ResponseEntity<>(dbTodo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> udpateTodo(@RequestBody Todo todo, @PathVariable long id) {
        todoList.stream().filter(t -> t.getId().equals(id)).forEach(t -> {
            t.setTitle(todo.getTitle());
            t.setDescription(todo.getDescription());
            t.setFinish(todo.getFinish());
        });
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteTodo (@PathVariable long id){
        todoRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }



}
