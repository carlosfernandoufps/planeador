package com.co.planeador.service;

import com.co.planeador.controller.dto.response.ProfileResponseDto;
import com.co.planeador.repository.dao.ProfileDao;
import com.co.planeador.repository.entities.Profile;
import com.co.planeador.repository.entities.ProfileType;
import com.co.planeador.service.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
