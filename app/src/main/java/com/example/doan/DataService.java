package com.example.doan;

public class DataService {
    private String nameSer;
    private String descSer;
    private Double priceSer;
    private String imageSer;

    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public DataService(String nameSer, String descSer, Double priceSer, String imageSer) {
        this.nameSer = nameSer;
        this.descSer = descSer;
        this.priceSer = priceSer;
        this.imageSer = imageSer;
    }


    public String getNameSer() {
        return nameSer;
    }

    public String getDescSer() {
        return descSer;
    }

    public Double getPriceSer() {
        return priceSer;
    }

    public String getImageSer() {
        return imageSer;
    }


    public DataService(){

    }
}
