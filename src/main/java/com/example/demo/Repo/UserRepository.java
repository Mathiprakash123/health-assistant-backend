package com.example.demo.Repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.modal.UserRegister;

public interface UserRepository extends JpaRepository<UserRegister, Integer> {
	
	UserRegister findAllByEmail(String email);

	

}