package com.grigori.app.dao;

import com.grigori.app.db.ConexionDB;
import com.grigori.app.model.Usuario;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, email, password_hash) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPasswordHash());
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) usuario.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insertar usuario: " + e.getMessage());
        }
        return false;
    }
    
    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return mapearUsuario(rs);
        } catch (SQLException e) {
            System.err.println("Error obtener usuario: " + e.getMessage());
        }
        return null;
    }
    
    public Usuario obtenerUsuarioPorEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return mapearUsuario(rs);
        } catch (SQLException e) {
            System.err.println("Error obtener usuario por email: " + e.getMessage());
        }
        return null;
    }
    
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapearUsuario(rs));
        } catch (SQLException e) {
            System.err.println("Error listar usuarios: " + e.getMessage());
        }
        return lista;
    }
    
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, password_hash = ? WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPasswordHash());
            pstmt.setInt(4, usuario.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar usuario: " + e.getMessage());
        }
        return false;
    }
    
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar usuario: " + e.getMessage());
        }
        return false;
    }
    
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        Timestamp ts = rs.getTimestamp("fecha_registro");
        if (ts != null) u.setFechaRegistro(ts.toLocalDateTime());
        return u;
    }
}
