package com.example.clinica_fisioterapeutica.Models;

import java.io.File;

public class FichaArchivo {
    private String idFichaArchivo;
    private File file;
    private String idFichaClinica;
    private String nombre;
    private String urlImagen;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getIdFichaClinica() {
        return idFichaClinica;
    }

    public void setIdFichaClinica(String idFichaClinica) {
        this.idFichaClinica = idFichaClinica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getIdFichaArchivo() {
        return idFichaArchivo;
    }

    public void setIdFichaArchivo(String idFichaArchivo) {
        this.idFichaArchivo = idFichaArchivo;
    }
}
