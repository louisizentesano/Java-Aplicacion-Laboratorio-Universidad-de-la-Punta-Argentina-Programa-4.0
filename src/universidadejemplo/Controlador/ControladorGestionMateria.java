/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.sql.Connection;
import javax.swing.JOptionPane;
import universidadejemplo.AccesoAdatos.MateriaData;
import universidadejemplo.Entidades.Materia;
import universidadejemplo.Vistas.GestionMateria;
import universidadejemplo.Vistas.MenuPrincipal;


/**
 *
 * @author Dario
 */
public class ControladorGestionMateria implements ActionListener, FocusListener,KeyListener {
    //private Connection con; // lo elimino porque ni se usa XD
    private final GestionMateria vista;
    private final MateriaData data;
    private final MenuPrincipal menu;

    public ControladorGestionMateria(GestionMateria vista, MateriaData data, MenuPrincipal menu) {
        this.vista = vista;
        this.data = data;
        this.menu = menu;
        
        // Se Colocan los objetos que tendran ActionListener
        vista.jbtBuscar.addActionListener(this);
        vista.jbtSalir.addActionListener(this);
        vista.jbtNuevo.addActionListener(this);
        vista.jbtEliminar.addActionListener(this);
        vista.jbtGuardar.addActionListener(this);
        
        // Se declaran los objetos que tendran FocusListener
        vista.jtxCodigo.addFocusListener(this);
        vista.jtxNombre.addFocusListener(this);
        vista.jtxAño.addFocusListener(this);        
        
        // Se declaran los objetos que tendran KeyListener
        vista.jtxCodigo.addKeyListener(this);
        vista.jtxAño.addKeyListener(this);

    }
    
    public void iniciar(){
        
        menu.jFondo.removeAll();
        menu.jFondo.repaint();
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus(); //le da el foco al formulario
        vista.jtxCodigo.setText("0");
        vista.jtxAño.setText("1");
        vista.jtxCodigo.requestFocus();
        
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.jbtBuscar ){ //Buscar alumno por medio del numero ingresado en el jtxCodigo
            Materia m = new Materia();
            m = data.buscarMateria(Integer.parseInt(vista.jtxCodigo.getText()));
            if (m != null){
                vista.jtxCodigo.setText(m.getIdMateria() + "");
                vista.jtxNombre.setText(m.getNombre());
                vista.jtxAño.setText(m.getAnioMateria()+ "");
                //vista.jchEstado.isSelected(m.getEstado()); // no se como cargar el jCheckBox
                
            }else{
                JOptionPane.showMessageDialog(null, "Se consulto pero no regreso ningun dato");
                vista.jtxAño.setText("1");
                vista.jtxNombre.setText("");
                vista.jtxCodigo.requestFocus();
            }
        }
        
        
        if (e.getSource() == vista.jbtSalir){ //Salir del JinternalFrame GestionMateria
            vista.dispose();
        }
        if (e.getSource() == vista.jbtNuevo){
            vista.jbtNuevo.setEnabled(false);
            vista.jbtEliminar.setEnabled(false);
            vista.jtxCodigo.setText("-1");
            vista.jtxCodigo.setEnabled(false);
            vista.jtxNombre.setText("");
            vista.jtxAño.setText("");
            vista.jchEstado.setSelected(true); 
            vista.jtxNombre.requestFocus();
        }
        if (e.getSource() == vista.jbtGuardar) {
            /* este boton tiene que guardar si es nuevo o modificar si ya existe para esto nos valemos del valor de jxCodigo
                si contiene el valor -1 que es lo que seteamos al precionar el boton nuevo para que limpie los campos
                caso contrario lo que hace es modificar segun el codigo actual siempre y cuando este no sea 0.
             */
            vista.jbtNuevo.setEnabled(true);
            vista.jbtEliminar.setEnabled(true);
            vista.jtxCodigo.setEnabled(true);
            if (!vista.jtxCodigo.getText().equals("-1") && !vista.jtxCodigo.getText().equals("0")) {
                int vresp = JOptionPane.showConfirmDialog(null, "Guardar los Cambios?","Advertencia",JOptionPane.YES_NO_OPTION);
                if (vresp == 0) { // tambien podria ser vresp == JOptionPane.YES_OPTION
                    Materia m = new Materia(Integer.parseInt(vista.jtxCodigo.getText()), vista.jtxNombre.getText(), Integer.parseInt(vista.jtxAño.getText()), vista.isSelected());
                    data.modificarMateria(m);
                }
            } else {
                int vresp = JOptionPane.showConfirmDialog(null, "Crear la nueva Materia?","Advertencia",JOptionPane.YES_NO_OPTION);
                if (vresp == JOptionPane.YES_OPTION) {
                    Materia m = new Materia(-1, vista.jtxNombre.getText(), Integer.parseInt(vista.jtxAño.getText()), true);
                    data.guardarMateria(m);
                }
            }
        }
        if (e.getSource() == vista.jbtEliminar) {
            if (!vista.jtxCodigo.getText().equals("0")) {
                int vResp = JOptionPane.showConfirmDialog(null, "Seguro de Eliminar la materia " + vista.jtxCodigo.getText() + " - " + vista.jtxNombre.getText(),"Advertencia",JOptionPane.YES_NO_OPTION);
                if (vResp == 0) {
                    data.eliminarMateria(Integer.parseInt(vista.jtxCodigo.getText()));
                    vista.jtxCodigo.setText("0");
                    vista.jtxNombre.setText("");
                    vista.jtxAño.setText("1");
                    vista.jchEstado.setEnabled(true);
                    vista.jtxCodigo.requestFocus();
                }
            }
        }

    }

    @Override
    public void focusGained(FocusEvent e) {
       if (e.getSource() == vista.jtxCodigo){
           vista.jtxCodigo.selectAll();
       }
       
       if (e.getSource() == vista.jtxNombre){
           vista.jtxNombre.selectAll();
       }
       if (e.getSource() == vista.jtxAño){
           vista.jtxAño.selectAll();
       }
           
    }

    @Override
    public void focusLost(FocusEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vista.jtxCodigo) {
            char caracter = e.getKeyChar(); // Convierte la tecla precionada en un caracter y luego los compara con los que siquiero que se cargen!!
            if (caracter < '0' || caracter > '9') {
                e.consume();
            }
        }
        if (e.getSource() == vista.jtxAño) {
            char caracter = e.getKeyChar(); // Convierte la tecla precionada en un caracter y luego los compara con los que siquiero que se cargen!!
            if (caracter < '0' || caracter > '9') {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
    }

   
