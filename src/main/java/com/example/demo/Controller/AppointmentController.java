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

    private static final String APPOINTMENT_CREATED = "Appointment created successfully";
    private static final String APPOINTMENT_ACCEPTED = "Appointment accepted successfully";
    private static final String APPOINTMENT_REJECTED = "Appointment rejected successfully";
    private static final String ERROR_MESSAGE = "An error occurred while processing the request.";

    @PostMapping("/post")
    public ResponseEntity<ResponseMessage> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            Appointment appointment = convertToEntity(appointmentDTO);
            appointmentService.save(appointment);
            return buildSuccessResponse(APPOINTMENT_CREATED);
        } catch (Exception e) {
            return handleErrorResponse(e, ERROR_MESSAGE);  // Handles error response for ResponseMessage
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable int id) {
        try {
            Appointment appointment = appointmentService.findById(id);
            return appointment != null ? ResponseEntity.ok(convertToDTO(appointment))
                                       : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return handleErrorResponse(e);  // Handles error response for null return
        }
    }

    @GetMapping("/patients")
    public ResponseEntity<List<AppointmentDTO>> getPatientsByDoctorId(@RequestParam int doctorId) {
        try {
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
            return appointments != null && !appointments.isEmpty() ? ResponseEntity.ok(appointments)
                                                                   : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return handleErrorResponse(e);  // Handles error response for null return
        }
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUserIdAndDoctorId(
            @RequestParam int userId, @RequestParam int doctorId) {
        try {
            List<Appointment> appointments = appointmentService.findAppointmentsByUserIdAndDoctorId(userId, doctorId);
            return appointments != null && !appointments.isEmpty()
                    ? ResponseEntity.ok(appointments.stream().map(this::convertToDTO).collect(Collectors.toList()))
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return handleErrorResponse(e);  // Handles error response for null return
        }
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<ResponseMessage> acceptAppointment(@PathVariable Integer id) {
        return updateAppointmentStatus(id, "ACCEPTED", APPOINTMENT_ACCEPTED);
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<ResponseMessage> rejectAppointment(@PathVariable Integer id) {
        return updateAppointmentStatus(id, "REJECTED", APPOINTMENT_REJECTED);
    }

    @GetMapping("/status/{appointmentId}")
    public ResponseEntity<Appointment> getStatus(@PathVariable int appointmentId) {
        try {
            Appointment appointment = appointmentService.getAppointmentStatus(appointmentId);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return handleErrorResponse(e);  // Handles error response for null return
        }
    }

    @GetMapping("/appointments/user/{userId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUserId(@PathVariable int userId) {
        try {
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsByUserId(userId);
            return appointments != null && !appointments.isEmpty() ? ResponseEntity.ok(appointments)
                                                                   : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return handleErrorResponse(e);  // Handles error response for null return
        }
    }

    // Helper method to update the appointment status and handle response
    private ResponseEntity<ResponseMessage> updateAppointmentStatus(Integer id, String status, String successMessage) {
        try {
            appointmentService.updateAppointmentStatus(id, status);
            return buildSuccessResponse(successMessage);
        } catch (Exception e) {
            return handleErrorResponse(e, ERROR_MESSAGE);  // Handles error response for ResponseMessage
        }
    }

    // Helper to convert DTO to Entity
    private Appointment convertToEntity(AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setUserId(dto.getUserId());
        appointment.setDoctorId(dto.getDoctorId());
        appointment.setDate(dto.getDate().toString());
        appointment.setTime(dto.getTime().toString());
        appointment.setStatus(dto.getStatus());
        return appointment;
    }

    // Helper to convert Entity to DTO
    private AppointmentDTO convertToDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getUserId(),
                appointment.getDoctorId(),
                LocalDate.parse(appointment.getDate()),
                LocalTime.parse(appointment.getTime()),
                appointment.getStatus());
    }

    // Helper for success response
    private ResponseEntity<ResponseMessage> buildSuccessResponse(String message) {
        return ResponseEntity.ok(new ResponseMessage(message));
    }

    // Helper for error handling response when a ResponseMessage is needed
    private ResponseEntity<ResponseMessage> handleErrorResponse(Exception e, String errorMessage) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseMessage(errorMessage));
    }

    // Generic helper for error handling response with other types or null
    private <T> ResponseEntity<T> handleErrorResponse(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
    }

    // Static class for response message structure
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
}
