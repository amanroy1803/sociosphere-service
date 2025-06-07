package com.aman.socio_sphere.repository;

import com.aman.socio_sphere.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
