package com.aman.socio_sphere.repository;

import com.aman.socio_sphere.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser_id(Long userId);
}
