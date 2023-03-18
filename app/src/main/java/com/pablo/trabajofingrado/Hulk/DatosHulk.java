package com.pablo.trabajofingrado.Hulk;

public class DatosHulk {
    String nombreHulk;
    String descripcionHulk;
    int logoHulk;

    public DatosHulk(String nombreHulk, String descripcionHulk, int logoHulk) {
        this.nombreHulk = nombreHulk;
        this.descripcionHulk = descripcionHulk;
        this.logoHulk = logoHulk;
    }

    public String getNombreHulk() {
        return nombreHulk;
    }

    public void setNombreHulk(String nombreHulk) {
        this.nombreHulk = nombreHulk;
    }

    public String getDescripcionHulk() {
        return descripcionHulk;
    }

    public void setDescripcionHulk(String descripcionHulk) {
        this.descripcionHulk = descripcionHulk;
    }

    public int getLogoHulk() {
        return logoHulk;
    }

    public void setLogoHulk(int logoHulk) {
        this.logoHulk = logoHulk;
    }
}
