package com.aman.socio_sphere.controller;

import com.aman.socio_sphere.dto.PostDto;
import com.aman.socio_sphere.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    //    create post
    @PostMapping("/users/{id}")
    private ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long id) {
        PostDto createdPost = postService.createPost(postDto, id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdPost);
    }

    //    get all post of a user
    @GetMapping("/users/{id}")
    private ResponseEntity<List<PostDto>> getAllPostForUser(@PathVariable Long id) {
        List<PostDto> allPosts = postService.getAllPostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(allPosts);
    }

    //    delete a post
    @DeleteMapping("/{postId}")
    private ResponseEntity<Void> deletePostById(@PathVariable Long postId) {
        postService.deletePostByPostId(postId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{postId}")
    private ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId) {
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    //    like/dislike a post
    @PatchMapping("/{userId}/like/{postId}")
    private ResponseEntity<Long> likeDislikePost(@PathVariable Long userId, @PathVariable Long postId) {
        Long likes = postService.likeDislike(userId, postId);
        return ResponseEntity.status(HttpStatus.OK).body(likes);
    }

}
