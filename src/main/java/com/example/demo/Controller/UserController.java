package com.example.demo.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.example.demo.modal.UserRegister;

@RestController
public class UserController {

    @Autowired
    private UserService userservice;

    @GetMapping("/viewall")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<UserRegister> view() {
        return userservice.viewAllInfo();
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:5173")
    public String register(@RequestBody UserRegister registerInfo) {
        return userservice.addUser(registerInfo);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173")
    public String login(@RequestBody UserRegister loginInfo) {
        return userservice.checkUser(loginInfo.getEmail(), loginInfo.getPassword());
    }
    
    @GetMapping("/profile")
    @CrossOrigin(origins = "http://localhost:5173")
    public UserRegister profile(@RequestParam String email) {
        return userservice.getUserProfileByEmail(email);
    }

}
