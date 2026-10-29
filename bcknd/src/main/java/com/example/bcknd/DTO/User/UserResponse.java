package com.example.bcknd.DTO.User;


// when a user sends a request they shall get a response of user email and jwt token
// back to the client

import lombok.Getter;

@Getter

public class UserResponse {
    private String email;
    private String jwt_token;

    public UserResponse(String email,String jwt)
    {
        this.email = email;
        this.jwt_token = jwt;
    }
}
