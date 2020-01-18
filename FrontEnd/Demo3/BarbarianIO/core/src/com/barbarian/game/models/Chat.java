package com.barbarian.game.models;

public class Chat {

    private Integer id;
    private String message = "";

    public Chat(Integer id, String message) {
        this.id = id;
        this.message = message;
    }
    public Chat() {

    }

    public Integer getId() {
        return id;
    }

    public void seId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
