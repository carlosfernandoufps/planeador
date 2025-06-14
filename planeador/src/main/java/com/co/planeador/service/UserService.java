package com.co.planeador.service;

import com.co.planeador.controller.dto.request.CreateUserRequestDto;
import com.co.planeador.controller.dto.request.UpdatePasswordRequestDto;
import com.co.planeador.controller.dto.request.UpdatePasswordUsingOtpRequestDto;
import com.co.planeador.exception.CustomException;
import com.co.planeador.repository.dao.UserDao;
import com.co.planeador.repository.entities.Profile;
import com.co.planeador.repository.entities.User;
import com.co.planeador.service.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserDao userDao;
    private final ProfileService profileService;
    private final EmailService emailService;
    private final OTPService otpService;
    private static final String WELCOME_SUBJECT_EMAIL_MESSAGE = "Bienvenido a Planeador UFPS";
    private static final String ONE_TIME_CODE_MESSAGE = "Código para actualizar contraseña Planeador UFPS";

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

    public void updatePasswordUsingOTP(UpdatePasswordUsingOtpRequestDto dto){
        User user = userDao.findOneByInstitutionalEmail(dto.getInstitutionalEmail());
        if(null == user){
            throw new CustomException("No existe usuario con correo provisto");
        }
        boolean isOtpValid = otpService.validateOtp(dto.getInstitutionalEmail(), dto.getOtp());
        if(!isOtpValid) {
            throw new CustomException("OTP inválido");
        }
        String encryptedPassword = PasswordUtil.encodePassword(dto.getNewPassword());
        user.setPassword(encryptedPassword);
        userDao.save(user);
    }

    public void generateOtp(String institutionalEmail){
        User user = userDao.findOneByInstitutionalEmail(institutionalEmail);
        if(null == user){
            throw new CustomException("No existe usuario con correo provisto");
        }
        String otp = otpService.generateOtp(institutionalEmail);
        sendOtpByEmail(institutionalEmail, otp);
    }

    private void sendOtpByEmail(String institutionalEmail, String otp){
        try{
            ClassPathResource htmlResource = new ClassPathResource("templates/otp.html");
            String html = StreamUtils.copyToString(htmlResource.getInputStream(), StandardCharsets.UTF_8);
            String emailBody = html.replace("${OTP_CODE}", otp);
            emailService.sendHtmlEmail(institutionalEmail, ONE_TIME_CODE_MESSAGE, emailBody);
        } catch (IOException ex){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al enviar OTP");
        }
    }

    public void createUser(CreateUserRequestDto dto){
        if(null == dto.getInstitutionalEmail() || null == dto.getProfileType() || null == dto.getName()){
            throw new CustomException("Faltan datos requeridos");
        }
        String password = PasswordUtil.generateRandomPassword();
        String encryptedPassword = PasswordUtil.encodePassword(password);
        User user = new User();
        user.setPassword(encryptedPassword);
        user.setInstitutionalEmail(dto.getInstitutionalEmail());
        User userSaved = userDao.save(user);
        Profile profileSaved = profileService.createProfile(userSaved.getId(), dto);
        sendEmail(profileSaved.getName(), user.getInstitutionalEmail(), password);
    }

    private void sendEmail(String userName, String institutionalEmail, String password){
        try{
            String emailBody = buildEmailBody(userName, institutionalEmail, password);
            emailService.sendHtmlEmail(institutionalEmail, WELCOME_SUBJECT_EMAIL_MESSAGE, emailBody);
        } catch (CustomException ex){
            log.error("Error sending email");
        }
    }

    private String buildEmailBody(String userName, String institutionalEmail, String password) {
        try{
            ClassPathResource htmlResource = new ClassPathResource("templates/welcome-email.html");
            String html = StreamUtils.copyToString(htmlResource.getInputStream(), StandardCharsets.UTF_8);

            return html.replace("${user_name}", userName)
                    .replace("${institutional_email}", institutionalEmail)
                    .replace("${password}", password);
        } catch (IOException ex){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear email");
        }
    }

}
