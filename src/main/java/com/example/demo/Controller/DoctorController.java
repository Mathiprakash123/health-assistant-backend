//package com.example.demo.Controller;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.Service.AppointmentService;
//import com.example.demo.Service.UserService;
//import com.example.demo.dto.UserDTO;
//import com.example.demo.modal.Appointment;
//import com.example.demo.modal.UserRegister;
//
//@RestController
//@RequestMapping("/api/doctors")
//public class DoctorController {
//
//    @Autowired
//    private AppointmentService appointmentService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/{doctorId}/patients")
//    public ResponseEntity<List<UserDTO>> getPatientsForDoctor(@PathVariable Long doctorId) {
//        // Fetch appointments for the given doctor ID
//        List<Appointment> appointments = appointmentService.findAppointmentsByDoctorId(doctorId);
//
//        // Extract user IDs from appointments
//        List<UserRegister> users = appointments.stream()
//                                              .map(Appointment::getUser)
//                                              .distinct()  // Ensure no duplicate users
//                                              .collect(Collectors.toList());
//
//        // Check if users list is not empty
//        if (users.isEmpty()) {
//            return ResponseEntity.ok(List.of());  // Return an empty list if no users are found
//        }
//
//        // Convert UserRegister entities to UserDTOs
//        List<UserDTO> patients = users.stream().map(userService::convertToDTO).collect(Collectors.toList());
//        return ResponseEntity.ok(patients);
//    }
//}
