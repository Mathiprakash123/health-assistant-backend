 package com.example.demo.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repo.DoctorRepo;
import com.example.demo.modal.DoctorEntity;
@Service
public class DoctorService {
	@Autowired
	private DoctorRepo doctorRepository;

	// public DoctorEntity findById(int i) {
	// 	  return doctorRepository.findById(i).orElse(null);
	// }
	public DoctorEntity checkDoctor(String email, String password) {
        return doctorRepository.findByEmailAndPassword(email, password);
    }
}
