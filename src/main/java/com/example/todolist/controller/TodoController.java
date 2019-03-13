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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("todo")
public class TodoController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TodoController.class);
    private final TodoRepository todoRepository;

    @Autowired
    private TodoController (TodoRepository todoRepository){this.todoRepository = todoRepository;}

    private List<Todo> todoList = new ArrayList<>();

    @GetMapping()
    //@RequestMapping(method = RequestMethod.GET, path = "/")
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
//
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

//    @PutMapping("/{id}")
//    public ResponseEntity<Todo> udpateTodo(@RequestBody Todo todo, @PathVariable long id){
//        Todo dbTodo = todoRepository.findById(id).orElse(todo);
//        dbTodo.setTitle(todo.getTitle() != null && todo.getTitle().isEmpty()? todo.getTitle() : dbTodo.getTitle());
//        dbTodo.setTitle(todo.getDescription() != null && todo.getDescription().isEmpty()? todo.getDescription() : dbTodo.getDescription());
//        dbTodo.setFinish(todo.getFinish() != null ? todo.getFinish() : dbTodo.getFinish());
//
//        return new ResponseEntity<>(dbTodo, HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Todo>> deleteTodo(@RequestBody Todo todo, @PathVariable long id){
        todoList.removeIf(t -> t.getId().equals(id));
        return new ResponseEntity(HttpStatus.OK);
    }



}
