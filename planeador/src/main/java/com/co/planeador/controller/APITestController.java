package com.co.planeador.controller;

import com.co.planeador.repository.entities.User;
import com.co.planeador.security.annotation.SessionRequired;
import com.co.planeador.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class APITestController {

    private final UserService userService;

    @SessionRequired
    @GetMapping("/user")
    ResponseEntity<List<User>> executeGetAllUser(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
