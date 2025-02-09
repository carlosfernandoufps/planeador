package com.co.planeador.service;

import com.co.planeador.repository.dao.ProfileDao;
import com.co.planeador.repository.entities.Profile;
import com.co.planeador.repository.entities.ProfileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileDao profileDao;

    public ProfileType getProfileTypeByUserId(Integer userId){
        Profile profile = profileDao.findOneByUserId(userId);
        return profile.getProfileType();
    }

}
