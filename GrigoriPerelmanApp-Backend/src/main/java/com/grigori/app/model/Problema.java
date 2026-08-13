package com.grigori.app.model;

import java.time.LocalDateTime;

public class Problema {
    private int id;
    private int usuarioId;
    private String ecuacion;
    private String solucion;
    private LocalDateTime fechaResolucion;
    
    public Problema() {}
    
    public Problema(int usuarioId, String ecuacion, String solucion) {
        this.usuarioId = usuarioId;
        this.ecuacion = ecuacion;
        this.solucion = solucion;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public String getEcuacion() { return ecuacion; }
    public void setEcuacion(String ecuacion) { this.ecuacion = ecuacion; }
    public String getSolucion() { return solucion; }
    public void setSolucion(String solucion) { this.solucion = solucion; }
    public LocalDateTime getFechaResolucion() { return fechaResolucion; }
    public void setFechaResolucion(LocalDateTime fechaResolucion) { this.fechaResolucion = fechaResolucion; }
    
    @Override
    public String toString() {
        return "Problema{id=" + id + ", ecuacion='" + ecuacion + "', solucion='" + solucion + "'}";
    }
}
