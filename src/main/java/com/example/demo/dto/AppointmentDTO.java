package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {
    private Integer id;
    private Integer userId;
    private Integer doctorId;
    private LocalDate date;
    private LocalTime time;

    public AppointmentDTO() {}

    public AppointmentDTO(Integer id, Integer userId, Integer doctorId, LocalDate date, LocalTime time) {
        this.id = id;
        this.userId = userId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
}
