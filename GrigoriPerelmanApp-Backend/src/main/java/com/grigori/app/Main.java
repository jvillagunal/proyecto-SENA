package com.grigori.app;

import com.grigori.app.dao.ProblemaDAO;
import com.grigori.app.dao.UsuarioDAO;
import com.grigori.app.model.Problema;
import com.grigori.app.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class Main {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final ProblemaDAO problemaDAO = new ProblemaDAO();
    
    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> gestionarUsuarios();
                case 2 -> gestionarProblemas();
                case 0 -> {
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                }
                default -> System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n=== GrigoriPerelmánApp - Módulo CRUD ===");
        System.out.println("1. Gestión de Usuarios");
        System.out.println("2. Gestión de Problemas");
        System.out.println("0. Salir");
    }
    
    private static void gestionarUsuarios() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Usuarios ---");
            System.out.println("1. Insertar usuario");
            System.out.println("2. Buscar usuario por ID");
            System.out.println("3. Listar todos los usuarios");
            System.out.println("4. Actualizar usuario");
            System.out.println("5. Eliminar usuario");
            System.out.println("0. Volver");
            opcion = leerEntero("Opción: ");
            
            switch (opcion) {
                case 1 -> insertarUsuario();
                case 2 -> buscarUsuario();
                case 3 -> listarUsuarios();
                case 4 -> actualizarUsuario();
                case 5 -> eliminarUsuario();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
    
    private static void insertarUsuario() {
        System.out.println("=== Insertar nuevo usuario ===");
        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: ");
        String password = leerTexto("Contraseña: ");
        Usuario usuario = new Usuario(nombre, email, password);
        if (usuarioDAO.insertarUsuario(usuario)) {
            System.out.println("Usuario insertado con ID: " + usuario.getId());
        } else {
            System.out.println("Error al insertar usuario.");
        }
    }
    
    private static void buscarUsuario() {
        int id = leerEntero("ID del usuario: ");
        Usuario u = usuarioDAO.obtenerUsuarioPorId(id);
        if (u != null) {
            System.out.println("Usuario encontrado: " + u);
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
    
    private static void listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("=== Lista de usuarios ===");
            usuarios.forEach(System.out::println);
        }
    }
    
    private static void actualizarUsuario() {
        int id = leerEntero("ID del usuario a actualizar: ");
        Usuario u = usuarioDAO.obtenerUsuarioPorId(id);
        if (u == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        System.out.println("Datos actuales: " + u);
        String nuevoNombre = leerTexto("Nuevo nombre (dejar vacío para no cambiar): ");
        if (!nuevoNombre.isEmpty()) u.setNombre(nuevoNombre);
        String nuevoEmail = leerTexto("Nuevo email (dejar vacío para no cambiar): ");
        if (!nuevoEmail.isEmpty()) u.setEmail(nuevoEmail);
        String nuevaPassword = leerTexto("Nueva contraseña (dejar vacío para no cambiar): ");
        if (!nuevaPassword.isEmpty()) u.setPasswordHash(nuevaPassword);
        
        if (usuarioDAO.actualizarUsuario(u)) {
            System.out.println("Usuario actualizado correctamente.");
        } else {
            System.out.println("Error al actualizar.");
        }
    }
    
    private static void eliminarUsuario() {
        int id = leerEntero("ID del usuario a eliminar: ");
        System.out.print("¿Está seguro? (s/n): ");
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("s")) {
            if (usuarioDAO.eliminarUsuario(id)) {
                System.out.println("Usuario eliminado.");
            } else {
                System.out.println("Error al eliminar.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    
    private static void gestionarProblemas() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Problemas ---");
            System.out.println("1. Insertar problema (vincular a usuario)");
            System.out.println("2. Buscar problema por ID");
            System.out.println("3. Listar problemas de un usuario");
            System.out.println("4. Listar todos los problemas");
            System.out.println("5. Actualizar problema");
            System.out.println("6. Eliminar problema");
            System.out.println("0. Volver");
            opcion = leerEntero("Opción: ");
            
            switch (opcion) {
                case 1 -> insertarProblema();
                case 2 -> buscarProblema();
                case 3 -> listarProblemasPorUsuario();
                case 4 -> listarTodosProblemas();
                case 5 -> actualizarProblema();
                case 6 -> eliminarProblema();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
    
    private static void insertarProblema() {
        int usuarioId = leerEntero("ID del usuario al que pertenece el problema: ");
        String ecuacion = leerTexto("Ecuación (formato LaTeX): ");
        String solucion = leerTexto("Solución (explicación paso a paso): ");
        Problema p = new Problema(usuarioId, ecuacion, solucion);
        if (problemaDAO.insertarProblema(p)) {
            System.out.println("Problema insertado con ID: " + p.getId());
        } else {
            System.out.println("Error al insertar problema.");
        }
    }
    
    private static void buscarProblema() {
        int id = leerEntero("ID del problema: ");
        Problema p = problemaDAO.obtenerProblemaPorId(id);
        if (p != null) {
            System.out.println("Problema encontrado: " + p);
        } else {
            System.out.println("Problema no encontrado.");
        }
    }
    
    private static void listarProblemasPorUsuario() {
        int usuarioId = leerEntero("ID del usuario: ");
        List<Problema> problemas = problemaDAO.listarProblemasPorUsuario(usuarioId);
        if (problemas.isEmpty()) {
            System.out.println("No hay problemas asociados a este usuario.");
        } else {
            System.out.println("=== Problemas del usuario " + usuarioId + " ===");
            problemas.forEach(System.out::println);
        }
    }
    
    private static void listarTodosProblemas() {
        List<Problema> problemas = problemaDAO.listarTodosLosProblemas();
        if (problemas.isEmpty()) {
            System.out.println("No hay problemas registrados.");
        } else {
            System.out.println("=== Todos los problemas ===");
            problemas.forEach(System.out::println);
        }
    }
    
    private static void actualizarProblema() {
        int id = leerEntero("ID del problema a actualizar: ");
        Problema p = problemaDAO.obtenerProblemaPorId(id);
        if (p == null) {
            System.out.println("Problema no encontrado.");
            return;
        }
        System.out.println("Datos actuales: " + p);
        String nuevaEcuacion = leerTexto("Nueva ecuación (dejar vacío para no cambiar): ");
        if (!nuevaEcuacion.isEmpty()) p.setEcuacion(nuevaEcuacion);
        String nuevaSolucion = leerTexto("Nueva solución (dejar vacío para no cambiar): ");
        if (!nuevaSolucion.isEmpty()) p.setSolucion(nuevaSolucion);
        
        if (problemaDAO.actualizarProblema(p)) {
            System.out.println("Problema actualizado.");
        } else {
            System.out.println("Error al actualizar.");
        }
    }
    
    private static void eliminarProblema() {
        int id = leerEntero("ID del problema a eliminar: ");
        System.out.print("¿Está seguro? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            if (problemaDAO.eliminarProblema(id)) {
                System.out.println("Problema eliminado.");
            } else {
                System.out.println("Error al eliminar.");
            }
        } else {
            System.out.println("Cancelado.");
        }
    }
    
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Ingrese un número: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
    
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}
