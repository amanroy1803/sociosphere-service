package com.aman.socio_sphere.dto;

import com.aman.socio_sphere.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long postId;

    private String description;

    private String location;

    private Long likes;

    private Long comments;

    private Timestamp tsCreated;

    private Timestamp tsModified;

}
