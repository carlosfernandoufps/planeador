package com.co.planeador.service.mapper;

import com.co.planeador.controller.dto.response.ProfileResponseDto;
import com.co.planeador.repository.entities.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileResponseDto profileToDto(Profile profile){
        ProfileResponseDto dto = new ProfileResponseDto();
        dto.setCode(profile.getCode());
        dto.setName(profile.getName());
        dto.setPhone(profile.getPhone());
        dto.setPersonalEmail(profile.getPersonalEmail());
        return dto;
    }


}
