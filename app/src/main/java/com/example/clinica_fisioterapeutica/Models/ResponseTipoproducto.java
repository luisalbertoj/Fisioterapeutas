package com.example.clinica_fisioterapeutica.Models;

import java.util.List;

public class ResponseTipoproducto {
    private List<TipoProducto> lista;
    private String totalDatos;

    public List<TipoProducto> getLista() {
        return lista;
    }

    public void setLista(List<TipoProducto> lista) {
        this.lista = lista;
    }

    public String getTotalDatos() {
        return totalDatos;
    }

    public void setTotalDatos(String totalDatos) {
        this.totalDatos = totalDatos;
    }
}
