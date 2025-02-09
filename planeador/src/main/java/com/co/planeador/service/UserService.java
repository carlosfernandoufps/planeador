package com.co.planeador.service;

import com.co.planeador.controller.dto.request.UpdatePasswordRequestDto;
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

    public void updatePassword(UpdatePasswordRequestDto dto, Integer userId){
        User user = userDao.findById(userId).orElseThrow(()-> new CustomException("No existe usuario con id provisto"));
        if(!user.getPassword().equals(dto.getActualPassword())){
            throw new CustomException("Contraseña actual incorrecta");
        }
        if(!dto.getNewPassword().equals(dto.getNewPasswordConfirmation())){
            throw new CustomException("Confirmación de nueva contraseña no coincide");
        }
        user.setPassword(dto.getNewPassword());
        userDao.save(user);
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }

}
