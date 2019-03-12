package com.example.todolist.controller;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    private List<Todo> todoList = new ArrayList<>();

    @GetMapping
    public ResponseEntity findAll(){
        return new ResponseEntity(todoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity findByTittle(@RequestParam(value = "title", required = false) String title){
        if (title !=null) {
            return new ResponseEntity(todoRepository.findByTitle(title), HttpStatus.OK);
        }
        return new ResponseEntity(todoRepository.findAll(), HttpStatus.OK);
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