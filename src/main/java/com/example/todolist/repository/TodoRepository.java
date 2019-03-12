package com.example.todolist.repository;

import com.example.todolist.model.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("todo")
public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findByTitle (String title);

}
