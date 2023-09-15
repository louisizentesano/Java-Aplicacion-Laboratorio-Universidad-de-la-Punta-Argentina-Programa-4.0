package universidadejemplo.Controlador;

/**
 *
 * @author PC1 Diego Gimenez
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import universidadejemplo.AccesoAdatos.AlumnoData;
import universidadejemplo.AccesoAdatos.InscripcionData;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Entidades.Inscripcion;
import universidadejemplo.Entidades.Materia;
import universidadejemplo.Vistas.Inscripciones;
import universidadejemplo.Vistas.MenuPrincipal;

public class ControladorInscripciones implements ActionListener, ListSelectionListener {

    private final Inscripciones vista;
    private final AlumnoData alumnoData1;
    private final InscripcionData inscripcionData;
    private final MenuPrincipal menu;
    DefaultTableModel modelo = new DefaultTableModel();

    public ControladorInscripciones(Inscripciones vista, AlumnoData alumnoData, InscripcionData inscripcionData, MenuPrincipal menu) {
        this.vista = vista;
        this.alumnoData1 = alumnoData;
        this.inscripcionData = inscripcionData;
        this.menu = menu;

        this.vista.jbtInscribir.addActionListener(this);
        this.vista.jbtEliminar.addActionListener(this);
        this.vista.jbtSalir1.addActionListener(this);//escucha boton salir
        this.vista.jComboBListAlum.addActionListener(this);//escucha al combo

        this.vista.jTable1.getSelectionModel().addListSelectionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Inscripciones");
        cargarMateriasNoCursadas();
        cargarMateriasCursadas();

        menu.jFondo.removeAll();
        menu.jFondo.repaint();
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus(); //le da el foco al formulario
        rellenar();
        modelaTabla();

    }

    @Override
    //que hace cuand oel boton se activa
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.jbtInscribir) {
            
        }
        
                
        /*
        if (e.getSource() == vista.jbtSalir){ // sale del jInternalFrame
            vista.dispose();
        }
        if (e.getSource() == vista.jcbMateria){ // Actualiza la Tabla con los datos de la consulta de InscripcionData
            System.out.println("Cargando Tabla ");
            String combobox = vista.jcbMateria.getSelectedItem().toString();
            String partes[] = combobox.split("-");
            int idMateria = Integer.parseInt(partes[0].trim());
            System.out.println(" ID Materia " + idMateria);
            List<Alumno> alumnos = new ArrayList<Alumno>();
            alumnos = idata.obtenerAlumnosxMateria(idMateria);
            System.out.println("Alumnos " + alumnos.size());
            modelo.setRowCount(0); // Borra todas las filas
            for (Alumno alumno : alumnos) {
                modelo.addRow(new Object[]{alumno.getIdAlumno(),alumno.getDni(),alumno.getApellido(),alumno.getNombre()});
                System.out.println("Agregando - " + alumno.toString());
            }
            vista.jTabla.setModel(modelo);
        }
        */
//
//            // Obtener la materia seleccionada en la tabla
//            int filaSeleccionada = vista.jTable1.getSelectedRow();
//
//            if (filaSeleccionada != -1) {
//                Materia materiaSeleccionada = InscripcionData.obtenerMateriaSeleccionada(filaSeleccionada);
//                if (materiaSeleccionada != null) {
//
//                    // Realizar la inscripción del alumno en la materia
//                    Inscripcion Insc = new Inscripcion(alumnoSeleccionado, materiaSeleccionada, filaSeleccionada);
//
//                    inscripcionData.guardarInscripcion(Insc);
//
//                    // Actualizar la tabla y cualquier otro componente necesario
//                    cargarMateriasNoCursadas();
//                    cargarMateriasCursadas();
//                }
//            }
//        } else if (e.getSource() == vista.jbtEliminar) {
//
//            // Obtener la materia seleccionada en la tabla
//            int filaSeleccionada = vista.jTable1.getSelectedRow();
//            if (filaSeleccionada != -1) {
//                Materia materiaSeleccionada = obtenerMateriaSeleccionada(filaSeleccionada);
//                if (materiaSeleccionada != null) {
//
//                    // Eliminar la inscripción del alumno en la materia
//                    inscripcionData.borrarInscripcionMateriaAlumno(alumnoSeleccionado.getIdAlumno(), materiaSeleccionada.getIdMateria());
//
//                    // Actualizar la tabla y cualquier otro componente necesario
//                    cargarMateriasNoCursadas();
//                    cargarMateriasCursadas();
//                }
//            }
//        } else if (e.getSource() == vista.jbtSalir1) {
//            vista.dispose();
//        }
        }

        @Override
        public void valueChanged
        (ListSelectionEvent e
        
        ) {
    }

    

    private void cargarMateriasNoCursadas() {

        // Obtener todas las materias disp.
        //      List<Materia> materiasDisponibles = inscripcionData.obtenerMateriasNoCursadas(alumnosSeleccionado.getIdAlumno());
        // Cargar las materias no inscritas en la tabla
        modelo.setRowCount(0);// Limpia la tabla
        //modelo.addColumn("ID");
        //modelo.addColumn("Nombre");
        //modelo.addColumn("Año");

        vista.jTable1.setModel(modelo);

        //       for (Materia materia : materiasDisponibles) {
        //           modelo.addRow(new Object[]{materia.getIdMateria(), materia.getNombre(), materia.getAnioMateria()});
        //           }
    }

    private void cargarMateriasCursadas() {
// Obtener las materias en las que el alumno está inscrito
//      List<Materia> materiasCursadas1 = inscripcionData.obtenerMateriasCursadas(alumnosSeleccionado.getId());
    }

    // private Materia obtenerMateriaSeleccionada(int fila) {
    //     int idMateria = (int) vista.jTable1.getValueAt(fila, 0);
    // Obtener la materia correspondiente según su ID
    //    return materiaData.buscarMateria(idMateria);
    private void rellenar() {
        List<Alumno> alumnos = new ArrayList<>();
        //List<Alumno> materias = new ArrayList<Alumno>(); cualquiera funciona

        alumnos = alumnoData1.listarAlumnos();
        vista.jComboBListAlum.removeAllItems();
        for (Alumno alumno : alumnos) {
            if (alumno.isEstado()) {
                String cadena = alumno.getDni() + "- " + alumno.getApellido();
                vista.jComboBListAlum.addItem(cadena);
            }
        }
    }

    public void modelaTabla() {
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("año");

        vista.jTable1.setModel(modelo);//seteando 
    }

}

//      alumnos =AlumnoData.listarAlumnos();
// }
//}
//public void cargaCombo(){
//        List<Materia> materias = new ArrayList<Materia>();
//        materias = mdata.listarMaterias();
//        vista.jcbMateria.removeAllItems();
//        for (Materia materia : materias) {
//            if (materia.isActivo()){
//                String cadena = materia.getIdMateria() + " - " + materia.getNombre() + " de " + materia.getAnioMateria() + " año.";
//                vista.jcbMateria.addItem(cadena);
//            }
//        }
//    }
