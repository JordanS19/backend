package com.autotec.backend.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.autotec.backend.model.Appointment;
import com.autotec.backend.service.AppointmentService;

@Controller
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @MutationMapping
    public Appointment createAppointment(
        @Argument String userId,
        @Argument String serviceId,
        @Argument String date,
        @Argument String time,
        @Argument String status
    ) {
        Appointment appointment = new Appointment();
        appointment.setUserId(userId);
        appointment.setServiceId(serviceId);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setStatus(status);

        return appointmentService.createAppointment(appointment);
    }

    @QueryMapping
    public Appointment getAppointmentById(@Argument String id){
        return appointmentService.getAppointmentById(id).orElse(null);
    }

    @QueryMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @QueryMapping
    public List<Appointment> getAppointmentsByUser(@Argument String userId) {
        return appointmentService.getAppointmentsByUser(userId);
    }

    @MutationMapping
    public Appointment updateAppointment(
        @Argument String id,
        @Argument String userId,
        @Argument String serviceId,
        @Argument String date,
        @Argument String time,
        @Argument String status
    ) {
        return appointmentService.updateAppointment(id, userId, serviceId, date, time, status);
    }

    @MutationMapping
    public Boolean deleteAppointment(@Argument String id) {
        return appointmentService.deleteAppointment(id);
    }
}
