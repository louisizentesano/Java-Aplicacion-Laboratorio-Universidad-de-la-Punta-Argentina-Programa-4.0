package universidadejemplo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import universidadejemplo.AccesoAdatos.AlumnoData;
import universidadejemplo.AccesoAdatos.InscripcionData;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Entidades.Materia;
import universidadejemplo.Vistas.CargaNotas;
import universidadejemplo.Vistas.MenuPrincipal;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.net.URL;
import javax.swing.JLabel;

//@author louisinette
public class ControladorCargaNotas implements ActionListener {

    public AlumnoData alumdata;
    public InscripcionData inscdata;
    public CargaNotas vistacarganotas;
    private Image carganotasbackground;
    public MenuPrincipal menu;
    MyTableModel modelo = new MyTableModel();
 
    public ControladorCargaNotas(AlumnoData alumdata, InscripcionData inscdata, CargaNotas vistacarganotas, MenuPrincipal menu) {
        this.alumdata = alumdata;
        this.inscdata = inscdata;
        this.menu = menu;
        this.vistacarganotas = vistacarganotas;

        // Agregan un oyente de accion a los componentes de la interfaz de usuario
        // this se refiere a la instancia actual de la clase que actua como oyente      
        vistacarganotas.jComboBListAlumCargaNotas.addActionListener(this);
        vistacarganotas.jButtonSalirCargaNotas.addActionListener(this);
        vistacarganotas.jButtonGuardar.addActionListener(this);

        UIManager.put("OptionPane.messageFont", UIManager.getFont("Label.font").deriveFont(20.0f));
        // Personaliza los mensajes de validacion de usuario

        ImageIcon imageIcon = new ImageIcon("/universidadejemplo/&Images/bckg2.jpg");
        carganotasbackground = imageIcon.getImage();
        // Esto asegurará que la imagen se cargue cuando se crea una instancia  
        // asigno la imagen cargada a la variable carganotasbackground
    }

    public ControladorCargaNotas() {
        ControladorCargaNotas controlador = new ControladorCargaNotas();
        controlador.mostrarMensajePersonalizado();
    }

    public void inicia() {
        menu.jFondo.add(vistacarganotas); // Agrega la vista actual (vistacarganotas) al contenedor jFondo de la clase menu
        vistacarganotas.setVisible(true); //hace visible se mostrará en la pantalla.
        menu.jFondo.moveToFront(vistacarganotas);// Coloca la vista actual en la parte delantera del contenedor jFondo u otro componentes
        vistacarganotas.requestFocus(); //le da el foco al formulario la vista estará lista para recibir eventos de entrada
        cargarComboCargaNotas(); //metodo cargar datos en un JComboBox de selección en la vista
        ModeloTablaCargaNotas();//configura y muestra una tabla en la vista de datos relacionados con las notas
        cargarFondo();
    }

