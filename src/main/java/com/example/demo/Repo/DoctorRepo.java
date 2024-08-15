package com.example.demo.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modal.DoctorEntity;

public interface DoctorRepo  extends JpaRepository<DoctorEntity, Integer>{

	DoctorEntity findAllByEmail(String email);

	Optional<DoctorEntity> findById(Long id);

}