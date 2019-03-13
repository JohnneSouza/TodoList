package com.example.todolist.repository;

import com.example.todolist.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long> {

    @Query(value = "select t from Todo t where t.title = ?1",
    countQuery = "select count(t) from Todo t where t.title = ?1")
    Page<Todo> findByTitle (String title, PageRequest pageRequest);

    @Query("select t from Todo t where t.title = ?1 and t.id = ?2")
    List<Todo> findByTitleAndId (String title, Long id);

}
