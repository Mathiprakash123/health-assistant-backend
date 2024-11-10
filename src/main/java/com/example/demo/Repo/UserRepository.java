package com.example.demo.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.UserRegister;

@Repository
public interface UserRepository extends JpaRepository<UserRegister, Integer> {

    UserRegister findByEmail(String email);

    UserRegister findAllByEmail(String email);

}
