package org.example.entites;

public class Stock {
    private String id;
    private String name;
    private String location;
    private int locationCode;

    public Stock(String id, String name, String location, int locationCode) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.locationCode = locationCode;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(int locationCode) {
        this.locationCode = locationCode;
    }
}
