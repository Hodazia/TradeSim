package com.example.bcknd.DTO.User;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String email;
    private String password;

    public UserRequest(String email,String pswd)
    {
        this.email = email;
        this.password  = pswd;
    }
}
