package com.co.planeador.repository.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends Profile {
}
