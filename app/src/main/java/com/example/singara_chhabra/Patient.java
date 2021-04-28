package com.example.singara_chhabra;

import java.util.Date;

public class Patient {

    String id;
    String name;
    float diastolic;
    float systolic;
    String type;
    String date;

    public Patient() {}

    public Patient(String id, String name, float diastolic,
                   float systolic, String type, String date) {
        this.id = id;
        this.name = name;
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.type = type;
        this.date = date;
    }

    public String getPatientId() { return id; }

    public void setPatientId(String x) {
        this.id = x;
    }

    public String getName() { return name; }

    public void setName(String x) {
        this.name = x;
    }

    public float getDiastolic() {return diastolic;}

    public void setDiastolic(float x) {
        this.diastolic = x;
    }

    public float getSystolic() {return systolic;}

    public void setSystolic(float x) {
        this.systolic = x;
    }

    public String getType() {return type;}

    public void setType(String x) {
        this.type = x;
    }

    public String getDate() {return date;}

    public void setDate(String x) {
        this.date= x;
    }
    }




