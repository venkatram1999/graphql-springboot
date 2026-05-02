package com.learngraphql.springgraphql.hospitalproject.controller;

import com.learngraphql.springgraphql.hospitalproject.models.*;
import com.learngraphql.springgraphql.hospitalproject.service.HospitalService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class HealthcareController {

    private final HospitalService hospitalService;

    public HealthcareController(HospitalService healthcareService) {
        this.hospitalService = healthcareService;
    }

    @QueryMapping
    public Patient patientById(@Argument String id) {
        return hospitalService.getPatientById(id);
    }

    @QueryMapping
    public Prescription prescriptionById(@Argument String id) {
        return hospitalService.getPrescriptionById(id);
    }

    @SchemaMapping(typeName = "Patient", field = "appointments")
    public List<Appointment> appointments(Patient patient) {
        return hospitalService.getAppointmentsByPatientId(patient.getId());
    }

    @SchemaMapping(typeName = "Appointment", field = "doctor")
    public Doctor doctor(Appointment appointment) {
        return hospitalService.getDoctorById(appointment.getDoctorId());
    }

    @SchemaMapping(typeName = "Prescription", field = "patient")
    public Patient patient(Prescription prescription) {
        return hospitalService.getPatientFromPrescription(prescription);
    }

    @SchemaMapping(typeName = "Prescription", field = "doctor")
    public Doctor prescriptionDoctor(Prescription prescription) {
        return hospitalService.getDoctorFromPrescription(prescription);
    }

    @SchemaMapping(typeName = "Prescription", field = "medicines")
    public List<Medicine> medicines(Prescription prescription) {
        return hospitalService.getMedicinesByPrescriptionId(prescription.getId());
    }
}