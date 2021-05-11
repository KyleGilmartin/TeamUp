package edu.kylegilmartin.teamup.LoginRegister;

public class User {
    public String fullName,age,email,admin,kd,consoletype,nameid;

    public User(){

    }

    public User(String fullName, String age, String email, String admin, String kd, String consoletype, String nameid) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.admin = admin;
        this.kd = kd;
        this.consoletype = consoletype;
        this.nameid = nameid;
    }
}
