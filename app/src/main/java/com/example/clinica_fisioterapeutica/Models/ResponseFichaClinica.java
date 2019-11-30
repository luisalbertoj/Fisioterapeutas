package com.example.clinica_fisioterapeutica.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFichaClinica {
    @SerializedName("lista")
    private List<FichaClinica> lista;
    private int totalDatos;

    public List<FichaClinica> getLista() {
        return lista;
    }

    public void setLista(List<FichaClinica> lista) {
        this.lista = lista;
    }

    public int getTotalDatos() {
        return totalDatos;
    }

    public void setTotalDatos(int totalDatos) {
        this.totalDatos = totalDatos;
    }
}
