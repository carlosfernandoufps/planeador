package com.co.planeador.repository.dao;

import com.co.planeador.repository.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDao extends JpaRepository<Profile, Integer> {

    Profile findOneByUserId(Integer userId);

}
