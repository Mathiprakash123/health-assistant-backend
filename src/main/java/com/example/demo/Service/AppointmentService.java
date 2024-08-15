package com.example.demo.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public List<UserDTO> getPatientsByDoctorId(int doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

        List<Integer> userIds = appointments.stream()
                                        .map(Appointment::getUserId)
                                        .distinct()
                                        .collect(Collectors.toList());

        List<UserDTO> userDTOs = new ArrayList<>();

        for (int userId : userIds) {
            Optional<UserRegister> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                UserRegister user = optionalUser.get();
                UserDTO dto = new UserDTO();
                dto.setId(user.getId());
                dto.setName(user.getFirstname());
                userDTOs.add(dto);
            }
        }

        return userDTOs;
    }

    public Appointment findById(Integer id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public List<AppointmentDTO> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        if (appointments == null) {
            appointments = new ArrayList<>();
        }
        return appointments.stream()
                           .map(this::convertToDTO)
                           .collect(Collectors.toList());
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {
        LocalDate date = parseDate(appointment.getDate());
        LocalTime time = parseTime(appointment.getTime());

        return new AppointmentDTO(appointment.getId(), 
                                  appointment.getUserId(), 
                                  appointment.getDoctorId(), 
                                  date, 
                                  time);
    }

    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            e.printStackTrace(); // Log the exception
            return null; // Or handle it as needed
        }
    }

    private LocalTime parseTime(String timeString) {
        try {
            return LocalTime.parse(timeString, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            e.printStackTrace(); // Log the exception
            return null; // Or handle it as needed
        }
    }
}
