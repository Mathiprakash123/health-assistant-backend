package com.example.demo.Service;

import com.example.demo.Repo.DoctorRepo;
import com.example.demo.Repo.TrainerRepo;
import com.example.demo.Repo.UserRepository;
// import com.example.demo.dto.UserDTO;
import com.example.demo.modal.DoctorEntity;
import com.example.demo.modal.TrainerEntity;
import com.example.demo.modal.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private TrainerRepo trainerRepo;

    public String addUser(UserRegister userInfo) {
        UserRegister user = new UserRegister();
        user.setFirstname(userInfo.getFirstname());
        user.setPassword(userInfo.getPassword());
        user.setEmail(userInfo.getEmail());
        user.setAge(userInfo.getAge());
        user.setHeight(userInfo.getHeight());
        user.setWeight(userInfo.getWeight());

        userRepository.save(user);
        return "Registered";
    }

    public List<UserRegister> viewAllInfo() {
        return userRepository.findAll();
    }

    public String checkUser(String email, String password) {
        UserRegister user = userRepository.findAllByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return "Login successful";
        }
        return "Login failed";
    }

    public UserRegister getUserProfileByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    public String addDoctor(DoctorEntity drregisterInfo) {
        DoctorEntity doctor = new DoctorEntity();
        doctor.setName(drregisterInfo.getName());
        doctor.setPassword(drregisterInfo.getPassword());
        doctor.setEmail(drregisterInfo.getEmail());
        doctor.setGender(drregisterInfo.getGender());
        doctor.setSpecialization(drregisterInfo.getSpecialization());
        doctorRepo.save(doctor);
        return "Registered";
    }

    public String updateUserInfo(UserRegister updateUser) {
        if (userRepository.existsById(updateUser.getId())) {
            userRepository.save(updateUser);
            return "User updated successfully";
        }
        return "User not found";
    }

    public String updateDoctor(DoctorEntity updatedDoctor) {
        if (doctorRepo.existsById(updatedDoctor.getId())) {
            doctorRepo.save(updatedDoctor);
            return "Doctor updated successfully";
        }
        return "Doctor not found";
    }

    public String updateTrainer(TrainerEntity updatedTrainer) {
        if (trainerRepo.existsById(updatedTrainer.getId())) {
            trainerRepo.save(updatedTrainer);
            return "Trainer updated successfully";
        }
        return "Trainer not found";
    }

    public String addTrainer(TrainerEntity trRegisterInfo) {
        TrainerEntity trainer = new TrainerEntity();
        trainer.setName(trRegisterInfo.getName());
        trainer.setPassword(trRegisterInfo.getPassword());
        trainer.setEmail(trRegisterInfo.getEmail());
        trainer.setGender(trRegisterInfo.getGender());
        trainerRepo.save(trainer);
        return "Registered";
    }

    public DoctorEntity getDoctorProfileByEmail(String email) {
        return doctorRepo.findAllByEmail(email);
    }

    public TrainerEntity getTrainerProfileByEmail(String email) {
        return trainerRepo.findTrainersByEmail(email);
    }

    public DoctorEntity checkDoctor(String email, String password) {
        DoctorEntity doctor = doctorRepo.findByEmailAndPassword(email, password);
        if (doctor != null && doctor.getPassword().equals(password)) {
            return doctor;
        }
        return null; // Return null if login fails
    }

    public String checkTrainer(String email, String password) {
        try {
            TrainerEntity trainer = trainerRepo.findTrainersByEmail(email);
            if (trainer != null && trainer.getPassword().equals(password)) {
                return "Login successful";
            }
            return "Login failed";
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return "An error occurred";
        }
    }

    public UserRegister findById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    
    
    
    
    
    
    
    
    
//    
//    public UserDTO convertToDTO(UserRegister user) {
//        UserDTO dto = new UserDTO();
//        dto.setId(user.getId());
//        dto.setFirstname(user.getFirstname());
//        dto.setEmail(user.getEmail());
//        dto.setAge(user.getAge());
//        dto.setHeight(user.getHeight());
//        dto.setWeight(user.getWeight());
//        return dto;
//    }
//
//
//    public UserDTO getUserById(int userId) {
//        UserRegister user = userRepository.findById(userId)
//            .orElseThrow(() -> new RuntimeException("User not found"));
//        return new UserDTO(user);
//    }
//
//    public List<UserDTO> findUsersByIds(List<Long> userIds) {
//        List<UserRegister> users = userRepository.findAllById(userIds);
//        return users.stream().map(UserDTO::new).collect(Collectors.toList());
//    }
    
    
    public Optional<UserRegister> getUserById(int userId) {
        return userRepository.findById(userId);
    }

    public DoctorEntity authenticateDoctor(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authenticateDoctor'");
    }
    
    
    
    
    
    
    
    
    
    
    
}
