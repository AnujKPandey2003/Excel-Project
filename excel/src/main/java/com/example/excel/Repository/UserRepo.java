package com.example.excel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.excel.model.User;
@Repository
public interface UserRepo extends JpaRepository<User,Long> {
  List<User> findByName(String name);
  boolean existsByEmail(String email);
  User findByEmail(String email);
}
