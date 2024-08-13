package com.example.demo.Service;

import com.example.demo.Repo.DoctorRepo;
import com.example.demo.Repo.TrainerRepo;
import com.example.demo.Repo.UserRepository;
import com.example.demo.modal.DoctorEntity;
import com.example.demo.modal.TrainerEntity;
import com.example.demo.modal.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (user != null && user.getPassword().equals(password)) {
            return "Login successful";
        }
        return "Login failed";
    }

    public UserRegister getUserProfileByEmail(String email) {
        return repo.findAllByEmail(email);
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

    public String updateUserInfo(UserRegister updateUser) {
        if (repo.existsById(updateUser.getId())) {
            repo.save(updateUser);
            return "User updated successfully";
        }
        return "User not found";
    }

    public String updateDoctor(DoctorEntity updatedDoctor) {
        if (dr_repo.existsById(updatedDoctor.getId())) {
            dr_repo.save(updatedDoctor);
            return "Doctor updated successfully";
        }
        return "Doctor not found";
    }

    public String updateTrainer(TrainerEntity updatedTrainer) {
        if (tr_repo.existsById(updatedTrainer.getId())) {
            tr_repo.save(updatedTrainer);
            return "Trainer updated successfully";
        }
        return "Trainer not found";
    }

    public String addTrainer(TrainerEntity tr_registerInfo) {
        TrainerEntity trainer = new TrainerEntity();
        trainer.setName(tr_registerInfo.getName());
        trainer.setPassword(tr_registerInfo.getPassword());
        trainer.setEmail(tr_registerInfo.getEmail());
        trainer.setGender(tr_registerInfo.getGender());
        tr_repo.save(trainer);
        return "Registered";
    }

    public DoctorEntity getDoctorProfileByEmail(String email) {
        return dr_repo.findAllByEmail(email);
    }

    public TrainerEntity getTrainerProfileByEmail(String email) {
        return tr_repo.findTrainersByEmail(email);
    }


    public String checkDoctor(String email, String password) {
        DoctorEntity user = dr_repo.findAllByEmail(email); // Ensure this method name is correct
        if (user != null && user.getPassword().equals(password)) {
            return "Login successful";
        }
        return "Login failed";
    }

    public String checkTrainer(String email, String password) {
        try {
            TrainerEntity user = tr_repo.findTrainersByEmail(email); // Make sure this method exists
            if (user != null && user.getPassword().equals(password)) {
                return "Login successful";
            }
            return "Login failed";
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return "An error occurred";
        }
    }


}
