package com.aman.socio_sphere.service.impl;

import com.aman.socio_sphere.dto.PostDto;
import com.aman.socio_sphere.entity.Post;
import com.aman.socio_sphere.entity.User;
import com.aman.socio_sphere.repository.PostRepository;
import com.aman.socio_sphere.service.PostService;
import com.aman.socio_sphere.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Override
    public PostDto createPost(PostDto postDto, Long id) {
        logger.info("PostServiceImpl -->> createPost -->> START");
        try {
            User user = userService.getUserById(id);
            if (null == user)
                throw new RuntimeException("user not found");

            Post newPost = new Post();
            newPost.setUser(user);
            convertDtoToEntity(postDto, newPost);
            newPost.setTsCreated(Timestamp.valueOf(LocalDateTime.now()));
            newPost.setTsModified(Timestamp.valueOf(LocalDateTime.now()));

            postRepository.save(newPost);
            logger.info("PostServiceImpl -->> createPost -->> END");
            return postDto;
        } catch (Exception e) {
            logger.error("error creating post : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PostDto> getAllPostById(Long id) {
        logger.info("PostServiceImpl -->> getAllPostById -->> START");
        try {
            User user = userService.getUserById(id);
            if (null == user)
                throw new RuntimeException("user not found");

            List<Post> optionalPost = postRepository.findByUser_id(id);
            if (!optionalPost.isEmpty()) {
                logger.info("PostServiceImpl -->> getAllPostById -->> END");

                return optionalPost
                        .stream()
                        .map(this::convertEntityToDto)
                        .collect(Collectors.toList());
            } else {
                logger.debug("user {}, has not post", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("exception while fetching all post for user with id {} : {}", id, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePostByPostId(Long id) {
        logger.info("PostServiceImpl -->> deletePostByPostId -->> START");
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(id);

            logger.info("PostServiceImpl -->> deletePostByPostId -->> END");
        } else {
            logger.debug("post not present with post_id : {}", id);
        }
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        logger.info("PostServiceImpl -->> updatePost -->> START");
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post currentPost = optionalPost.get();
            if (null != postDto.getDescription())
                currentPost.setDescription(postDto.getDescription());
            if (null != postDto.getLocation())
                currentPost.setLocation(postDto.getLocation());
            currentPost.setTsModified(Timestamp.valueOf(LocalDateTime.now()));
            postRepository.save(currentPost);

            logger.info("PostServiceImpl -->> updatePost -->> END");
            return postDto;
        } else {
            logger.debug("post with post_id : {} is not present", id);
            return null;
        }
    }

    @Override
    public Long likeDislike(Long userId, Long postId) {
        logger.info("PostServiceImpl -->> likeDislike -->> START");
        User user = userService.getUserById(userId);
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post currentPost = optionalPost.get();
            boolean isPresent = currentPost.getLikedByUsers()
                    .stream()
                    .anyMatch(currentUser -> currentUser.equals(user));
            if (isPresent) {
                currentPost.getLikedByUsers().remove(user);
            } else {
                List<User> users = currentPost.getLikedByUsers();
                users.add(user);
                currentPost.setLikedByUsers(users);
            }
            currentPost.setLikes((long) currentPost.getLikedByUsers().size());
            postRepository.save(currentPost);
            logger.info("PostServiceImpl -->> likeDislike -->> END");
            return currentPost.getLikes();
        }
        return 0L;
    }

    private void convertDtoToEntity(PostDto postDto, Post post) {
        if (null != postDto.getPostId())
            post.setPostId(postDto.getPostId());
        if (null != postDto.getDescription())
            post.setDescription(postDto.getDescription());
        if (null != postDto.getLocation())
            post.setLocation(postDto.getLocation());
    }

    private PostDto convertEntityToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());
        postDto.setLikes(post.getLikes());
        postDto.setComments(post.getComments());
        postDto.setDescription(post.getDescription());
        postDto.setLocation(post.getLocation());
        postDto.setTsCreated(post.getTsCreated());
        postDto.setTsModified(post.getTsModified());

        return postDto;
    }
}
