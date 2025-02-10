package com.co.planeador.service;

import com.co.planeador.controller.dto.response.ProfileResponseDto;
import com.co.planeador.repository.dao.ProfileDao;
import com.co.planeador.repository.entities.Profile;
import com.co.planeador.repository.entities.ProfileType;
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

}
