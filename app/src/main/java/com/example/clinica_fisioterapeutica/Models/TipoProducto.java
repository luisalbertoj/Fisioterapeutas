package com.example.clinica_fisioterapeutica.Models;

public class TipoProducto {
    private String idTipoProducto;
    private String descripcion;

    public String getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(String idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public TipoProducto() {
    }

    public TipoProducto(String idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
