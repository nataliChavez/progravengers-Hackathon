package com.example.morpheus.proyectohackathon.Models;

public class LocalizacionBBVA {
    String name;
    String address;
    String postcode;
    double latitud;
    double longitud;
    String movimimientos;
    HorariosCajeros horariosCajeros;

    public LocalizacionBBVA() {
    }

    public LocalizacionBBVA(String name, String address, String postcode, double latitud, double longitud) {
        this.name = name;
        this.address = address;
        this.postcode = postcode;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public LocalizacionBBVA(String name, String address, String postcode, String movimimientos, HorariosCajeros horariosCajeros, double latitud, double longitud) {
        this.name = name;
        this.address = address;
        this.postcode = postcode;
        this.movimimientos = movimimientos;
        this.horariosCajeros = horariosCajeros;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getMovimimientos() {
        return movimimientos;
    }

    public void setMovimimientos(String movimimientos) {
        this.movimimientos = movimimientos;
    }

    public HorariosCajeros getHorariosCajeros() {
        return horariosCajeros;
    }

    public void setHorariosCajeros(HorariosCajeros horariosCajeros) {
        this.horariosCajeros = horariosCajeros;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
