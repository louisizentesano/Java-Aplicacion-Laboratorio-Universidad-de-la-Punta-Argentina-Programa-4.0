/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import universidadejemplo.AccesoAdatos.AlumnoData;
import universidadejemplo.AccesoAdatos.InscripcionData;
import universidadejemplo.AccesoAdatos.MateriaData;
import universidadejemplo.Vistas.CargaNotas;
import universidadejemplo.Vistas.ConsultaAlumnoMateria;
import universidadejemplo.Vistas.GestionAlumnos;
import universidadejemplo.Vistas.GestionMateria;
import universidadejemplo.Vistas.Inscripciones;
import universidadejemplo.Vistas.MenuPrincipal;

/**
 *
 * @author Dario
 */
public class ControladorMenuPrincipal implements ActionListener, MenuListener,ComponentListener {

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
        this.menu.jFondo.addComponentListener(this);

    }

    public void iniciar() {
        menu.setTitle("Universidad de la Punta");
        menu.setLocationRelativeTo(null);
        menu.setResizable(false);
        ponerFondo();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.jmiFormularioAlumno) {
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
        if (e.getSource() == menu.jmiManejoInscripciones) {
            AlumnoData adata = new AlumnoData();
            InscripcionData idata = new InscripcionData();
            Inscripciones vista = new Inscripciones();
            ControladorInscripciones a = new ControladorInscripciones(vista, adata, idata, menu);

            a.iniciar();

        }
        if (e.getSource() == menu.jmiManipulacionNotas) {
            AlumnoData adata = new AlumnoData();
            InscripcionData idata = new InscripcionData();
            CargaNotas vista = new CargaNotas();
            ControladorCargaNotas a = new ControladorCargaNotas(adata, idata, vista, menu);
            a.inicia();

        }
        if (e.getSource() == menu.jmiAlumnosPorMateria) {
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
        if (e.getSource() == menu.jmSalir) {
            //System.out.println("Se presiono Salir");
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

    public void ponerFondo() {
        ClassLoader directorio = getClass().getClassLoader();
        URL rutaImagenFondo = directorio.getResource("&Images/bckgcn.jpg");
        
        // Crea un ImageIcon a partir de la imagen de fondo
        ImageIcon imagenFondoIcon = new ImageIcon(rutaImagenFondo);
        // Obtiene la imagen de fondo
        Image imagenFondo = imagenFondoIcon.getImage();
        // Redimensiona la imagen de fondo al tamaño del JPanel
        imagenFondo = imagenFondo.getScaledInstance(menu.jFondo.getWidth(), menu.jFondo.getHeight(), Image.SCALE_SMOOTH);
        // Crea un nuevo ImageIcon con la imagen redimensionada
        ImageIcon imagenFondoRedimensionadaIcon = new ImageIcon(imagenFondo);
        // Crea una etiqueta JLabel para mostrar la imagen de fondo en el JPanel
        JLabel imagenFondoLabel = new JLabel(imagenFondoRedimensionadaIcon);
        // Establece la ubicación y el tamaño de la imagen de fondo
        imagenFondoLabel.setBounds(0, 0, menu.jFondo.getWidth(), menu.jFondo.getHeight());
        // Agrega la imagen de fondo al JPanel
        menu.jFondo.add(imagenFondoLabel);
        // Asegúrate de que la imagen de fondo esté en la parte posterior para no ocultar otros componentes
        menu.jFondo.setComponentZOrder(imagenFondoLabel, 0);
        // Actualiza el JPanel para mostrar la imagen
        menu.jFondo.revalidate();
        menu.jFondo.repaint();

    }
    

    @Override
    public void componentResized(ComponentEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       ponerFondo();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
