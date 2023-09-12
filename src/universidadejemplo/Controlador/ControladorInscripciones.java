package universidadejemplo.Controlador;

/**
 *
 * @author PC1 Diego Gimenez
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import universidadejemplo.AccesoAdatos.InscripcionData;
import universidadejemplo.AccesoAdatos.MateriaData;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Entidades.Materia;
import universidadejemplo.Vistas.Inscripciones;

public class ControladorInscripciones implements ActionListener, ListSelectionListener {

    private final Inscripciones vista;
    private final Alumno alumnoSeleccionado;
    private final MateriaData materiaData;
    private final InscripcionData inscripcionData;

    public ControladorInscripciones(Inscripciones vista, Alumno alumnoSeleccionado, MateriaData materiaData, InscripcionData inscripcionData) {
        this.vista = vista;
        this.alumnoSeleccionado = alumnoSeleccionado;
        this.materiaData = materiaData;
        this.inscripcionData = inscripcionData;

        this.vista.jbtInscribir.addActionListener(this);
        this.vista.jbtEliminar.addActionListener(this);
        this.vista.jbtSalir1.addActionListener(this);

        this.vista.jTable1.getSelectionModel().addListSelectionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Inscripciones - " + alumnoSeleccionado.getNombre());
        cargarMateriasNoInscriptas();
        cargarMateriasInscriptas();
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
                    inscripcionData.inscribirAlumnoEnMateria(alumnoSeleccionado.getId(), materiaSeleccionada.getIdMateria());

                    // Actualizar la tabla y cualquier otro componente necesario
                    cargarMateriasNoInscriptas();
                    cargarMateriasInscriptas();
                }
            }
        } else if (e.getSource() == vista.jbtEliminar) {

                    // Obtener la materia seleccionada en la tabla
            int filaSeleccionada = vista.jTable1.getSelectedRow();
            if (filaSeleccionada != -1) {
                Materia materiaSeleccionada = obtenerMateriaSeleccionada(filaSeleccionada);
                if (materiaSeleccionada != null) {

                    // Eliminar la inscripción del alumno en la materia
                    inscripcionData.eliminarInscripcion(alumnoSeleccionado.getId(), materiaSeleccionada.getIdMateria());

                    // Actualizar la tabla y cualquier otro componente necesario
                    cargarMateriasNoInscriptas();
                    cargarMateriasInscriptas();
                }
            }
        } else if (e.getSource() == vista.jbtSalir1) {
            vista.dispose();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    private void cargarMateriasNoInscriptas() {

        // Obtener todas las materias disp.
        List<Materia> materiasDisponibles = materiaData.obtenerMateriasDisponiblesParaInscripcion(alumnoSeleccionado.getId());

        // Cargar las materias no inscritas en la tabla
        DefaultTableModel modelo = (DefaultTableModel) vista.jTable1.getModel();

        modelo.setRowCount(0); // Limpia la tabla
       
        for (Materia materia : materiasDisponibles) {
            modelo.addRow(new Object[]{materia.getIdMateria(), materia.getNombre(), materia.getAnio()});
        }
    }

    private void cargarMateriasInscriptas() {

    // Obtener las materias en las que el alumno está inscrito
        List<Materia> materiasInscriptas = inscripcionData.obtenerMateriasInscriptasPorAlumno(alumnoSeleccionado.getId());
    }

    private Materia obtenerMateriaSeleccionada(int fila) {
        int idMateria = (int) vista.jTable1.getValueAt(fila, 0);

        // Obtener la materia correspondiente según su ID
        return materiaData.obtenerMateriaPorID(idMateria);
    }
}
