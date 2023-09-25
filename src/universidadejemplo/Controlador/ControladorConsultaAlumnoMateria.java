/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;


import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import universidadejemplo.AccesoAdatos.InscripcionData;
import universidadejemplo.AccesoAdatos.MateriaData;
import universidadejemplo.Entidades.Materia;
import universidadejemplo.Entidades.Alumno;
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
    // MyTableModel modelo = new MyTableModel();
    DefaultTableModel modelo = new DefaultTableModel();
    

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
        modelaTabla();
        vista.jTabla.setEnabled(false);
        agregarIconos();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jbtSalir){ // sale del jInternalFrame
            vista.dispose();
        }
        if (e.getSource() == vista.jcbMateria){ // Actualiza la Tabla con los datos de la consulta de InscripcionData
            int idMateria = extraerIdMateria();
            List<Alumno> alumnos = new ArrayList<Alumno>();
            alumnos = idata.obtenerAlumnosxMateria(idMateria);
            modelo.setRowCount(0); // Borra todas las filas
            for (Alumno alumno : alumnos) {
                modelo.addRow(new Object[]{alumno.getIdAlumno(),alumno.getDni(),alumno.getApellido(),alumno.getNombre()});
            }
            vista.jTabla.setModel(modelo);
        }
        
    }
    private int extraerIdMateria(){
        int idMateria = -1;
        try{
            String combobox = vista.jcbMateria.getSelectedItem().toString();
            String partes[] = combobox.split("-");
            idMateria = Integer.parseInt(partes[0].trim()); // pensar en si necesita un Try cach para la conversion pero pareciera que no!!!
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "A ocurrido un error al cargar los indices en el combobox, revices la posicion del idMateria");
        }
        
        return idMateria;
    }
    public void modelaTabla() {
        modelo.addColumn("ID Alumno");
        modelo.addColumn("DNI");
        modelo.addColumn("Apellido");
        modelo.addColumn("Nombre");
        vista.jTabla.setModel(modelo);
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

    public class MyTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Aquí puedes personalizar la edición de celdas según tus necesidades
            // En este ejemplo, solo permitimos editar la columna número 4 (nombre)
            return column == 2; // Columna número 4 (columna 3 en índice base 0)
        }
    }
    private void agregarIconos(){
        ClassLoader directorio = getClass().getClassLoader();
        URL guardarIconUbicacion = directorio.getResource("&IconButtons/salir1.png");
        ImageIcon salirIcon = new ImageIcon(guardarIconUbicacion);
        Image imagenRedimensionada = salirIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        salirIcon = new ImageIcon(imagenRedimensionada);
        vista.jbtSalir.setIcon(salirIcon);
                
        vista.setClosable(true);
        vista.setIconifiable(true);
        vista.setMaximizable(true);
    }
}
