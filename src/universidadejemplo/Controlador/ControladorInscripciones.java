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
import universidadejemplo.Vistas.Inscripciones;
import universidadejemplo.Vistas.MenuPrincipal;

public class ControladorInscripciones implements ActionListener, ListSelectionListener {

    private final Inscripciones vista;
    private final AlumnoData alumnoData;
    private final InscripcionData inscripcionData;
    private final MenuPrincipal menu;
    DefaultTableModel modelo = new DefaultTableModel();

    private List<Materia> materiasDisponibles;
    private List<Materia> materiasInscriptas;
    private Alumno alumnoSeleccionado;

    public ControladorInscripciones(Inscripciones vista, AlumnoData alumnoData, InscripcionData inscripcionData, MenuPrincipal menu) {
        this.vista = vista;
        this.alumnoData = alumnoData;
        this.inscripcionData = inscripcionData;
        this.menu = menu;

        this.vista.jbtInscribir.addActionListener(this);            //
        this.vista.jbtAnularInscripcion.addActionListener(this);    //escucha al boton anular inscripcion
        this.vista.jbtSalir.addActionListener(this);                //escucha boton salir
        this.vista.jComboBListAlum.addActionListener(this);         //escucha al combo
        this.vista.jRadioButtonMateriasInscriptas.addActionListener(this);
        this.vista.jRadioButtonMateriasNoInscriptas.addActionListener(this);
        this.vista.jTable1.getSelectionModel().addListSelectionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Inscripciones");

        menu.jFondo.removeAll();        //remueve todas las vistas anteriores
        menu.jFondo.repaint();          // repinta
        menu.jFondo.add(vista);         //agrega fondo
        vista.setVisible(true);         //lo hace visible
        menu.jFondo.moveToFront(vista); //mueve el fondo al frente
        vista.requestFocus();           //le da el foco al formulario
        rellenarCombo();                     //
        modelaTabla();                  //
        vista.jRadioButtonMateriasInscriptas.setSelected(true); //muestra por defecto materias inscriptas inicializada
        rellenarTabla(); //rellena la tabla de materias incriptas inicializada
        vista.jbtInscribir.setEnabled(false); //desactiva el boton Inscribir (ya que muestra por defecto materias inscriptas)
       }

