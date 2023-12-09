package ca.arctechlabs.aoc.y2023.models;

public class SeedInfo {
    private final Long id;
    private Long soil;
    private Long fertilizer;
    private Long water;
    private Long light;
    private Long temperature;
    private Long humidity;
    private Long location;

    public SeedInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getSoil() {
        return soil;
    }

    public void setSoil(Long soil) {
        this.soil = soil;
    }

    public Long getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(Long fertilizer) {
        this.fertilizer = fertilizer;
    }

    public Long getWater() {
        return water;
    }

    public void setWater(Long water) {
        this.water = water;
    }

    public Long getLight() {
        return light;
    }

    public void setLight(Long light) {
        this.light = light;
    }

    public Long getTemperature() {
        return temperature;
    }

    public void setTemperature(Long temperature) {
        this.temperature = temperature;
    }

    public Long getHumidity() {
        return humidity;
    }

    public void setHumidity(Long humidity) {
        this.humidity = humidity;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }
}
