package com.sishuok.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sishuok.entity.User;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-12-22
 * <p>Version: 1.0
 */
@EnableAutoConfiguration
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/view")
    private User view(@RequestParam Long id, @RequestParam String username) {
        User user = new User();
        user.setId(id);
        user.setName(username);
        return user;
    }

    @RequestMapping("/addUser")
    private void addUser()
    {
    	System.out.println("addUser");
    }
    
    public static void main(String[] args) {
        SpringApplication.run(UserController.class);
    }
}
