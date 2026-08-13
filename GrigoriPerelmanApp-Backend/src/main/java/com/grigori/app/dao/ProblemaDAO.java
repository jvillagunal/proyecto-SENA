package com.grigori.app.dao;

import com.grigori.app.db.ConexionDB;
import com.grigori.app.model.Problema;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProblemaDAO {
    
    public boolean insertarProblema(Problema problema) {
        String sql = "INSERT INTO problemas (usuario_id, ecuacion, solucion) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, problema.getUsuarioId());
            pstmt.setString(2, problema.getEcuacion());
            pstmt.setString(3, problema.getSolucion());
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) problema.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insertar problema: " + e.getMessage());
        }
        return false;
    }
    
    public Problema obtenerProblemaPorId(int id) {
        String sql = "SELECT * FROM problemas WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return mapearProblema(rs);
        } catch (SQLException e) {
            System.err.println("Error obtener problema: " + e.getMessage());
        }
        return null;
    }
    
    public List<Problema> listarProblemasPorUsuario(int usuarioId) {
        List<Problema> lista = new ArrayList<>();
        String sql = "SELECT * FROM problemas WHERE usuario_id = ? ORDER BY fecha_resolucion DESC";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) lista.add(mapearProblema(rs));
        } catch (SQLException e) {
            System.err.println("Error listar problemas por usuario: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Problema> listarTodosLosProblemas() {
        List<Problema> lista = new ArrayList<>();
        String sql = "SELECT * FROM problemas ORDER BY fecha_resolucion DESC";
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapearProblema(rs));
        } catch (SQLException e) {
            System.err.println("Error listar todos los problemas: " + e.getMessage());
        }
        return lista;
    }
    
    public boolean actualizarProblema(Problema problema) {
        String sql = "UPDATE problemas SET ecuacion = ?, solucion = ? WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, problema.getEcuacion());
            pstmt.setString(2, problema.getSolucion());
            pstmt.setInt(3, problema.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar problema: " + e.getMessage());
        }
        return false;
    }
    
    public boolean eliminarProblema(int id) {
        String sql = "DELETE FROM problemas WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar problema: " + e.getMessage());
        }
        return false;
    }
    
    private Problema mapearProblema(ResultSet rs) throws SQLException {
        Problema p = new Problema();
        p.setId(rs.getInt("id"));
        p.setUsuarioId(rs.getInt("usuario_id"));
        p.setEcuacion(rs.getString("ecuacion"));
        p.setSolucion(rs.getString("solucion"));
        Timestamp ts = rs.getTimestamp("fecha_resolucion");
        if (ts != null) p.setFechaResolucion(ts.toLocalDateTime());
        return p;
    }
}
