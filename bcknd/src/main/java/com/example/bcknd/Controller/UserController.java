package com.example.bcknd.Controller;

import com.example.bcknd.DTO.User.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserResponse> sayhi()
    {
        UserResponse response = new UserResponse("zia23hoda@gmail.com", "tkjwt#23");
        return ResponseEntity.status(200).body(response);
    }
}
