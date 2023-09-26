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

//@author louisinette
public class ControladorCargaNotas implements ActionListener {

    public AlumnoData alumdata;
    public InscripcionData inscdata;
    public CargaNotas vistacarganotas;
    private Image carganotasbackground;
    public MenuPrincipal menu;
    MyTableModel modelo = new MyTableModel();
    private ArrayList<HashMap<String, Object>> valoresOriginales = new ArrayList<>();

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
        UIManager.put("OptionPane.messageFont", UIManager.getFont("Label.font").deriveFont(20.0f));

        ImageIcon imageIcon = new ImageIcon("/universidadejemplo/&Images/bckg2.jpg");
        carganotasbackground = imageIcon.getImage();
        //Esto asegurará que la imagen se cargue cuando se crea una instancia de la clase ControladorCargaNotas
        //asigno la imagen cargada a la variable carganotasbackground
        // Luego, puedo usar esta imagen en otros métodos, como inicia(), para configurar el fondo personalizado del JPanel.
    }

    public ControladorCargaNotas() {
        ControladorCargaNotas controlador = new ControladorCargaNotas();
        controlador.mostrarMensajePersonalizado();
    }

    public void inicia() {
       // menu.jFondo.removeAll();
       //eliminando todos los componentes que estaban dentro del contenedor jFondo de la clase menu
       //para limpiar cualquier contenido previo antes de agregar la nueva vista
       //menu.jFondo.repaint();
        menu.jFondo.add(vistacarganotas); // Agrega la vista actual (vistacarganotas) al contenedor jFondo de la clase menu
        vistacarganotas.setVisible(true); //hace visible se mostrará en la pantalla.
        menu.jFondo.moveToFront(vistacarganotas);// Coloca la vista actual en la parte delantera del contenedor jFondo u otro componentes
        vistacarganotas.requestFocus(); //le da el foco al formulario la vista estará lista para recibir eventos de entrada
        cargarComboCargaNotas(); //metodo cargar datos en un JComboBox u otro componente de selección en la vista
        ModeloTablaCargaNotas();//configurar y mostrar una tabla en la vista,  para mostrar datos relacionados con las notas

        // vistacarganotas.jTableCargaNotas.setEnabled(false);
//Deshabilita la tabla en la vista (jTabla). Esto significa que el usuario no podrá interactuar directamente con la tabla 
//hasta que se habilite nuevamente.
        ImagePanel imagePanel = new ImagePanel(carganotasbackground);
        vistacarganotas.add(imagePanel);
        //creación y adición un objeto ImagePanel a vistacarganotas pasando carganotasbackground como argumento.
        //es donde se inicializa el panel y se configura para tener la imagen de fondo
        //Agrego este panel personalizado a la vistacarganotas para que funcione como el fondo de la vista
    }

    //getSource() se utiliza para determinar que componente genero el evento
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistacarganotas.jComboBListAlumCargaNotas) {
            // verifico si el evento proviene del JComboBox 
            if (vistacarganotas.jComboBListAlumCargaNotas.getItemCount() > 0) {  ////si tiene elementos >0 

                //  obtengo el elemento seleccionado del JComboBox la cadena  que contiene el DNI, apellido, nombre y el ID del alumno
                String selectedItem = (String) vistacarganotas.jComboBListAlumCargaNotas.getSelectedItem();

                //Divido la cadena seleccionada  para obtener el ID del alumno
                String[] partes = selectedItem.split("-");
                int idAlumno = Integer.parseInt(partes[2].trim()); //el indice 0 contiene el id del alumno se convierte a entero
                //antes de intentar convertirla a un entero utiliza  ndo Integer.parseInt()
                // trim() se utiliza para eliminar los espacios en blanco al principio y al final de la cadena almacenada en partes[0] 
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
        //en una celda de la tercera columna // de jTableCargaNotas: 
        if (e.getSource() == vistacarganotas.jButtonGuardar) {
            int selectedIndex = vistacarganotas.jComboBListAlumCargaNotas.getSelectedIndex();
            if (selectedIndex == -1 || (selectedIndex == 0 && vistacarganotas.jTableCargaNotas.getSelectedRow() == -1)) {
                // verifica si el usuario ha seleccionado un alumno válido (índice no es -1) o si ha seleccionado el primer elemento
                //(índice 0) y también ha seleccionado una fila en la tabla. Si ninguna de estas condiciones se cumple, se muestra
                //el mensaje "No ha seleccionado un alumno válido". De lo contrario, se permite modificar o guardar datos en la tabla
                JOptionPane.showMessageDialog(null, "No ha seleccionado un alumno válido");
                //for (int fila = 0; fila < vistacarganotas.jTableCargaNotas.getSelectedRowCount(); fila++) { // obtener la fila seleccionada
            } else {
                int filaSeleccionada = vistacarganotas.jTableCargaNotas.getSelectedRow(); // obtener la fila seleccionada 
                if (filaSeleccionada >= 0) {
                    Object idMateria = modelo.getValueAt(filaSeleccionada, 0);
                    Object nota = modelo.getValueAt(filaSeleccionada, 2);
                    String selectedItem = (String) vistacarganotas.jComboBListAlumCargaNotas.getSelectedItem();
                    //Divido la cadena seleccionada para obtener el ID del alumno:
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

// modelo de la tabla columnas
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
                //probar  sino vistacarganotas.jComboBListAlumCargaNotas.addItem(alumno.getNombreCompleto());
                //no puedo usar alumnodelcombo.toString porque no existe el override to.String en Alumno, el error era que en el diseño
                //estana el type de tabla alumno se saco todo
            }
        }
    }

    private void cargarTablaCargaNotas(int idAlumno) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    public class ImagePanel extends JPanel {

        private Image carganotasbackground;

        public ImagePanel(Image carganotasbackground) {
            this.carganotasbackground = carganotasbackground;
            //ImagePanel se encarga de dibujar la imagen de fondo en 
            //el método paintComponent, lo que permite mostrar la imagen como fondo en la vista
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (carganotasbackground != null) {
                g.drawImage(carganotasbackground, 0, 0, getWidth(), getHeight(), this);
                setOpaque(false);
                 super.paint(g);
            }
        }
    }

    public void mostrarMensajePersonalizado() {
        // Mostrar el mensaje utilizando JOptionPane.showMessageDialog con el tamaño de fuente configurado
        JOptionPane.showMessageDialog(null, JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean filaHaSidoModificada(int fila) {
        Object notaActual = modelo.getValueAt(fila, 2);
        HashMap<String, Object> filaOriginal = valoresOriginales.get(fila);
        Object notaOriginal = filaOriginal.get("Nota");
        return !notaOriginal.equals(notaActual);

    }

    public class MyTableModel extends DefaultTableModel {

        //para habilitar la modificacion de la columna 3( indice 2) en jTableCargaNotas
        @Override
        public boolean isCellEditable(int row, int column) {
            //   probando solo sleccionado: int filaSeleccionada = vistacarganotas.getSelectedRow();
            //int columnaSeleccionada = tabla.convertColumnIndexToModel(tabla.getSelectedColumn());
            return column == 2; // Columna número 4 (columna 3 en índice base 0)
        }
    }
}
