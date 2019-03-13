package com.example.todolist.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Todo {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    private Date finish;
}
