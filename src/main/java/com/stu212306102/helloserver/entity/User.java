package com.stu212306102.helloserver.entity;

public class User {
    private String name;
    private Long id;
    private Integer age;

    // 无参构造方法
    public User() {
    }

    // 全参构造方法
    public User(String name, Long id, Integer age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    // Getter和Setter方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}