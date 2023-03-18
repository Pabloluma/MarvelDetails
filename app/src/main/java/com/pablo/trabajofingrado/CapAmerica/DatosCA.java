package com.pablo.trabajofingrado.CapAmerica;

public class DatosCA {
    String nombreCA;
    String descripcionCA;
    int logoCA;

    public DatosCA(String nombreCA, String descripcionCA, int logoCA) {
        this.nombreCA = nombreCA;
        this.descripcionCA = descripcionCA;
        this.logoCA = logoCA;
    }

    public String getNombreCA() {
        return nombreCA;
    }

    public void setNombreCA(String nombreCA) {
        this.nombreCA = nombreCA;
    }

    public String getDescripcionCA() {
        return descripcionCA;
    }

    public void setDescripcionCA(String descripcionCA) {
        this.descripcionCA = descripcionCA;
    }

    public int getLogoCA() {
        return logoCA;
    }

    public void setLogoCA(int logoCA) {
        this.logoCA = logoCA;
    }
}
