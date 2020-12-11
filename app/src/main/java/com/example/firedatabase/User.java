package com.example.firedatabase;


public class User {
    public String id, name, sec_name, email, image_id;

    //пустой конструктор
    public User(){

    }
    //не пустой конструктор
    public User(String id, String name, String sec_name, String email, String image_id) {
        this.id = id;
        this.name = name;
        this.sec_name = sec_name;
        this.email = email;
        this.image_id = image_id;
    }
}
