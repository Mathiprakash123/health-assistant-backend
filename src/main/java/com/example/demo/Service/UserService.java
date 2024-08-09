package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repo.DoctorRepo;
import com.example.demo.Repo.TrainerRepo;
import com.example.demo.Repo.UserRepository;
import com.example.demo.modal.DoctorEntity;
import com.example.demo.modal.TrainerEntity;
import com.example.demo.modal.UserRegister;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private DoctorRepo dr_repo;
    @Autowired
    private TrainerRepo tr_repo;
    public String addUser(UserRegister userInfo) {
        UserRegister user = new UserRegister();
        user.setFirstname(userInfo.getFirstname());
        user.setPassword(userInfo.getPassword());
        user.setEmail(userInfo.getEmail());
        user.setAge(userInfo.getAge());
        user.setHeight(userInfo.getHeight());
        user.setWeight(userInfo.getWeight());

        repo.save(user);
        return "Registered";
    }
    public List<UserRegister> viewAllInfo() {
    	return repo.findAll();
    }
	public String checkUser(String email, String password) {
		
		UserRegister user = repo.findAllByEmail(email);
		if(user!=null && user.getPassword().equals(password)){
			return "Login successful";
		}
		
		return "Login failed";
	}
	

    public UserRegister getUserProfileByEmail(String email) {
        UserRegister user= repo.findAllByEmail(email);
        return user;
    }
	public String addDoctor(DoctorEntity drregisterInfo) {
		 DoctorEntity doctor = new DoctorEntity();
		 doctor.setName(drregisterInfo.getName());
		 doctor.setPassword(drregisterInfo.getPassword());
		 doctor.setEmail(drregisterInfo.getEmail());
		 doctor.setGender(drregisterInfo.getGender());
		 doctor.setSpecialization(drregisterInfo.getSpecialization());
		 dr_repo.save(doctor);
	        return "Registered";
		
	}
	public String addTrainer(TrainerEntity tr_registerInfo) {
		 TrainerEntity trainer = new TrainerEntity();
		 trainer.setName(tr_registerInfo.getName());
		 trainer.setPassword(tr_registerInfo.getPassword());
		 trainer.setEmail(tr_registerInfo.getEmail());
		 trainer.setGender(tr_registerInfo.getGender());
		 trainer.setSpecialization(tr_registerInfo.getSpecialization());
		 tr_repo.save(trainer);
	        return "Registered";
		
	}
	public DoctorEntity getDoctorProfileByEmail(String email) {
        DoctorEntity user= dr_repo.findAllByEmail(email);
        return user;
    }
//	public TrainerEntity getTrainerProfileByEmail(String email) {
//        TrainerEntity user= tr_repo.findAllByEmail(email);
//        return user;
//    }
	public TrainerEntity getTrainerProfileByEmail(String email) {
		  TrainerEntity user= tr_repo.findAllByEmail(email);
	        return user;
	}
	
}
