/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import universidadejemplo.AccesoAdatos.AlumnoData;
import universidadejemplo.AccesoAdatos.InscripcionData;
import universidadejemplo.AccesoAdatos.MateriaData;
import universidadejemplo.Vistas.ConsultaAlumnoMateria;
import universidadejemplo.Vistas.GestionAlumnos;
import universidadejemplo.Vistas.GestionMateria;
import universidadejemplo.Vistas.Inscripciones;
import universidadejemplo.Vistas.MenuPrincipal;

/**
 *
 * @author Dario
 */
public class ControladorMenuPrincipal implements ActionListener, MenuListener{

    private final MenuPrincipal menu;

    public ControladorMenuPrincipal(MenuPrincipal menu) {
        this.menu = menu;
        //con  addActionListener se escucham a los JmenuItems en el metodo actionPerformed
        this.menu.jmiFormularioAlumno.addActionListener(this);
        this.menu.jmiFormularioMaterias.addActionListener(this);
        this.menu.jmiManejoInscripciones.addActionListener(this);
        this.menu.jmiManipulacionNotas.addActionListener(this);
        this.menu.jmiAlumnosPorMateria.addActionListener(this);
        // con AddMenuListener se escuchan a los jMenu en los metodos menuSelected, MenuDeselected y menuCanceled
        // Para Git
        this.menu.jmSalir.addMenuListener(this);

    }

    public void iniciar() {
        menu.setTitle("Universidad de la Punta");
        menu.setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.jmiFormularioAlumno ){
            AlumnoData data = new AlumnoData();
            GestionAlumnos vista = new GestionAlumnos();
            ControladorGestionAlumnos a = new ControladorGestionAlumnos(vista, data, menu);
            a.iniciar();
        }
        if (e.getSource() == menu.jmiFormularioMaterias) {
            MateriaData data = new MateriaData();
            GestionMateria vista = new GestionMateria();
            ControladorGestionMateria a = new ControladorGestionMateria(vista, data, menu);
            a.iniciar();
        }
        if(e.getSource() == menu.jmiManejoInscripciones){
            AlumnoData adata = new AlumnoData();
            InscripcionData idata = new InscripcionData();
            Inscripciones vista = new Inscripciones();
            ControladorInscripciones a = new ControladorInscripciones(vista, adata, idata, menu);
            a.iniciar();
                  
            
        }
        if(e.getSource() == menu.jmiManipulacionNotas){
            
        }
        if(e.getSource() == menu.jmiAlumnosPorMateria){
            MateriaData data = new MateriaData();
            InscripcionData data1 = new InscripcionData();
            ConsultaAlumnoMateria vista = new ConsultaAlumnoMateria();
            ControladorConsultaAlumnoMateria a = new ControladorConsultaAlumnoMateria(data, data1, menu, vista);
            a.inicia();
            
        }
    }

    @Override
    public void menuSelected(MenuEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getSource() == menu.jmSalir){
            menu.dispose();
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
