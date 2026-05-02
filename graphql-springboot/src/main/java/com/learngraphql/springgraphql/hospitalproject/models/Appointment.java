package com.learngraphql.springgraphql.hospitalproject.models;

public class Appointment {
    String id;
    String visitDate;
    String reason;
    String patientId;
    String doctorId;

    public Appointment(String id, String visitDate, String reason, String patientId, String doctorId) {
        this.id = id;
        this.visitDate = visitDate;
        this.reason = reason;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
