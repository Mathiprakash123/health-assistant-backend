package com.example.demo.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
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
    public ResponseEntity<ResponseMessage> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            Appointment appointment = new Appointment();
            appointment.setUserId(appointmentDTO.getUserId());
            appointment.setDoctorId(appointmentDTO.getDoctorId());
            appointment.setDate(appointmentDTO.getDate().toString());
            appointment.setTime(appointmentDTO.getTime().toString());

            appointmentService.save(appointment);

            // Return a JSON response
            return ResponseEntity.ok(new ResponseMessage("Appointment created successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            // Return a JSON error response
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseMessage("An error occurred while creating the appointment."));
        }
    }

    // Define a simple response class to return JSON responses
    public static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable int id) {
        try {
            Appointment appointment = appointmentService.findById(id);
            if (appointment != null) {
                AppointmentDTO appointmentDTO = new AppointmentDTO(
                    appointment.getId(),
                    appointment.getUserId(),
                    appointment.getDoctorId(),
                    LocalDate.parse(appointment.getDate()),
                    LocalTime.parse(appointment.getTime()),
                    appointment.getStatus()
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

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUserIdAndDoctorId(
            @RequestParam int userId, @RequestParam int doctorId) {
        try {
            List<Appointment> appointments = appointmentService.findAppointmentsByUserIdAndDoctorId(userId, doctorId);
            if (appointments != null && !appointments.isEmpty()) {
                List<AppointmentDTO> appointmentDTOs = appointments.stream()
                    .map(a -> new AppointmentDTO(
                        a.getId(),
                        a.getUserId(),
                        a.getDoctorId(),
                        LocalDate.parse(a.getDate()),
                        LocalTime.parse(a.getTime()),
                        a.getStatus()
                    ))
                    .collect(Collectors.toList());
                return ResponseEntity.ok(appointmentDTOs);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<ResponseMessage> acceptAppointment(@PathVariable Integer id) {
        try {
            appointmentService.updateAppointmentStatus(id, "ACCEPTED");
            return ResponseEntity.ok(new ResponseMessage("Appointment accepted successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseMessage("An error occurred while accepting the appointment."));
        }
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<ResponseMessage> rejectAppointment(@PathVariable Integer id) {
        try {
            appointmentService.updateAppointmentStatus(id, "REJECTED");
            return ResponseEntity.ok(new ResponseMessage("Appointment rejected successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseMessage("An error occurred while rejecting the appointment."));
        }
    }

    @GetMapping("/status/{appointmentId}")
    public Appointment getStatus(@PathVariable int appointmentId) {
        return appointmentService.getAppointmentStatus(appointmentId);
    }

    @GetMapping("/appointments/user/{userId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUserId(@PathVariable int userId) {
        try {
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsByUserId(userId);
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
