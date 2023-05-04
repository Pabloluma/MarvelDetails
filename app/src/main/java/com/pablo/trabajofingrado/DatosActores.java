package com.pablo.trabajofingrado;

public class DatosActores {
    String nombre;
    String personaje;
    int foto;

    public DatosActores(String nombre, String personaje, int foto) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPersonaje() {
        return personaje;
    }

    public int getFoto() {
        return foto;
    }
}
