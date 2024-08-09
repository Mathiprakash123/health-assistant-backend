package com.example.demo.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modal.DoctorEntity;
import com.example.demo.modal.TrainerEntity;

public interface TrainerRepo  extends JpaRepository<DoctorEntity, Integer>{

	TrainerEntity findAllByEmail(String email);

	void save(TrainerEntity trainer);

}
