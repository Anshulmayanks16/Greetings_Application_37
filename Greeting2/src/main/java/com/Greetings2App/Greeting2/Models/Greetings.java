package com.Greetings2App.Greeting2.Models;
import jakarta.persistence.*;

@Entity
@Table(name="AllGreetings")
public class Greetings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    public Greetings() {
    }

    public Greetings(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
