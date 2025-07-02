package com.aman.socio_sphere.service.impl;

import com.aman.socio_sphere.dto.UserDto;
import com.aman.socio_sphere.entity.User;
import com.aman.socio_sphere.repository.UserRepository;
import com.aman.socio_sphere.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("UserServiceImpl -->> createUser -->> START");
        try {
            User newUser = convertDtoToEntity(userDto, new User());
            newUser.setTsCreated(Timestamp.valueOf(LocalDateTime.now()));
            newUser.setTsModified(Timestamp.valueOf(LocalDateTime.now()));
            userRepository.save(newUser);
            logger.info("UserServiceImpl -->> createUser -->> END");
            return userDto;
        } catch (Exception e) {
            logger.error("error saving user : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("UserServiceImpl -->> getAllUsers -->> START");
        try {
            List<User> users = userRepository.findAll();
            logger.info("UserServiceImpl -->> getAllUsers -->> END");
            return users;
        } catch (Exception e) {
            logger.error("error which fetching all users : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User getUserById(Long id) {
        logger.info("UserServiceImpl -->> getUserById -->> START");
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                logger.info("UserServiceImpl -->> getUserById -->> END");
                return user;
            } else {
                logger.debug("user with id : {} not found", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Exception while fetching user with id {} : {}", id, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(Long id, UserDto userDto) {
        logger.info("UserServiceImpl -->> updateUser -->> START");
        if (!userRepository.existsById(id)) {
            logger.error("user with id : {} doesn't exist", id);
            throw new RuntimeException("User not found with id :" + id);
        }
        try {
            User existingUser = getUserById(id);
            User updatedUser = convertDtoToEntity(userDto, existingUser);
            updatedUser.setTsModified(Timestamp.valueOf(LocalDateTime.now()));
            logger.info("UserServiceImpl -->> updateUser -->> END");
            userRepository.save(updatedUser);
        } catch (Exception e) {
            logger.error("Error updating user with id : " + id + " - " + e.getMessage());
            throw new RuntimeException("Failed to update user", e);
        }
    }

    private User convertDtoToEntity(UserDto userDto, User user) {

        if (null != userDto.getEmail())
            user.setEmail(userDto.getEmail());
        if (null != userDto.getUsername())
            user.setUsername(userDto.getUsername());
        if (null != userDto.getPassword())
            user.setPassword(userDto.getPassword());
        if (null != userDto.getFirstName())
            user.setFirstName(userDto.getFirstName());
        if (null != userDto.getLastName())
            user.setLastName(userDto.getLastName());
        if (null != userDto.getLastName())
            user.setLastName(userDto.getLastName());
        if (null != userDto.getAbout())
            user.setAbout(userDto.getAbout());
        if (null != userDto.getLivesIn())
            user.setLivesIn(userDto.getLivesIn());
        if (null != userDto.getWorksAt())
            user.setWorksAt(userDto.getWorksAt());
        if (null != userDto.getRelationship())
            user.setRelationship(userDto.getRelationship());
        if (null != userDto.getCountry())
            user.setCountry(userDto.getCountry());

        return user;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        logger.info("UserServiceImpl -->> deleteUser -->> START");
        if (!userRepository.existsById(id)) {
            logger.error("user with id : {} not found", id);
            throw new RuntimeException("user not found with id: " + id);
        }

        try {
            userRepository.deleteById(id);
            logger.info("User with id : {} deleted successfully!!", id);
            logger.info("UserServiceImpl -->> deleteUser -->> END");
        } catch (Exception e) {
            throw new RuntimeException("Exception while deleting user", e);
        }
    }

    @Override
    public void followAndUnfollowUser(Long userId, Long userIdToBeFollowed) {
        User user = getUserById(userId);
        User userToBeFollowed = getUserById(userIdToBeFollowed);

        List<User> userFollowingList = user.getFollowing();
        List<User> userToBeFollowedFollowersList = userToBeFollowed.getFollowers();

        boolean isFollowing = false;
        if (!userFollowingList.isEmpty())
            isFollowing = userFollowingList
                    .stream()
                    .anyMatch(currentUser -> currentUser.equals(userToBeFollowed));

        if (isFollowing) {
            userFollowingList.remove(userToBeFollowed);
            userToBeFollowedFollowersList.remove(user);
        } else {
            userFollowingList.add(userToBeFollowed);
            userToBeFollowedFollowersList.add(user);
        }

        user.setFollowing(userFollowingList);
        userToBeFollowed.setFollowers(userToBeFollowedFollowersList);

        userRepository.save(user);
        userRepository.save(userToBeFollowed);
    }
}
