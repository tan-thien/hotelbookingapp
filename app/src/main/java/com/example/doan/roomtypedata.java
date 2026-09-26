package com.example.doan;

public class roomtypedata {
    private String typeroomName;
    private String typeroomDesc;
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public roomtypedata(String typeroomName, String typeroomDesc) {
        this.typeroomName = typeroomName;
        this.typeroomDesc = typeroomDesc;
    }

    public String getTyperoomName() {
        return typeroomName;
    }

    public String getTyperoomDesc() {
        return typeroomDesc;
    }
    public roomtypedata(){}
}
