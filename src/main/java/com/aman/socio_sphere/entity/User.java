package com.aman.socio_sphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean isAdmin = false;

//    private String profilePicture;
//
//    private String coverPicture;

    private String about;

    private String livesIn;

    private String worksAt;

    private String relationship;

    private String country;

    private List<Long> followers;

    private List<Long> following;

    private Timestamp tsCreated;

    private Timestamp tsModified;
}
