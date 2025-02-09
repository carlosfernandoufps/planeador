package com.co.planeador.service;

import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.UserDao;
import com.co.planeador.repository.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public User login(String institutionalEmail, String password){
        User user = userDao.findOneByInstitutionalEmail(institutionalEmail);
        if(null == user){
            throw new CustomException("No existe usuario con correo provisto");
        }
        if(!user.getPassword().equals(password)){
            throw new CustomException("Password inválido");
        }
        return user;
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }

}
