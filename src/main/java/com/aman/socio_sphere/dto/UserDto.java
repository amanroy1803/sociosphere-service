package com.aman.socio_sphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean isAdmin;

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
