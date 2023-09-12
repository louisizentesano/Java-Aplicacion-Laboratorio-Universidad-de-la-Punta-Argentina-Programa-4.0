/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import universidadejemplo.AccesoAdatos.InscripcionData;
import universidadejemplo.AccesoAdatos.MateriaData;
import universidadejemplo.Entidades.Materia;
import universidadejemplo.Vistas.ConsultaAlumnoMateria;
import universidadejemplo.Vistas.MenuPrincipal;

/**
 *
 * @author Dario
 */
public class ControladorConsultaAlumnoMateria implements ActionListener{
    public MateriaData mdata;
    public InscripcionData idata;
    public MenuPrincipal menu;
    public ConsultaAlumnoMateria vista;

    public ControladorConsultaAlumnoMateria(MateriaData mdata, InscripcionData idata, MenuPrincipal menu, ConsultaAlumnoMateria vista) {
        this.mdata = mdata;
        this.idata = idata;
        this.menu = menu;
        this.vista = vista;
        
        vista.jcbMateria.addActionListener(this);
        vista.jbtSalir.addActionListener(this);
        
    }
    
    public void inicia(){
        menu.jFondo.removeAll();
        menu.jFondo.repaint();
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus(); //le da el foco al formulario
        cargaCombo();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jbtSalir){
            vista.dispose();
        }
        
        
    }
    
    public void modelaTabla(){
        
    }
    
    public void cargaCombo(){
        List<Materia> materias = new ArrayList<Materia>();
        materias = mdata.listarMaterias();
        vista.jcbMateria.removeAllItems();
        for (Materia materia : materias) {
            if (materia.isActivo()){
                String cadena = materia.getIdMateria() + " - " + materia.getNombre() + " de " + materia.getAnioMateria() + " año.";
                vista.jcbMateria.addItem(cadena);
            }
        }
        
    }
}
