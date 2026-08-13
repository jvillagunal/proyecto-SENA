package com.grigori.app.model;

import java.time.LocalDateTime;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String passwordHash;
    private LocalDateTime fechaRegistro;
    
    public Usuario() {}
    
    public Usuario(String nombre, String email, String passwordHash) {
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "', email='" + email + "'}";
    }
}
