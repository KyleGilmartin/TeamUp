package edu.kylegilmartin.teamup.LoginRegister;

public class User {
    public String fullName,age,email,admin;

    public User(){

    }

    public User(String fullName, String age, String email,String admin) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.admin = admin;
    }
}
