package com.example.demo.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repo.AppointmentRepository;
import com.example.demo.Repo.UserRepository;
import com.example.demo.dto.AppointmentDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.modal.Appointment;
import com.example.demo.modal.UserRegister;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public List<UserDTO> getPatientsByDoctorId(int doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        List<Integer> userIds = appointments.stream()
                                            .map(Appointment::getUserId)
                                            .distinct()
                                            .collect(Collectors.toList());
        return userIds.stream()
                      .map(this::getUserDetails)
                      .filter(userDTO -> userDTO != null)
                      .collect(Collectors.toList());
    }

    public Appointment findById(Integer id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public List<AppointmentDTO> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        return appointments.stream()
                           .map(this::convertToDTO)
                           .collect(Collectors.toList());
    }

    // private AppointmentDTO convertToDTO(Appointment appointment) {
    //     LocalDate date = parseDate(appointment.getDate());
    //     LocalTime time = parseTime(appointment.getTime());
    //     return new AppointmentDTO(appointment.getId(),
    //                               appointment.getUserId(),
    //                               appointment.getDoctorId(),
    //                               date,
    //                               time);
    // }

    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LocalTime parseTime(String timeString) {
        try {
            return LocalTime.parse(timeString, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserDTO getUserDetails(int userId) {
        Optional<UserRegister> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserRegister user = optionalUser.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getFirstname());
            return userDTO;
        } else {
            return null;
        }
    }
    public void updateAppointmentStatus(Integer id, String status) {
        Appointment appointment = findById(id);
        if (appointment != null) {
            appointment.setStatus(status);
            save(appointment);
        }
    }



    public Appointment getAppointmentStatus(int appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

    public List<Appointment> findAppointmentsByUserIdAndDoctorId(int userId, int doctorId) {
        return appointmentRepository.findByUserIdAndDoctorId(userId, doctorId);
    }


    public List<AppointmentDTO> getAppointmentsByUserId(int userId) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);
        return appointments.stream()
                           .map(this::convertToDTO)
                           .collect(Collectors.toList());
    }


    // @Autowired
    // private AppointmentRepo appointmentRepo;

    private AppointmentDTO convertToDTO(Appointment appointment) {
        LocalDate date = parseDate(appointment.getDate());
        LocalTime time = parseTime(appointment.getTime());
        return new AppointmentDTO(
            appointment.getId(),
            appointment.getUserId(),
            appointment.getDoctorId(),
            date,
            time,
            appointment.getStatus() // Ensure status ais included in the DTO
        );
    }

}
