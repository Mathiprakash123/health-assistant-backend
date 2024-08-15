package com.example.demo.Controller;

import com.example.demo.Repo.DoctorRepo;
import com.example.demo.Repo.TrainerRepo;
import com.example.demo.Service.UserService;
import com.example.demo.dto.UserDTO;
import com.example.demo.modal.DoctorEntity;
import com.example.demo.modal.TrainerEntity;
import com.example.demo.modal.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private DoctorRepo doctorRepository;
    
    @Autowired
    private TrainerRepo trainerRepo;
    @GetMapping("/viewall")
    public List<UserRegister> view() {
        return userService.viewAllInfo();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegister registerInfo) {
        return ResponseEntity.ok(userService.addUser(registerInfo));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserRegister loginInfo) {
        String result = userService.checkUser(loginInfo.getEmail(), loginInfo.getPassword());
        if ("Login successful".equals(result)) {
            return ResponseEntity.ok(new LoginResponse("Login successful", loginInfo.getEmail()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Login failed", null));
        }
    }

    @GetMapping("/profile")
    public UserRegister getUserProfile(@RequestParam String email) {
        return userService.getUserProfileByEmail(email);
    }

    @PutMapping("/update_user")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserRegister updateUser) {
        return ResponseEntity.ok(userService.updateUserInfo(updateUser));
    }

    // Doctor endpoints
    @PostMapping("/doctorregister")
    public ResponseEntity<String> doctorRegister(@RequestBody DoctorEntity drregisterInfo) {
        return ResponseEntity.ok(userService.addDoctor(drregisterInfo));
    }

    @PostMapping("/doctor_login")
    public ResponseEntity<LoginResponse> drLogin(@RequestBody DoctorEntity loginInfo) {
        String result = userService.checkDoctor(loginInfo.getEmail(), loginInfo.getPassword());
        if ("Login successful".equals(result)) {
            return ResponseEntity.ok(new LoginResponse("Login successful", loginInfo.getEmail()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Login failed", null));
        }
    }

    @GetMapping("/dr_profile")
    public DoctorEntity getDoctorProfile(@RequestParam String email) {
        return userService.getDoctorProfileByEmail(email);
    }

    @PutMapping("/update_doctor")
    public ResponseEntity<String> updateDoctor(@RequestBody DoctorEntity updatedDoctor) {
        return ResponseEntity.ok(userService.updateDoctor(updatedDoctor));
    }

   

    @GetMapping("/view_alldoctor")
    public List<DoctorEntity> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    
    
    
    @GetMapping("view_dr_byid/{id}")
    public Optional<DoctorEntity> getDoctor(@PathVariable Long id) {
        return doctorRepository.findById(id);
    }

    
    
    
//    
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserDTO> getUser(@PathVariable int userId) {
//        UserDTO userDTO = userService.getUserById(userId);
//        return ResponseEntity.ok(userDTO);
//    }
//    
    
    
    
    
    
    
    // Trainer endpoints
    @PostMapping("/trainer_register")
    public ResponseEntity<String> trainerRegister(@RequestBody TrainerEntity trRegisterInfo) {
        return ResponseEntity.ok(userService.addTrainer(trRegisterInfo));
    }

    @PostMapping("/trainer_login")
    public ResponseEntity<LoginResponse> trLogin(@RequestBody TrainerEntity loginInfo) {
        try {
            String result = userService.checkTrainer(loginInfo.getEmail(), loginInfo.getPassword());
            if ("Login successful".equals(result)) {
                return ResponseEntity.ok(new LoginResponse("Login successful", loginInfo.getEmail()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Login failed", null));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponse("An error occurred", null));
        }
    }

    @GetMapping("/trainer_profile")
    public TrainerEntity getTrainerProfile(@RequestParam String email) {
        return userService.getTrainerProfileByEmail(email);
    }

    @PutMapping("/update_trainer")
    public ResponseEntity<String> updateTrainer(@RequestBody TrainerEntity updatedTrainer) {
        return ResponseEntity.ok(userService.updateTrainer(updatedTrainer));
    }
//    @GetMapping("/view_alldoctor")
//    public List<TrainerEntity> getAllTrainers() {
//        return trainerRepo.findAll();
//    }
    // Response class
    public static class LoginResponse {
        private String message;
        private String email;

        public LoginResponse(String message, String email) {
            this.message = message;
            this.email = email;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
