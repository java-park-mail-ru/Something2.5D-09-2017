package com.tp.tanks.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String username;

    private Boolean mouseControlEnabled;

    @JsonCreator
    public User() {
    }

    public User(Long id, String username, String email, String password, Boolean mouseControlEnabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.mouseControlEnabled = mouseControlEnabled;
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getMouseControlEnabled() {
        return mouseControlEnabled;
    }

    public void setMouseControlEnabled(Boolean mouseControlEnabled) {
        this.mouseControlEnabled = mouseControlEnabled;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, username='%s', email='%s']",
                id, username, email);
    }
}