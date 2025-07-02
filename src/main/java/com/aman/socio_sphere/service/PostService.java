package com.aman.socio_sphere.service;

import com.aman.socio_sphere.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Long id);

    List<PostDto> getAllPostById(Long id);

    void deletePostByPostId(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    Long likeDislike(Long userId, Long postId);
}
