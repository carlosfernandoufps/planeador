package com.co.planeador.repository.dao;

import com.co.planeador.repository.dto.ProfileUserDto;
import com.co.planeador.repository.entities.Profile;
import com.co.planeador.repository.entities.ProfileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileDao extends JpaRepository<Profile, Integer> {

    Profile findOneByUserId(Integer userId);
    List<Profile> findByProfileType(ProfileType profileType);

    @Query("SELECT p.id as id, p.name as name, u.institutionalEmail as institutionalEmail, " +
            "p.personalEmail as personalEmail, p.phone as phone, p.code as code FROM Profile p, User u" +
            " WHERE u.id = :id AND u.id = p.userId")
    ProfileUserDto getProfileUserInfo(@Param("id") Integer userId);

    @Query("SELECT p.id as id, p.name as name, u.institutionalEmail as institutionalEmail, " +
            "p.personalEmail as personalEmail, p.phone as phone, p.code as code FROM Profile p, User u" +
            " WHERE p.profileType = :profileType AND u.id = p.userId")
    List<ProfileUserDto> getProfileUserInfoByProfileType(ProfileType profileType);

}
