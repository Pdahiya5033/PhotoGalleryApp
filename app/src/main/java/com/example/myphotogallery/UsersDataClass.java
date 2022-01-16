package com.example.myphotogallery;

public class UsersDataClass {
    private String email;
    private String userName;
    private String password;
    public UsersDataClass(){

    }
    public UsersDataClass(String id){
        email=id;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

}
