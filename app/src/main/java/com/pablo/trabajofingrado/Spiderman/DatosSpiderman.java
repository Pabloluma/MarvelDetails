package com.pablo.trabajofingrado.Spiderman;

public class DatosSpiderman {
    String nombre;
    String descripcion;
    int logo;

    public DatosSpiderman(String nombre, String descripcion, int logo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.logo = logo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
