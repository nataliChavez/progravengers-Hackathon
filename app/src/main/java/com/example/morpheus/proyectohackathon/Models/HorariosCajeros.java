package com.example.morpheus.proyectohackathon.Models;

public class HorariosCajeros  {
    String dayName;
    String openingTime;
    String closingTime;

    public HorariosCajeros(String dayName, String openingTime, String closingTime) {
        this.dayName = dayName;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }
}
