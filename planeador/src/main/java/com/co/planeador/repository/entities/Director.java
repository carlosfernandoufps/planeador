package com.co.planeador.repository.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DIRECTOR")
public class Director extends Profile{
}
