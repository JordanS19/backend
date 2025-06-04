package com.autotec.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.autotec.backend.model.Appointment;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Service
public class AppointmentService {
    private final DynamoDbTable<Appointment> appointmentTable;

    public AppointmentService(DynamoDbTable<Appointment> appointmentTable) {
        this.appointmentTable = appointmentTable;
    }

    public Appointment createAppointment(Appointment appointment) {
        appointment.setId(UUID.randomUUID().toString());
        appointmentTable.putItem(appointment);
        return appointment;
    }

    public Optional<Appointment> getAppointmentById(String id) {
        return Optional.ofNullable(appointmentTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    public List<Appointment> getAppointmentsByUser(String userId) {
        return appointmentTable.scan().items().stream().filter(p -> p.getUserId().contains(userId)).toList();
    }

    public List<Appointment> getAllAppointments() {
        return appointmentTable.scan().items().stream().toList();
    }

    public Appointment updateAppointment(String id, String userId, String serviceId, String date, String time, String status) {
        Appointment existing = appointmentTable.getItem(r -> r.key(k -> k.partitionValue(id)));
        if (existing == null) return null;

        if (userId != null) existing.setUserId(userId);
        if (serviceId != null) existing.setServiceId(serviceId);
        if (date != null) existing.setDate(date);
        if (time != null) existing.setTime(time);
        if (status != null) existing.setStatus(status);

        appointmentTable.putItem(existing);
        return existing;
    }

    public boolean deleteAppointment(String id) {
        Appointment appointment = appointmentTable.getItem(r -> r.key(k -> k.partitionValue(id)));

        if(appointment != null){
            appointmentTable.deleteItem(appointment);
            return true;
        }
        return false;
    }
}
