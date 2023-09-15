package universidadejemplo.Controlador;
/**
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import universidadejemplo.AccesoAdatos.AlumnoData;
import universidadejemplo.AccesoAdatos.InscripcionData;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Vistas.CargaNotas;
import universidadejemplo.Vistas.MenuPrincipal;

//@author louisinette
public class ControladorCargaNotas implements ActionListener {
    public AlumnoData alumdata;
    public InscripcionData inscdata;
    public CargaNotas vistacarganotas;
    public MenuPrincipal menu;
    DefaultTableModel modelo = new DefaultTableModel();

    public ControladorCargaNotas(AlumnoData alumdata, InscripcionData inscdata, CargaNotas vistacarganotas, MenuPrincipal menu) {
        this.alumdata = alumdata;
        this.inscdata = inscdata;
        this.menu = menu;
        this.vistacarganotas = vistacarganotas;

        vistacarganotas.jComboBListAlumCargaNotas.addActionListener(this);
        vistacarganotas.jButtonSalirCargaNotas.addActionListener(this);
        vistacarganotas.jButtonGuardar.addActionListener(this);

//agregan un oyente de accion a los componentes de la interfaz de usuario
//se refiere a la instancia actual de la clase ControladorCargarNotas 
// this se refiere a la instancia actual de la clase que actua como oyente de
// los eventos generados por los componentes jcbMateria etc y ejecuta en esta clase
// el metodo actionPerformed de la interfaz ActionListener que implementa
    }

    public void inicia() {
        menu.jFondo.removeAll();
        //eliminando todos los componentes que estaban dentro del contenedor jFondo de la clase menu
//para limpiar cualquier contenido previo antes de agregar la nueva vista
        menu.jFondo.repaint();
        menu.jFondo.add(vistacarganotas); // Agrega la vista actual (vistacarganotas) al contenedor jFondo de la clase menu
        vistacarganotas.setVisible(true); //hace visible se mostrará en la pantalla.
        menu.jFondo.moveToFront(vistacarganotas);// Coloca la vista actual en la parte delantera del contenedor jFondo u otro componentes
        vistacarganotas.requestFocus(); //le da el foco al formulario la vista estará lista para recibir eventos de entrada
        cargarComboCargaNotas(); //metodo cargar datos en un JComboBox u otro componente de selección en la vista
        ModeloTablaCargaNotas(); //configurar y mostrar una tabla en la vista,  para mostrar datos relacionados con las notas
        vistacarganotas.jTableCargaNotas.setEnabled(false);
//Deshabilita la tabla en la vista (jTabla). Esto significa que el usuario no podrá interactuar directamente con la tabla 
//hasta que se habilite nuevamente.
    }

    //getSource() se utiliza para determinar que componente genero el evento
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistacarganotas.jComboBListAlumCargaNotas) { // verifico si el evento proviene del JComboBox 
            if (vistacarganotas.jComboBListAlumCargaNotas.getItemCount() > 0) {  ////si tiene elementos >0 
                //  obtengo el elemento seleccionado del JComboBox la cadena  que contiene el DNI, apellido, nombre y el ID del alumno
                String selectedItem = (String) vistacarganotas.jComboBListAlumCargaNotas.getSelectedItem();

                //Divido la cadena seleccionada para obtener el ID del alumno
                String[] partes = selectedItem.split(" - ");
                int idAlumno = Integer.parseInt(partes[3]); //el indice 3 contiene el id del alumno se convierte a entero

                AlumnoData alumData = new AlumnoData(); // instancia de AlumnoData
                Alumno alumno = alumnoData.listarAlumnos(idAlumno);   // Llamo al metodo listarAlumnos de la clase AlumnoData
                // Ahora  tengo la instancia del alumno con la información
                // Puedo acceder a sus propiedades, como DNI, apellido, nombre, etc

                if (alumno != null) {
                    // Mostrar información del alumno en la jTableCargaNotas
                    DefaultTableModel model = (DefaultTableModel) vistacarganotas.jTableCargaNotas.getModel();
                    model.setRowCount(0); // Limpiar la tabla
                    // Agregar fila con los datos del alumno
                    model.addRow(new Object[]{
                        alumno.getDni(),
                        alumno.getApellido(),
                        alumno.getNombre(),
                        alumno.getIdAlumno()
                    });
                }
            }
        } else if (e.getSource() == vistacarganotas.jButtonSalirCargaNotas) {
            //  }  if (e.getSource() == vistacarganotas.jButtonSalirCargaNotas){
            vistacarganotas.dispose();
        }

        /**
         * // Código para manejar el evento del botón "Guardar" una nueva nota
         * en una celda de la tercera columna // de jTableCargaNotas: }else if
         * (e.getSource() == vistacarganotas.jButtonGuardar) { DefaultTableModel
         * model = (DefaultTableModel)
         * vistacarganotas.jTableCargaNotas.getModel(); int filaSeleccionada =
         * vistacarganotas.jTableCargaNotas.getSelectedRow(); // obtener la fila
         * seleccionada
         *
         * if (filaSeleccionada >= 0) { // Obtener la nueva nota desde un
         * JTextField???nope jTextFieldNuevaNota //double nuevaNota =
         * Double.parseDouble(jTextFieldNuevaNota.getText());
         *
         * // Actualizar el valor de la celda en la tercera columna (índice 2)
         * de la fila seleccionada model.setValueAt(nuevaNota, filaSeleccionada,
         * 2);//o siempre model.setValueAt(nuevaNota, 0, 2); si fuera 1 fila
         * nomas
         *
         * vistacarganotas.jTableCargaNotas.repaint();
         *
         * } else { System.out.println("No selecciono ninguna fila no es posible
         * guardar una nota"); }
         *
         * }
         
    } */
/**
    public void ModeloTablaCargaNotas() {
        modelo.addColumn("Id Materia");
        modelo.addColumn("Nombre");
        modelo.addColumn("Nota");
        vistacarganotas.jTableCargaNotas.setModel(modelo); //Establecer el modelo de tabla creado en modeloTabla
    }

    public void cargarComboCargaNotas() {
        List<Alumno> alumnos = alumdata.listarAlumnos();
        vistacarganotas.jComboBListAlumCargaNotas.removeAllItems();
        for (Alumno alumno : alumnos) {
            if (alumno.isEstado()) {
                String alumnodelcombo = alumno.getDni() + " - " + alumno.getApellido() + ", " + alumno.getNombre() + " - " + alumno.getIdAlumno();
                vistacarganotas.jComboBListAlumCargaNotas.addItem(alumnodelcombo);
                //probar  sino vistacarganotas.jComboBListAlumCargaNotas.addItem(alumno.getNombreCompleto());
                //no puedo usar alumnodelcombo.toString porque no existe el override to.String en Alumno
            }
        }
    }

}
*/