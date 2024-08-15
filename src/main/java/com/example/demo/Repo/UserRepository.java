package com.example.demo.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.UserRegister;

@Repository
public interface UserRepository extends JpaRepository<UserRegister, Integer> {

    UserRegister findByEmail(String email);

    // Optional<UserRegister> findById(Integer userId);

    // List<UserRegister> findAllById(Iterable<Integer> ids);

	UserRegister findAllByEmail(String email);

	// List<UserRegister> findAllById(List<Long> userIds);
    Optional<UserRegister> findById(Integer userId);

    // Use List<Long> if IDs are Long
    List<UserRegister> findAllById(Iterable<Integer> ids);
    Optional<UserRegister> findById(int id);
}
