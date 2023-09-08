/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.AccesoAdatos;

import java.awt.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import universidadejemplo.Entidades.Alumno;

/**
 *
 * @author PC1
 */
public class AlumnoData {
    private Connection con;

    public AlumnoData() {
    }
    
    public void guardarAlumno(Alumno alumno) {
        String sql = "INSERT INTO alumno (dni, apellido, nombre, fechaNacimiento, estado) VALUES (?, ?, ?, ?, ?)";
        try {
            /*
             private int idAlumno;
            private String apellido;
            private String nombre;
            private LocalDate fechaNac;
            private boolean activo;
            */
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));//localDate a Date
            ps.setBoolean(5, alumno.isEstado()); // if reducido
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                JOptionPane.showMessageDialog(null, "Alumno añadido con exito.");38 }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno" + ex.getMessage());
        }

    }
    
    public Alumno buscarAlumnoPorDni(int dni){
        Alumno alumno = new Alumno();
        return alumno;
    }
    private List<Alumno> listarAlumnos(){
        List<Alumno> alumnos = new List<>();
        
        
    }
    public void modificarAlumno(Alumno alumno){
        
    }
    public void eliminarAlumno(int id){
        
    }
    
}
