package com.example.llcadmin;

public class AddStaff_Class {
    public String stream;

    public AddStaff_Class(String stream) {
        this.stream = stream;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return stream;
    }
}
