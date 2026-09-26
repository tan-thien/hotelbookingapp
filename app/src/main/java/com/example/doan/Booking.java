package com.example.doan;

public class Booking {
    private String room_name;
    private String start_date;
    private String end_date;
    private double room_price;


    public Booking() {
        // Cần có constructor mặc định rỗng khi làm việc với Firebase
    }

    public Booking(String room_name, String start_date, String end_date, double room_price) {

        this.room_name = room_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.room_price = room_price;
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public double getRoom_price() {
        return room_price;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setRoom_price(double room_price) {
        this.room_price = room_price;
    }
}

