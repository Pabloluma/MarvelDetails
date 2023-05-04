package com.pablo.trabajofingrado.Hulk;

public class DatosActoresHulk {
    String nombreActorHulk;
    String personajeHulk;
    int fotoHulk;

    public DatosActoresHulk(String nombreActorHulk, String personajeHulk, int fotoHulk) {
        this.nombreActorHulk = nombreActorHulk;
        this.personajeHulk = personajeHulk;
        this.fotoHulk = fotoHulk;
    }

    public String getNombreActorHulk() {
        return nombreActorHulk;
    }

    public String getPersonajeHulk() {
        return personajeHulk;
    }

    public int getFotoHulk() {
        return fotoHulk;
    }
}
