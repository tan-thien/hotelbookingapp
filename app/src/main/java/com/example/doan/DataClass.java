package com.example.doan;

public class DataClass {
    private String roomName;
    private String roomDesc;
    private Double roomPrice;
    private String roomTyperoom;
    private String roomImage;

    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public DataClass(String roomName, String roomDesc, Double roomPrice, String roomTyperoom, String roomImage) {
        this.roomName = roomName;
        this.roomDesc = roomDesc;
        this.roomPrice = roomPrice;
        this.roomTyperoom = roomTyperoom;
        this.roomImage = roomImage;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public String getRoomTyperoom() {
        return roomTyperoom;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public  DataClass(){

    }


}

