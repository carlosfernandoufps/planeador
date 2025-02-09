package com.co.planeador.repository.dao;

import com.co.planeador.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    User findOneByInstitutionalEmail(String institutionalEmail);

}
