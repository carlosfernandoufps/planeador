package com.co.planeador.repository.dao;

import com.co.planeador.repository.entities.Profile;
import com.co.planeador.repository.entities.ProfileType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileDao extends JpaRepository<Profile, Integer> {

    Profile findOneByUserId(Integer userId);
    List<Profile> findByProfileType(ProfileType profileType);

}
