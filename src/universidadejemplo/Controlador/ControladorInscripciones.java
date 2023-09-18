package universidadejemplo.Controlador;

/**
 *
 * @author PC1 Diego Gimenez
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import universidadejemplo.AccesoAdatos.AlumnoData;
import universidadejemplo.AccesoAdatos.InscripcionData;
import universidadejemplo.AccesoAdatos.MateriaData;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Entidades.Inscripcion;
import universidadejemplo.Entidades.Materia;
import universidadejemplo.Vistas.Inscripciones1;
import universidadejemplo.Vistas.MenuPrincipal;

public class ControladorInscripciones implements ActionListener, ListSelectionListener {

    private final Inscripciones1 vista;
    private final AlumnoData alumnoData1;
    private final InscripcionData inscripcionData;
    private final MenuPrincipal menu;
    DefaultTableModel modelo = new DefaultTableModel();

    private List<Materia> materiasDisponibles;
    private List<Materia> materiasInscriptas;
    private Alumno alumnoSeleccionado;

    public ControladorInscripciones(Inscripciones1 vista, AlumnoData alumnoData, InscripcionData inscripcionData, MenuPrincipal menu) {
        this.vista = vista;
        this.alumnoData1 = alumnoData;
        this.inscripcionData = inscripcionData;
        this.menu = menu;

        this.vista.jbtInscribir.addActionListener(this);            //
        this.vista.jbtAnularInscripcion.addActionListener(this);    //escucha al boton anular inscripcion
        this.vista.jbtSalir.addActionListener(this);                //escucha boton salir
        this.vista.jComboBListAlum.addActionListener(this);         //escucha al combo

        this.vista.jTable1.getSelectionModel().addListSelectionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Inscripciones");
        cargarMateriasNoCursadas();
        cargarMateriasCursadas();

        menu.jFondo.removeAll();        //remueve todas las vistas anteriores
        menu.jFondo.repaint();          // repinta
        menu.jFondo.add(vista);         //agrega fondo
        vista.setVisible(true);         //lo hace visible
        menu.jFondo.moveToFront(vista); //mueve el fondo al frente
        vista.requestFocus();           //le da el foco al formulario
        rellenar();                     //
        modelaTabla();                  //

    }

    @Override
    //acciones cuando cada boton se activa
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.jComboBListAlum) {
            alumnoSeleccionado = (Alumno) vista.jComboBListAlum.getSelectedItem();
            cargarMateriasNoCursadas();
            cargarMateriasCursadas();
        } else if (e.getSource() == vista.jbtInscribir) {  //Buscar incripcion el alumno a incribir en 
            inscribirAlumnoEnMateria();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo incribir al alumno");
        }

        if (e.getSource() == vista.jbtSalir) { // sale del jInternalFrame
            vista.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al salir");
        }

        if (e.getSource() == vista.jbtEliminar) {
            anularInscripcionAlumno();
        } else {
            JOptionPane.showMessageDialog(null, "no se pudo anular la incripción");
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e
    ) {
    }

    private void cargarMateriasNoCursadas() {
        // Obtener todas las materias disp.
        materiasDisponibles = inscripcionData.obtenerMateriasNoCursadas(alumnoSeleccionado.getIdAlumno());
        actualizarTablaConMaterias(materiasDisponibles);

        //      List<Materia> materiasDisponibles = inscripcionData.obtenerMateriasNoCursadas(alumnosSeleccionado.getIdAlumno());
        // Cargar las materias no inscritas en la tabla
        modelo.setRowCount(0);// Limpia la tabla
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Año");

        vista.jTable1.setModel(modelo);

    }

    private void cargarMateriasCursadas() {
// Obtener las materias en las que el alumno está inscrito

        materiasInscriptas = inscripcionData.obtenerMateriasCursadas(alumnoSeleccionado.getIdAlumno());
        actualizarTablaConMaterias(materiasInscriptas);
    }

    private void actualizarTablaConMaterias(List<Materia> materias) {
        DefaultTableModel modelo = (DefaultTableModel) vista.jTable1.getModel();
        modelo.setRowCount(0);

        for (Materia materia : materias) {
            modelo.addRow(new Object[]{materia.getIdMateria(), materia.getNombre(), materia.getAnio()});
        }
    }

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

    private void inscribirAlumnoEnMateria() {
        int filaSeleccionada = vista.jTable1.getSelectedRow();
        if (filaSeleccionada != -1) {
            Materia materiaSeleccionada = obtenerMateriaSeleccionada(filaSeleccionada);
            if (materiaSeleccionada != null) {
               // inscripcionData.inscribirAlumnoEnMateria(alumnoSeleccionado.getId(), materiaSeleccionada.getIdMateria());
                cargarMateriasNoCursadas();
                cargarMateriasCursadas();
            }
        }
    }

    private void anularInscripcionAlumno() {
        int filaSeleccionada = vista.jTable1.getSelectedRow();
        if (filaSeleccionada != -1) {
            Materia materiaSeleccionada = obtenerMateriaSeleccionada(filaSeleccionada);
            if (materiaSeleccionada != null) {
           //    inscripcionData.anularInscripcionAlumno(alumnoSeleccionado.getId(), materiaSeleccionada.getIdMateria());
                cargarMateriasNoCursadas();
                cargarMateriasCursadas();
            }
        }
    }

    private Materia obtenerMateriaSeleccionada(int fila) {
        int idMateria = (int) vista.jTable1.getValueAt(fila, 0);
        return IncripcionesData.obtenerMateriaPorID(idMateria);
    }
}
