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
    private final Alumno alumnoSeleccionado;
    private final AlumnoData almunoData;
    private final InscripcionData inscripcionData;
    private final MenuPrincipal menu;
    DefaultTableModel modelo = new DefaultTableModel();

    public ControladorInscripciones(Inscripciones vista, Alumno alumnoSeleccionado, AlumnoData alumnoData, InscripcionData inscripcionData, MenuPrincipal menu) {
        this.vista = vista;
        this.alumnoSeleccionado = alumnoSeleccionado;
        this.almunoData = alumnoData;
        this.inscripcionData = inscripcionData;

        this.vista.jbtInscribir.addActionListener(this);
        this.vista.jbtEliminar.addActionListener(this);
        this.vista.jbtSalir1.addActionListener(this);

        this.vista.jTable1.getSelectionModel().addListSelectionListener(this);
        this.menu = menu;

    }

    public void iniciar() {
        vista.setTitle("Inscripciones - " + alumnoSeleccionado.getNombre());
        cargarMateriasNoCursadas();
        cargarMateriasCursadas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jbtInscribir) {

            // Obtener la materia seleccionada en la tabla
            int filaSeleccionada = vista.jTable1.getSelectedRow();
           
            if (filaSeleccionada != -1) {
                Materia materiaSeleccionada = obtenerMateriaSeleccionada(filaSeleccionada);
                if (materiaSeleccionada != null) {

                    // Realizar la inscripción del alumno en la materia
                    Inscripcion Insc = new Inscripcion(alumnoSeleccionado, materiaSeleccionada, filaSeleccionada);

                    inscripcionData.guardarInscripcion(Insc);

                    // Actualizar la tabla y cualquier otro componente necesario
                    cargarMateriasNoCursadas();
                    cargarMateriasCursadas();
                }
            }
        } else if (e.getSource() == vista.jbtEliminar) {

            // Obtener la materia seleccionada en la tabla
            int filaSeleccionada = vista.jTable1.getSelectedRow();
            if (filaSeleccionada != -1) {
                Materia materiaSeleccionada = obtenerMateriaSeleccionada(filaSeleccionada);
                if (materiaSeleccionada != null) {

                    // Eliminar la inscripción del alumno en la materia
                    inscripcionData.borrarInscripcionMateriaAlumno(alumnoSeleccionado.getIdAlumno(), materiaSeleccionada.getIdMateria());

                    // Actualizar la tabla y cualquier otro componente necesario
                    cargarMateriasNoCursadas();
                    cargarMateriasCursadas();
                }
            }
        } else if (e.getSource() == vista.jbtSalir1) {
            vista.dispose();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    private void cargarMateriasNoCursadas() {

        // Obtener todas las materias disp.
        List<Materia> materiasDisponibles = inscripcionData.obtenerMateriasNoCursadas(alumnoSeleccionado.getIdAlumno());

        // Cargar las materias no inscritas en la tabla
        modelo.setRowCount(0);// Limpia la tabla
        //modelo.addColumn("ID");
        //modelo.addColumn("Nombre");
        //modelo.addColumn("Año");

        vista.jTable1.setModel(modelo);

        for (Materia materia : materiasDisponibles) {
            modelo.addRow(new Object[]{materia.getIdMateria(), materia.getNombre(), materia.getAnioMateria()});
        }
    }

    private void cargarMateriasCursadas() {

        // Obtener las materias en las que el alumno está inscrito
        List<Materia> materiasCursadas1 = inscripcionData.obtenerMateriasCursadas(alumnoSeleccionado.getId());
    }

   // private Materia obtenerMateriaSeleccionada(int fila) {
   //     int idMateria = (int) vista.jTable1.getValueAt(fila, 0);

        // Obtener la materia correspondiente según su ID
   //    return materiaData.buscarMateria(idMateria);
       // System.out.println("");
    

    private void rellenar() {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        alumnos =AlumnoData.listarAlumnos();

    }

}
