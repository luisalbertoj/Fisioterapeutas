package com.example.clinica_fisioterapeutica.Models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFichaArchivo {
    @SerializedName("lista")
    private List<FichaArchivo> lista;
    private int totalDatos;

    public List<FichaArchivo> getLista() {
        return lista;
    }

    public void setLista(List<FichaArchivo> lista) {
        this.lista = lista;
    }

    public int getTotalDatos() {
        return totalDatos;
    }

    public void setTotalDatos(int totalDatos) {
        this.totalDatos = totalDatos;
    }
}


