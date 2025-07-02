package com.aman.socio_sphere.controller;

import com.aman.socio_sphere.dto.UserDto;
import com.aman.socio_sphere.entity.User;
import com.aman.socio_sphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //    Create User
    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUser);
    }

    //    Get All Users
    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //    Get User by Id
    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (null == user)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    //    Update User
    @PatchMapping(path = "/{id}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    //    Delete User
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    //    Follow a User
    @PatchMapping(path = "/{userId}/follow/{userIdToBeFollowed}")
    public ResponseEntity<String> followAndUnfollowUser(@PathVariable Long userId, @PathVariable Long userIdToBeFollowed) {
        if (userId.equals(userIdToBeFollowed))
            return ResponseEntity.badRequest().body(Map.of("error", "Cannot follow/unfollow yourself.").toString());

        userService.followAndUnfollowUser(userId, userIdToBeFollowed);
        return ResponseEntity.ok("user successfully followed/unfollowed");
    }
}
