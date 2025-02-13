package com.co.planeador.service;

import com.co.planeador.controller.dto.request.CreateUserRequestDto;
import com.co.planeador.controller.dto.request.UpdatePasswordRequestDto;
import com.co.planeador.controller.dto.response.CreateUserResponseDto;
import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.UserDao;
import com.co.planeador.repository.entities.Profile;
import com.co.planeador.repository.entities.User;
import com.co.planeador.service.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final ProfileService profileService;

    public User login(String institutionalEmail, String password){
        User user = userDao.findOneByInstitutionalEmail(institutionalEmail);
        if(null == user){
            throw new CustomException("No existe usuario con correo provisto");
        }
        if(!PasswordUtil.passwordMatch(password, user.getPassword())){
            throw new CustomException("Password inválido");
        }
        return user;
    }

    public void updatePassword(UpdatePasswordRequestDto dto, Integer userId){
        User user = userDao.findById(userId).orElseThrow(()-> new CustomException("No existe usuario con id provisto"));
        if(!PasswordUtil.passwordMatch(dto.getActualPassword(), user.getPassword())){
            throw new CustomException("Contraseña actual incorrecta");
        }
        if(!dto.getNewPassword().equals(dto.getNewPasswordConfirmation())){
            throw new CustomException("Confirmación de nueva contraseña no coincide");
        }
        String encryptedPassword = PasswordUtil.encodePassword(dto.getNewPassword());
        user.setPassword(encryptedPassword);
        userDao.save(user);
    }

    public CreateUserResponseDto createUser(CreateUserRequestDto dto){
        String password = PasswordUtil.generateRandomPassword();
        String encryptedPassword = PasswordUtil.encodePassword(password);
        User user = new User();
        user.setPassword(encryptedPassword);
        user.setInstitutionalEmail(dto.getInstitutionalEmail());
        User userSaved = userDao.save(user);
        Profile profileSaved = profileService.createProfile(userSaved.getId(), dto);
        CreateUserResponseDto responseDto = new CreateUserResponseDto();
        responseDto.setName(profileSaved.getName());
        responseDto.setInstitutionalEmail(userSaved.getInstitutionalEmail());
        responseDto.setPassword(password);
        return responseDto;
    }

}
