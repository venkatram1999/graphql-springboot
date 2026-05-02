package com.learngraphql.springgraphql.hospitalproject.models;

public class Prescription {
    String id;
    String issuedDate;
    String patientId;
    String doctorId;

    public Prescription(String id, String issuedDate, String patientId, String doctorId) {
        this.id = id;
        this.issuedDate = issuedDate;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
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
