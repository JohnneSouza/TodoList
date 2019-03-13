package com.example.todolist.repository;

import com.example.todolist.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserName(String userName);
}
