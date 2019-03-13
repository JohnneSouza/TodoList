package com.example.todolist.repository;

import com.example.todolist.model.Todo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long> {

    @Query(value = "select t from Todo t where t.title = ?1",
    countQuery = "select count(t) from Todo t where t.title = ?1")
    List<Todo> findByTitle (String title, PageRequest pageRequest);

    @Query("select t from Todo t where t.title = ?1 and t.id = ?2")
    List<Todo> findByTitleAndId (String title, Long id);

}
