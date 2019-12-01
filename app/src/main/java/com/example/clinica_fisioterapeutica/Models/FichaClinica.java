package com.example.clinica_fisioterapeutica.Models;

public class FichaClinica {
    private String idFichaClinica;
    private String motivoConsulta;
    private String diagnostico;
    private String observacion;
    private Persona idEmpleado;
    private Persona idCliente;
    private TipoProducto idTipoProducto;
    private String fechaHora;
    private String fechaHoraCadena;

    public String getIdFichaClinica() {
        return idFichaClinica;
    }

    public void setIdFichaClinica(String idFichaClinica) {
        this.idFichaClinica = idFichaClinica;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Persona getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Persona idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Persona getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Persona idCliente) {
        this.idCliente = idCliente;
    }

    public TipoProducto getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(TipoProducto idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getFechaHoraCadena() {
        return fechaHoraCadena;
    }

    public void setFechaHoraCadena(String fechaHoraCadena) {
        this.fechaHoraCadena = fechaHoraCadena;
    }
}
