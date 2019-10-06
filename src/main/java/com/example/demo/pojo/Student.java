package com.example.demo.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "student")
public class Student {

    @Id
    private Integer id;

    private String name;

    private Integer age;

    private Integer schoolId;
}