    //getSource() determina que componente genero el evento
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistacarganotas.jComboBListAlumCargaNotas) {
            // verifico si el evento proviene del JComboBox 
            if (vistacarganotas.jComboBListAlumCargaNotas.getItemCount() > 0) {  ////si tiene elementos >0 

                //  obtengo el elemento seleccionado del JComboBox la cadena que contiene el DNI, apellido, nombre y el ID del alumno
                String selectedItem = (String) vistacarganotas.jComboBListAlumCargaNotas.getSelectedItem();
                String[] partes = selectedItem.split("-");   //Divido la cadena seleccionada  para obtener el ID del alumno
                int idAlumno = Integer.parseInt(partes[2].trim()); //el indice 2 contiene el id del alumno se convierte a entero
                // trim() se utiliza para eliminar los espacios en blanco al principio y al final de la cadena almacenada en partes
                List<Materia> materias = inscdata.obtenerMateriasCursadas(idAlumno);
                modelo.setRowCount(0);
                for (Materia materia : materias) {
                    modelo.addRow(new Object[]{materia.getIdMateria(), materia.getNombre(), inscdata.notadeMateria(idAlumno, materia.getIdMateria())});
                }
                vistacarganotas.jTableCargaNotas.setModel(modelo);
            }
        }

        if (e.getSource() == vistacarganotas.jButtonSalirCargaNotas) {
            vistacarganotas.dispose();
        }

        //Código para manejar el evento del botón "Guardar" para actualizar la nota
        //en una celda seleccionada de la tercera columna de jTableCargaNotas: 
        if (e.getSource() == vistacarganotas.jButtonGuardar) {
            int selectedIndex = vistacarganotas.jComboBListAlumCargaNotas.getSelectedIndex();
            if (selectedIndex == -1 || (selectedIndex == 0 && vistacarganotas.jTableCargaNotas.getSelectedRow() == -1)) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un alumno válido");
                //for (int fila = 0; fila < vistacarganotas.jTableCargaNotas.getSelectedRowCount(); fila++) { // obtener la fila seleccionada
            } else {
                int filaSeleccionada = vistacarganotas.jTableCargaNotas.getSelectedRow(); // obtener la fila seleccionada 
                if (filaSeleccionada >= 0) {
                    Object idMateria = modelo.getValueAt(filaSeleccionada, 0);
                    Object nota = modelo.getValueAt(filaSeleccionada, 2);
                    String selectedItem = (String) vistacarganotas.jComboBListAlumCargaNotas.getSelectedItem();
                    String[] partes = selectedItem.split(" - ");
                    int idAlumno = Integer.parseInt(partes[2]);
                    double notad = 0;
                    if (nota instanceof Number) {
                        notad = ((Number) nota).doubleValue();
                    } else if (nota instanceof String) {
                        try {
                            notad = Double.parseDouble((String) nota);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "No debe ingresar texto solo se aceptan Notas del 1 al 10");
                            return;
                        }
                    }
                    if (notad == 0.0 || notad == 0 || notad == 00) {
                        JOptionPane.showMessageDialog(null, "No se permite guardar una nota igual a cero");
                    } else if (notad > 10) {
                        JOptionPane.showMessageDialog(null, "Solo se aceptan Notas del 1 al 10");
                    } else {
                        inscdata.actualizarNota(idAlumno, (int) idMateria, notad);
                        JOptionPane.showMessageDialog(null, "Se ha actualizado la nota del alumno");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe modificar una nota y seleccionar la fila correspondiente para guardar");
                }
            }
        }

    }

    public void ModeloTablaCargaNotas() {
        modelo.addColumn("Id Materia");
        modelo.addColumn("Nombre");
        modelo.addColumn("Nota");
        vistacarganotas.jTableCargaNotas.setModel(modelo); //Establecer el modelo de tabla creado en modeloTabla
    }

    //cargar combo box
    public void cargarComboCargaNotas() {
        List<Alumno> alumnos = alumdata.listarAlumnos();
        vistacarganotas.jComboBListAlumCargaNotas.removeAllItems();
        for (Alumno alumno : alumnos) {
            if (alumno.isEstado()) {
                String alumc = alumno.getDni() + " - " + alumno.getApellido() + ", " + alumno.getNombre() + " - " + alumno.getIdAlumno();
                vistacarganotas.jComboBListAlumCargaNotas.addItem(alumc);
            }
        }
    }

    private void cargarFondo() {
        ClassLoader directorio = getClass().getClassLoader();
        URL rutaImagenFondo = directorio.getResource("&Images/bckg3.jpg");
       //Se carga una imagen de fondo desde un recurso ubicado en un directorio
        ImageIcon imagenFondoIcon = new ImageIcon(rutaImagenFondo);
       // La ruta de la imagen se utiliza para crear un objeto ImageIcon que representa la imagen de fondo
        Image imagenFondo = imagenFondoIcon.getImage();
       //Se obtiene la imagen desde el objeto ImageIcon
        imagenFondo = imagenFondo.getScaledInstance(vistacarganotas.jPanel1.getWidth(), vistacarganotas.jPanel1.getHeight(), Image.SCALE_SMOOTH);
          // Redimensiona la imagen de fondo al tamaño del menu.jFondo JPanel
         ImageIcon imagenFondoRedimensionadaIcon = new ImageIcon(imagenFondo);
         // La imagen redimensionada se utiliza para crear un nuevo objeto ImageIcon
        vistacarganotas.jLbFondo.setIcon(imagenFondoRedimensionadaIcon);
        vistacarganotas.jLbFondo.setBounds(0, 0, vistacarganotas.jPanel1.getWidth(), vistacarganotas.jPanel1.getHeight());
        vistacarganotas.jPanel1.revalidate();
        vistacarganotas.jPanel1.repaint();
    }

    public void mostrarMensajePersonalizado() {
        // Mostrar el mensaje utilizando JOptionPane.showMessageDialog con el tamaño de fuente configurado
        JOptionPane.showMessageDialog(null, JOptionPane.INFORMATION_MESSAGE);
    }

    public class MyTableModel extends DefaultTableModel {
        //para habilitar la modificacion de la columna 3( indice 2) en jTableCargaNotas
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 2;
        }
    }
}
