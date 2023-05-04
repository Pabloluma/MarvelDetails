package com.pablo.trabajofingrado.Thor;

public class DatosActoresThor {
    String nombreActorThor;
    String personajeThor;
    int fotoThor;


    public DatosActoresThor(String nombreActorThor, String personajeThor, int fotoThor) {
        this.nombreActorThor = nombreActorThor;
        this.personajeThor = personajeThor;
        this.fotoThor = fotoThor;
    }
    public DatosActoresThor(String nombreActorThor, int fotoThor) {
        this.nombreActorThor = nombreActorThor;
        this.fotoThor = fotoThor;
    }

    public String getNombreActorThor() {
        return nombreActorThor;
    }

    public void setNombreActorThor(String nombreActorThor) {
        this.nombreActorThor = nombreActorThor;
    }

    public String getPersonajeThor() {
        return personajeThor;
    }

    public void setPersonajeThor(String personajeThor) {
        this.personajeThor = personajeThor;
    }

    public int getFotoThor() {
        return fotoThor;
    }

    public void setFotoThor(int fotoThor) {
        this.fotoThor = fotoThor;
    }
}
