package com.learngraphql.springgraphql.hospitalproject.service;

import com.learngraphql.springgraphql.hospitalproject.models.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HospitalService {

    private final Map<String, Patient> patients = Map.of(
            "P1", new Patient("P1", "Arun Kumar", 34),
            "P2", new Patient("P2", "Meena Ravi", 29)
    );

    private final Map<String, Doctor> doctors = Map.of(
            "D1", new Doctor("D1", "Dr. Priya Sharma", "Cardiology"),
            "D2", new Doctor("D2", "Dr. Karthik Raman", "Dermatology")
    );

    private final Map<String, Prescription> prescriptions = Map.of(
            "PR1", new Prescription("PR1", "2026-04-20", "P1", "D1"),
            "PR2", new Prescription("PR2", "2026-04-25", "P2", "D2")
    );

    private final List<Appointment> appointments = List.of(
            new Appointment("A1", "2026-04-10", "Chest Pain", "P1", "D1"),
            new Appointment("A2", "2026-04-18", "Follow-up", "P1", "D1"),
            new Appointment("A3", "2026-04-22", "Skin Allergy", "P2", "D2")
    );

    private final List<Medicine> medicines = List.of(
            new Medicine("M1", "Aspirin", "75mg once daily", "PR1"),
            new Medicine("M2", "Atorvastatin", "10mg at night", "PR1"),
            new Medicine("M3", "Cetirizine", "10mg after food", "PR2")
    );

    public Patient getPatientById(String id) {
        return patients.get(id);
    }

    public Prescription getPrescriptionById(String id) {
        return prescriptions.get(id);
    }

    public List<Appointment> getAppointmentsByPatientId(String patientId) {
        return appointments.stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .toList();
    }

    public Doctor getDoctorById(String doctorId) {
        return doctors.get(doctorId);
    }

    public Patient getPatientFromPrescription(Prescription prescription) {
        return patients.get(prescription.getPatientId());
    }

    public Doctor getDoctorFromPrescription(Prescription prescription) {
        return doctors.get(prescription.getDoctorId());
    }

    public List<Medicine> getMedicinesByPrescriptionId(String prescriptionId) {
        return medicines.stream()
                .filter(m -> m.getPrescriptionId().equals(prescriptionId))
                .toList();
    }
}