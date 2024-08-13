package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.UserService;
import com.example.demo.modal.DoctorEntity;
import com.example.demo.modal.TrainerEntity;
import com.example.demo.modal.UserRegister;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class UserController {

    @Autowired
    private UserService userservice;

    @GetMapping("/viewall")
    public List<UserRegister> view() {
        return userservice.viewAllInfo();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegister registerInfo) {
        return ResponseEntity.ok(userservice.addUser(registerInfo));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserRegister loginInfo) {
        String result = userservice.checkUser(loginInfo.getEmail(), loginInfo.getPassword());
        if ("Login successful".equals(result)) {
            return ResponseEntity.ok(new LoginResponse("Login successful", loginInfo.getEmail()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Login failed", null));
        }
    }

    @GetMapping("/profile")
    public UserRegister getUserProfile(@RequestParam String email) {
        return userservice.getUserProfileByEmail(email);
    }

    @PutMapping("/update_user")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserRegister updateUser) {
        return ResponseEntity.ok(userservice.updateUserInfo(updateUser));
    }

    // Doctor endpoints
    @PostMapping("/doctorregister")
    public ResponseEntity<String> doctorRegister(@RequestBody DoctorEntity drregisterInfo) {
        return ResponseEntity.ok(userservice.addDoctor(drregisterInfo));
    }
    

    
    @PostMapping("/doctor_login")
    public ResponseEntity<LoginResponse> dr_login(@RequestBody DoctorEntity loginInfo) {
        String result = userservice.checkDoctor(loginInfo.getEmail(), loginInfo.getPassword());
        if ("Login successful".equals(result)) {
            return ResponseEntity.ok(new LoginResponse("Login successful", loginInfo.getEmail()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Login failed", null));
        }
    }



    @GetMapping("/dr_profile")
    public DoctorEntity getDoctorProfile(@RequestParam String email) {
        return userservice.getDoctorProfileByEmail(email);
    }

    @PutMapping("/update_doctor")
    public ResponseEntity<String> updateDoctor(@RequestBody DoctorEntity updatedDoctor) {
        return ResponseEntity.ok(userservice.updateDoctor(updatedDoctor));
    }

    // Trainer endpoints
    @PostMapping("/trainer_register")
    public ResponseEntity<String> trainerRegister(@RequestBody TrainerEntity tr_registerInfo) {
        return ResponseEntity.ok(userservice.addTrainer(tr_registerInfo));
    }

    @GetMapping("/trainer_profile")
    public TrainerEntity getTrainerProfile(@RequestParam String email) {
        return userservice.getTrainerProfileByEmail(email);
    }

    @PutMapping("/update_trainer")
    public ResponseEntity<String> updateTrainer(@RequestBody TrainerEntity updatedTrainer) {
        return ResponseEntity.ok(userservice.updateTrainer(updatedTrainer));
    }
    
    @PostMapping("/trainer_login")
    public ResponseEntity<LoginResponse> tr_login(@RequestBody TrainerEntity loginInfo) {
        try {
            String result = userservice.checkTrainer(loginInfo.getEmail(), loginInfo.getPassword());
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
