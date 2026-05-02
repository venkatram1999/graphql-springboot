package com.learngraphql.springgraphql.hospitalproject.models;

public class Medicine {
    String id;
    String name;
    String dosage;
    String prescriptionId;

    public Medicine(String id, String name, String dosage, String prescriptionId) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.prescriptionId = prescriptionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
