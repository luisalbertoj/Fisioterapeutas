package com.example.clinica_fisioterapeutica.Models;

import java.util.List;

public class ResponseCategoria {
    private List<Categoria> lista;
    private String totalDatos;

    public List<Categoria> getLista() {
        return lista;
    }

    public void setLista(List<Categoria> lista) {
        this.lista = lista;
    }

    public String getTotalDatos() {
        return totalDatos;
    }

    public void setTotalDatos(String totalDatos) {
        this.totalDatos = totalDatos;
    }
}
