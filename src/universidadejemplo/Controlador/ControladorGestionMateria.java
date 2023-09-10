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
import universidadejemplo.AccesoAdatos.MateriaData;
import universidadejemplo.Vistas.GestionMateria;
import universidadejemplo.Vistas.MenuPrincipal;


/**
 *
 * @author Dario
 */
public class ControladorGestionMateria implements ActionListener{
    private Connection con;
    private final GestionMateria vista;
    private final MateriaData data;
    private final MenuPrincipal menu;

    public ControladorGestionMateria(GestionMateria vista, MateriaData data, MenuPrincipal menu) {
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
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.jbtBuscar ){
            JOptionPane.showMessageDialog(null,"Funciona!!!");
        }
        if (e.getSource() == vista.jbtSalir){
            vista.dispose();
        }
    } 

  
    }

   
