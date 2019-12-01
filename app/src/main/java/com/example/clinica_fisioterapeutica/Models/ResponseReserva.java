package com.example.clinica_fisioterapeutica.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseReserva {
    @SerializedName("lista")
    private List<Reserva> lista;
    private int totalDatos;

    public List<Reserva> getLista() {
        return lista;
    }

    public void setLista(List<Reserva> lista) {
        this.lista = lista;
    }

    public int getTotalDatos() {
        return totalDatos;
    }

    public void setTotalDatos(int totalDatos) {
        this.totalDatos = totalDatos;
    }
}
