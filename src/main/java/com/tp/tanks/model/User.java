package com.tp.tanks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    public User()
    {}

    public User(Long id, String username, String email, String password)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(User other)
    {
        this.id = other.id;
        this.username = other.username;
        this.email = other.email;
        this.password = other.password;
    }

    public Long getId() { return id; }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @JsonIgnore
    public String getPassword() { return password; }
    @JsonProperty
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, username='%s', email='%s']",
                id, username, email);
    }
}