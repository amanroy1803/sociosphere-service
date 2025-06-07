package com.aman.socio_sphere.service;

import com.aman.socio_sphere.dto.UserDto;
import com.aman.socio_sphere.entity.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<User> getAllUsers();
    User getUserById(Long id);
    void updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);

//    follow and unfollow user
}
