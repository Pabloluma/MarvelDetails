package com.pablo.trabajofingrado.ironman;

public class DatosIM {
    String nombreIM;
    String descripcionIM;
    int logoIM;

    public DatosIM(String nombreIM, String descripcionIM, int logoIM) {
        this.nombreIM = nombreIM;
        this.descripcionIM = descripcionIM;
        this.logoIM = logoIM;
    }

    public String getNombreIM() {
        return nombreIM;
    }

    public void setNombreIM(String nombreIM) {
        this.nombreIM = nombreIM;
    }

    public String getDescripcionIM() {
        return descripcionIM;
    }

    public void setDescripcionIM(String descripcionIM) {
        this.descripcionIM = descripcionIM;
    }

    public int getLogoIM() {
        return logoIM;
    }

    public void setLogoIM(int logoIM) {
        this.logoIM = logoIM;
    }
}
