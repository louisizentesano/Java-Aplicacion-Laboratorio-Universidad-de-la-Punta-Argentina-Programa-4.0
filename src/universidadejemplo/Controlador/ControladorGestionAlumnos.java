/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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
        }
    }
    
}
