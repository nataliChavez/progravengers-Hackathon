package com.example.morpheus.proyectohackathon.Models;

public class LocalizacionBBVA {
    String name;
    String address;
    String postcode;
    String latitud;
    String longitud;

    public LocalizacionBBVA() {
    }

    public LocalizacionBBVA(String name, String address, String postcode, String latitud, String longitud) {
        this.name = name;
        this.address = address;
        this.postcode = postcode;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
