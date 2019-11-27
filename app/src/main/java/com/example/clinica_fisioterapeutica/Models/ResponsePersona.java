package com.example.clinica_fisioterapeutica.Models;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePersona {
    @SerializedName("lista")
    private List<Persona> lista;
    private int totalDatos;

    public List<Persona> getLista() {
        return lista;
    }

    public void setLista(List<Persona> lista) {
        this.lista = lista;
    }

    public int getTotalDatos() {
        return totalDatos;
    }

    public void setTotalDatos(int totalDatos) {
        this.totalDatos = totalDatos;
    }
}
