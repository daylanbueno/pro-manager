package com.qima.promanagerapi.application.domain;

import com.qima.promanagerapi.application.enums.RoleEnum;

public class User {

    private Long id;
    private String name;
    private String login;
    private String password;
    private RoleEnum role;

    public User() {
    }

    public User(Long id, String name, String login, String password, RoleEnum role) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(Long id, String name, String longin, RoleEnum role) {
        this.id = id;
        this.name = name;
        this.login = longin;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
