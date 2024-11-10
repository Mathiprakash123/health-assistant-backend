package com.example.demo.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.modal.DoctorEntity;
import com.example.demo.modal.TrainerEntity;

public interface TrainerRepo extends JpaRepository<DoctorEntity, Integer> {

	@Query("SELECT t FROM TrainerEntity t WHERE t.email = :email")
	TrainerEntity findTrainersByEmail(@Param("email") String email);

	void save(TrainerEntity trainer);

}