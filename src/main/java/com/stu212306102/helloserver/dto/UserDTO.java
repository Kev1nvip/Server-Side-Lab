package com.stu212306102.helloserver.dto;

public class UserDTO {
    private String username;
    private String password;

    // Getter 方法
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setter 方法
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}