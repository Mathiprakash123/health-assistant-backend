package com.example.demo.Repo;





import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	 List<Appointment> findByDoctorId(int doctorId);

	// List<Appointment> findByDoctorId(Long doctorId);
	List<Appointment> findByUserIdAndDoctorId(int userId, int doctorId);

    List<Appointment> findByUserId(int userId);

}
