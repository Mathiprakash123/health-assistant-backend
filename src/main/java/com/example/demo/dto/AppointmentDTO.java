package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {
    private int id;
    private int userId;
    private int doctorId;
    private LocalDate date;
    private LocalTime time;

    // Constructor with parameters
    public AppointmentDTO(int id, int userId, int doctorId, LocalDate date, LocalTime time) {
        this.id = id;
        this.userId = userId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
