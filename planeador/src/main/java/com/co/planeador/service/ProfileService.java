package com.co.planeador.service;

import com.co.planeador.controller.dto.request.CreateUserRequestDto;
import com.co.planeador.controller.dto.request.UpdateProfileRequestDto;
import com.co.planeador.controller.dto.response.ProfileResponseDto;
import com.co.planeador.repository.dao.ProfileDao;
import com.co.planeador.repository.entities.Director;
import com.co.planeador.repository.entities.Profile;
import com.co.planeador.repository.entities.ProfileType;
import com.co.planeador.repository.entities.Teacher;
import com.co.planeador.repository.entities.User;
import com.co.planeador.service.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileDao profileDao;
    private final ProfileMapper profileMapper;

    public ProfileType getProfileTypeByUserId(Integer userId){
        Profile profile = profileDao.findOneByUserId(userId);
        return profile.getProfileType();
    }

    public ProfileResponseDto getProfileInfoByUserId(Integer userId){
        Profile profile = profileDao.findOneByUserId(userId);
        return profileMapper.profileToDto(profile);
    }

    public List<ProfileResponseDto> getProfilesByProfileType(ProfileType profileType){
        List<Profile> profiles = profileDao.findByProfileType(profileType);
        return profileMapper.profileToDto(profiles);
    }

    public ProfileResponseDto updateProfile(UpdateProfileRequestDto dto, Integer userId){
        Profile profile = profileDao.findOneByUserId(userId);
        profile.setCode(dto.getCode());
        profile.setName(dto.getName());
        profile.setPhone(dto.getPhone());
        profile.setPersonalEmail(dto.getPersonalEmail());
        profileDao.save(profile);
        return profileMapper.profileToDto(profile);
    }

    public Profile createProfile(Integer userId, CreateUserRequestDto dto){
        Profile profile = dto.getProfileType().equals(ProfileType.DIRECTOR) ? new Director() : new Teacher();
        profile.setUserId(userId);
        profile.setName(dto.getName());
        profile.setProfileType(dto.getProfileType());
        profile.setCode(null == dto.getCode() ? "" : dto.getCode());
        profile.setPhone(null == dto.getPhone() ? "" : dto.getPhone());
        profile.setPersonalEmail(null == dto.getPersonalEmail() ? "" : dto.getPersonalEmail());
        return profileDao.save(profile);
    }

}