    @Override
    //acciones cuando cada boton se activa
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.jComboBListAlum) {
            rellenarTabla();
        }

        if (e.getSource() == vista.jRadioButtonMateriasInscriptas) {
            // Deshabilita el botón "Inscribir" cuando se selecciona "Materias Inscriptas"
            vista.jbtInscribir.setEnabled(false);
            vista.jRadioButtonMateriasNoInscriptas.setSelected(false);

            // habilita el botón "Anular Inscripción" cuando se selecciona "Materias No Inscriptas"
            vista.jbtAnularInscripcion.setEnabled(true);
            vista.jRadioButtonMateriasInscriptas.setSelected(true);

            rellenarTabla();
        }

        if (e.getSource() == vista.jRadioButtonMateriasNoInscriptas) {
            // Habilita el botón "Inscribir" cuando se selecciona "Materias No Inscriptas"
            vista.jbtInscribir.setEnabled(true);
            vista.jRadioButtonMateriasInscriptas.setSelected(false);

            // Deshabilita el botón "Anular Inscripción" cuando se selecciona "Materias No Inscriptas"
            vista.jbtAnularInscripcion.setEnabled(false);
            vista.jRadioButtonMateriasNoInscriptas.setSelected(true);

            rellenarTabla();
        }

        if (e.getSource() == vista.jbtInscribir) {  //Buscar incripcion el alumno a incribir en 

            if (vista.jTable1.getSelectedRow() != -1) {

                Inscripcion inscribir = new Inscripcion();
                Materia materiasNueva = new Materia();
                Alumno alumn = new Alumno();

                int filaSelect = vista.jTable1.getSelectedRow(); //obtener fila seleccionada
                int idMateriaSelect = (int) modelo.getValueAt(filaSelect, 0);

                materiasNueva.setIdMateria(idMateriaSelect);
                alumn.setIdAlumno(traerID());

                inscribir.setAlumno(alumn);
                inscribir.setMateria(materiasNueva);
                inscribir.setNota(0);

                inscripcionData.guardarInscripcion(inscribir); //al alumno lo isncribe en esa materia
                rellenarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "El alumno ya está inscrito en esta materia.");
                //System.out.println("Para inscribir debe seleccionar primero una materia");
            }
            //  JOptionPane.showMessageDialog(null, "No se pudo incribir al alumno");
        }

        if (e.getSource() == vista.jbtSalir) { // sale del jInternalFrame
            vista.dispose();
        }

        if (e.getSource() == vista.jbtAnularInscripcion) {
            anularInscripcionAlumno();
            //            JOptionPane.showMessageDialog(null, "no se pudo anular la incripción");
        }

        if (e.getSource() == vista.jRadioButtonMateriasInscriptas) {
            vista.jRadioButtonMateriasNoInscriptas.setSelected(false);
            rellenarTabla();
        }

        if (e.getSource() == vista.jRadioButtonMateriasNoInscriptas) {
            vista.jRadioButtonMateriasInscriptas.setSelected(false);
            rellenarTabla();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
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

    private void rellenarTabla() {

        List<Materia> materias = new ArrayList<Materia>(); //array list vacio para cargar los datos que provienen del metodo inscripcion data obtener materias cursadas

        if (vista.jRadioButtonMateriasInscriptas.isSelected()) {
            materias = inscripcionData.obtenerMateriasCursadas(traerID());
        } else {
            materias = inscripcionData.obtenerMateriasNoCursadas(traerID());
        }

        System.out.println(
                "Rellenar tabla funciona. Alumnos: " + materias.size());

        modelo.setRowCount(0); //borra el modelo

        for (Materia materia : materias) {
            modelo.addRow(new Object[]{materia.getIdMateria(), materia.getNombre(), materia.getAnioMateria()});//rellena el modelo
        }

        vista.jTable1.setModel(modelo); //asignamos el modelo a la vista

    }

    private int traerID() {
        String varTemp = vista.jComboBListAlum.getSelectedItem().toString(); //trae el texto que está en el combo
        String[] partes = varTemp.split("-"); // particiona la cadena usando el guón como punto de partida y genera el vector
        int idAlumno = Integer.parseInt(partes[0].trim()); // selecciona la primer parte de l vector indice 0, lo extrae el dato que está en caracteres y lo parsea a entero
        return idAlumno;
    }

    private void rellenarCombo() {
        List<Alumno> alumnos = new ArrayList<>();
        //o List<Alumno> materias = new ArrayList<Alumno>(); cualquiera funciona

        alumnos = alumnoData.listarAlumnos();
        vista.jComboBListAlum.removeAllItems();
        System.out.println("tamaño de alumno " + alumnos.size());

        for (Alumno alumno : alumnos) {
            if (alumno.isEstado()) {
                String cadena = alumno.getIdAlumno() + " - " + alumno.getDni() + " - " + alumno.getApellido();
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

            }
        }
    }

    private void anularInscripcionAlumno() {
        int filaSeleccionada = vista.jTable1.getSelectedRow();
        if (filaSeleccionada != -1) {
            Materia materiaSeleccionada = obtenerMateriaSeleccionada(filaSeleccionada);
            if (materiaSeleccionada != null) {
                int idAlumno = traerID();
                int idMateria = materiaSeleccionada.getIdMateria();

                inscripcionData.borrarInscripcionMateriaAlumno(idAlumno, idMateria);

                cargarMateriasNoCursadas();
                cargarMateriasCursadas();
                JOptionPane.showMessageDialog(null, "Inscripción anulada con éxito.");
            }
        }
    }

    private Materia obtenerMateriaSeleccionada(int fila) {
        int idMateria = (int) vista.jTable1.getValueAt(fila, 0);
        // return universidadejemplo.AccesoAdatos.IncripcionData.obtenerMateriaPorID(idMateria);
        return new Materia();

    }
}
