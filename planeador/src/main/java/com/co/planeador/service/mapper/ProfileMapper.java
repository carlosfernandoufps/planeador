package com.co.planeador.service.mapper;

import com.co.planeador.controller.dto.response.ProfileResponseDto;
import com.co.planeador.repository.dto.ProfileUserDto;
import com.co.planeador.repository.entities.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileMapper {

    public ProfileResponseDto profileToDto(ProfileUserDto profile){
        ProfileResponseDto dto = new ProfileResponseDto();
        dto.setCode(profile.getCode());
        dto.setName(profile.getName());
        dto.setPhone(profile.getPhone());
        dto.setPersonalEmail(profile.getPersonalEmail());
        dto.setId(profile.getId());
        dto.setInstitutionalEmail(profile.getInstitutionalEmail());
        return dto;
    }

    public List<ProfileResponseDto> profileToDto(List<ProfileUserDto> profiles){
        List<ProfileResponseDto> dtoList = new ArrayList<>();
        for(ProfileUserDto profile: profiles){
            dtoList.add(profileToDto(profile));
        }
        return dtoList;
    }

    public ProfileResponseDto profileToDto(Profile profile){
        ProfileResponseDto dto = new ProfileResponseDto();
        dto.setCode(profile.getCode());
        dto.setName(profile.getName());
        dto.setPhone(profile.getPhone());
        dto.setPersonalEmail(profile.getPersonalEmail());
        dto.setId(profile.getId());
        return dto;
    }


}
