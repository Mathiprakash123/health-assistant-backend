package com.example.demo.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modal.DoctorEntity;

public interface DoctorRepo  extends JpaRepository<DoctorEntity, Integer>{

	DoctorEntity findAllByEmail(String email);

}