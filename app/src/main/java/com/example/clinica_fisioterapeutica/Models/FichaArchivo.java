package com.example.clinica_fisioterapeutica.Models;

import java.io.File;

public class FichaArchivo {
    private File file;
    private String idFichaClinica;
    private String nombre;

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
}
