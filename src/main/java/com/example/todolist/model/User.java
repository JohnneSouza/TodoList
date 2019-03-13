package com.example.todolist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    Long id;

    @NotEmpty
    @Column(unique = true)
    private String userName;

    @NotEmpty
    private String name;

    @NotNull
    private boolean admin;

    @NotEmpty
    private String password;

}
