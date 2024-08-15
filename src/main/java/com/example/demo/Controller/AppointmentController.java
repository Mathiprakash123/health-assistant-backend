package com.example.demo.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.AppointmentService;
import com.example.demo.dto.AppointmentDTO;
import com.example.demo.modal.Appointment;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/post")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            // Convert AppointmentDTO to Appointment entity
            Appointment appointment = new Appointment();
            appointment.setUserId(appointmentDTO.getUserId());
            appointment.setDoctorId(appointmentDTO.getDoctorId());
            
            // Convert date and time to string in the format expected by your Appointment entity
            String dateStr = appointmentDTO.getDate().toString();
            String timeStr = appointmentDTO.getTime().toString();
            
            appointment.setDate(dateStr);
            appointment.setTime(timeStr);

            appointmentService.save(appointment);
            return ResponseEntity.ok("Appointment created successfully");
        } catch (Exception e) {
            // Log the error and return a generic message
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the appointment.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable int id) {
        try {
            Appointment appointment = appointmentService.findById(id);
            if (appointment != null) {
                // Convert Appointment to AppointmentDTO
                AppointmentDTO appointmentDTO = new AppointmentDTO(
                    appointment.getId(),
                    appointment.getUserId(),
                    appointment.getDoctorId(),
                    LocalDate.parse(appointment.getDate()), // Convert string to LocalDate
                    LocalTime.parse(appointment.getTime())  // Convert string to LocalTime
                );
                return ResponseEntity.ok(appointmentDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/patients")
    public ResponseEntity<List<AppointmentDTO>> getPatientsByDoctorId(@RequestParam int doctorId) {
        try {
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
            if (appointments != null && !appointments.isEmpty()) {
                return ResponseEntity.ok(appointments);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
