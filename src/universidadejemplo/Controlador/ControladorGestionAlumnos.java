/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import universidadejemplo.AccesoAdatos.AlumnoData;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Vistas.GestionAlumnos;
import universidadejemplo.Vistas.MenuPrincipal;

/**
 *
 * @author Matias
 */
public class ControladorGestionAlumnos implements ActionListener{
    private Connection con;
    private final GestionAlumnos vista;
    private final AlumnoData data;
    private final MenuPrincipal menu;
    
    public ControladorGestionAlumnos(GestionAlumnos vista, AlumnoData data, MenuPrincipal menu){
        this.vista = vista;
        this.data = data;
        this.menu = menu;
        vista.jbtBuscar.addActionListener(this);
        vista.jbtSalir.addActionListener(this);
        vista.jbtNuevo.addActionListener(this);
        vista.jbtEliminar.addActionListener(this);
        vista.jbtGuardar.addActionListener(this);
    }

    public void iniciar(){
    
        menu.jFondo.removeAll();
        menu.jFondo.repaint();
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if (ae.getSource() == vista.jbtBuscar){
           Alumno a = new Alumno();
           a = data.buscarAlumnoPorDni(Integer.parseInt(vista.jtxDocumento.getText()));
           if (a != null){
               vista.jtxDocumento.setText(a.getIdAlumno()+ "");
               vista.jtxNombre.setText(a.getNombre());
               vista.jtxApellido.setText(a.getApellido());
           }else{
               JOptionPane.showMessageDialog(null, "Su Consulta no pudo ser llevada a cabo");
           }
        }
        
        if (ae.getSource() == vista.jbtSalir){
            vista.dispose();
        }
        if(ae.getSource() == vista.jbtNuevo){
            vista.jbtNuevo.setEnabled(false);
            vista.jbtEliminar.setEnabled(false);
            vista.jtxDocumento.setText("-1");
            vista.jtxDocumento.setEnabled(false);
            vista.jtxNombre.setText("");
            vista.jtxApellido.setText("");
            vista.jtxNombre.requestFocus();
            //Si alguno sabe cual es la mejor opcion de codigo para Jcalendar, puede editar aqui sin problema.
        }
       if (ae.getSource() == vista.jbtGuardar) {
        vista.jbtNuevo.setEnabled(true);
        vista.jbtEliminar.setEnabled(true);
        vista.jtxDocumento.setEnabled(true);
        
        int dni = Integer.parseInt(vista.jtxDocumento.getText());
        String nombre = vista.jtxNombre.getText();
        String apellido = vista.jtxApellido.getText();
        boolean estado = true; // Asumiendo que siempre quieres establecer el estado en verdadero
        
        if (dni != -1 && dni != 1) {
            // Código para modificar el alumno existente
            Alumno a = new Alumno(dni, apellido, nombre, LocalDate.MIN, estado);
            data.modificarAlumno(a);
        } else {
            // Código para guardar un nuevo alumno
            Alumno b = new Alumno(dni, apellido, nombre, LocalDate.MIN, true);
            data.guardarAlumno(b);
        }
    }
}
}