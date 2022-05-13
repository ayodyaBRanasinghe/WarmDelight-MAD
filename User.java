package com.warmdelightapp.Model;

public class User {
    String UserName,Email,ContactNo,Password;

    public User() {
    }


    public User(String userName, String email, String contactNo, String password) {
        UserName = userName;
        Email = email;
        ContactNo = contactNo;
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }




}
