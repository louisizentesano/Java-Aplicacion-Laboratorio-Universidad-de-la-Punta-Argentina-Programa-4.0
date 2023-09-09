/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import universidadejemplo.Vistas.GestionMateria;
import universidadejemplo.Vistas.MenuPrincipal;

/**
 *
 * @author Dario
 */
public class ControladorMenuPrincipal implements ActionListener {

    private final MenuPrincipal menu;

    public ControladorMenuPrincipal(MenuPrincipal menu) {
        this.menu = menu;
        this.menu.jmiFormularioMaterias.addActionListener(this);
        //this.menu.jmMateria.addActionListener(this);

    }

    public void iniciar() {
        menu.setTitle("Universidad de la Punta");
        menu.setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.jmiFormularioMaterias) {
            menu.jFondo.removeAll();
            menu.jFondo.repaint();
            GestionMateria v = new GestionMateria();
            menu.jFondo.add(v);
            v.setVisible(true);
            menu.jFondo.moveToFront(v);
        }
    }

}
