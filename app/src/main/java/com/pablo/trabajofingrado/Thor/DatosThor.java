package com.pablo.trabajofingrado.Thor;

public class DatosThor {
    String nombreThor;
    String descripcionThor;
    int logoThor;


    public DatosThor(String nombreThor, String descripcionThor, int logoThor) {
        this.nombreThor = nombreThor;
        this.descripcionThor = descripcionThor;
        this.logoThor = logoThor;
    }

    public String getNombreThor() {
        return nombreThor;
    }

    public void setNombreThor(String nombreThor) {
        this.nombreThor = nombreThor;
    }

    public String getDescripcionThor() {
        return descripcionThor;
    }

    public void setDescripcionThor(String descripcionThor) {
        this.descripcionThor = descripcionThor;
    }

    public int getLogoThor() {
        return logoThor;
    }

    public void setLogoThor(int logoThor) {
        this.logoThor = logoThor;
    }
}
