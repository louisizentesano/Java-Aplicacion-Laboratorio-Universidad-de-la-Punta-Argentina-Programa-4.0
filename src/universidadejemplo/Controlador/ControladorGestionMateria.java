/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
//import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static jdk.nashorn.internal.objects.NativeDebug.getClass;
import universidadejemplo.AccesoAdatos.MateriaData;
import universidadejemplo.Entidades.Materia;
import universidadejemplo.Vistas.GestionMateria;
import universidadejemplo.Vistas.MenuPrincipal;

/**
 * @author Dario
 */
public class ControladorGestionMateria implements ActionListener, FocusListener, KeyListener {

    //private Connection con; // lo elimino porque ni se usa XD
    private final GestionMateria vista;
    private final MateriaData data;
    private final MenuPrincipal menu;

    public ControladorGestionMateria(GestionMateria vista, MateriaData data, MenuPrincipal menu) {
        this.vista = vista;
        this.data = data;
        this.menu = menu;

        // Se Colocan los objetos que tendran ActionListener
        vista.jbtBuscar.addActionListener(this);
        vista.jbtSalir.addActionListener(this);
        vista.jbtNuevo.addActionListener(this);
        vista.jbtEliminar.addActionListener(this);
        vista.jbtGuardar.addActionListener(this);

        // Se declaran los objetos que tendran FocusListener
        vista.jtxCodigo.addFocusListener(this);
        vista.jtxNombre.addFocusListener(this);
        vista.jtxAño.addFocusListener(this);

        // Se declaran los objetos que tendran KeyListener
        vista.jtxCodigo.addKeyListener(this);
        vista.jtxAño.addKeyListener(this);

    }

    public void iniciar() {

        //menu.jFondo.removeAll();
        //menu.jFondo.repaint();
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus(); //le da el foco al formulario
        vista.jtxCodigo.setText("0");
        vista.jtxAño.setText("1");
        vista.jbtEliminar.setEnabled(false);
        vista.jbtGuardar.setEnabled(false);
        vista.jtxCodigo.requestFocus();
        agregaIconos(); // se cargan los iconos en la vista 
        ponerFondo();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.jbtBuscar) { //Buscar alumno por medio del numero ingresado en el jtxCodigo
            Materia m = new Materia();
            m = data.buscarMateria(Integer.parseInt(vista.jtxCodigo.getText()));
            if (m != null) {
                vista.jtxCodigo.setText(m.getIdMateria() + "");
                vista.jtxNombre.setText(m.getNombre());
                vista.jtxAño.setText(m.getAnioMateria() + "");
                vista.jchEstado.setSelected(m.isActivo()); // no se como cargar el jCheckBox
                vista.jbtEliminar.setEnabled(true);
                vista.jbtGuardar.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Se consulto pero no regreso ningun dato");
                vista.jtxAño.setText("1");
                vista.jtxNombre.setText("");
                vista.jtxCodigo.requestFocus();
                vista.jbtEliminar.setEnabled(false);
                vista.jbtGuardar.setEnabled(false);
            }
        }

        if (e.getSource() == vista.jbtSalir) { //Salir del JinternalFrame GestionMateria
            vista.dispose();
        }
        if (e.getSource() == vista.jbtNuevo) {
            vista.jbtNuevo.setEnabled(false);
            vista.jbtEliminar.setEnabled(false);
            vista.jbtBuscar.setEnabled(false);
            vista.jtxCodigo.setText("-1");
            vista.jtxCodigo.setEnabled(false);
            vista.jtxNombre.setText("");
            vista.jtxAño.setText("");
            vista.jchEstado.setSelected(true);
            vista.jbtGuardar.setEnabled(true);
            vista.jtxNombre.requestFocus();

        }
        if (e.getSource() == vista.jbtGuardar) {
            /* este boton tiene que guardar si es nuevo o modificar si ya existe para esto nos valemos del valor de jxCodigo
                si contiene el valor -1 que es lo que seteamos al precionar el boton nuevo para que limpie los campos
                caso contrario lo que hace es modificar segun el codigo actual siempre y cuando este no sea 0.
             */
            boolean cambio = false;
            if (!vista.jtxCodigo.getText().equals("-1") && !vista.jtxCodigo.getText().equals("0")) {
                int vresp = JOptionPane.showConfirmDialog(null, "Guardar los Cambios?", "Advertencia", JOptionPane.YES_NO_OPTION);
                if (vresp == 0) { // tambien podria ser vresp == JOptionPane.YES_OPTION
                    Materia m = new Materia(Integer.parseInt(vista.jtxCodigo.getText()), vista.jtxNombre.getText(), Integer.parseInt(vista.jtxAño.getText()), vista.isSelected());
                    data.modificarMateria(m);
                    cambio = true;
                }
            } else {
                int vresp = JOptionPane.showConfirmDialog(null, "Crear la nueva Materia?", "Advertencia", JOptionPane.YES_NO_OPTION);
                if (vresp == JOptionPane.YES_OPTION) {
                    Materia m = new Materia(-1, vista.jtxNombre.getText(), Integer.parseInt(vista.jtxAño.getText()), true);
                    data.guardarMateria(m);
                    cambio = true;
                    List<Materia> materias = new ArrayList<>();
                    materias = data.listarMaterias();
                    int idGenerado = -1;
                    for (Materia materia : materias) {
                        if (materia.getIdMateria() > idGenerado) {
                            idGenerado = materia.getIdMateria();
                        }
                    }
                    vista.jtxCodigo.setText(idGenerado + "");
                }
            }
            if (cambio) {
                vista.jbtNuevo.setEnabled(true);
                vista.jbtEliminar.setEnabled(true); // modificacion para comint
                vista.jtxCodigo.setEnabled(true);
                vista.jbtBuscar.setEnabled(true);
            }
        }
        if (e.getSource() == vista.jbtEliminar) { // revicion de porque no elimima
            if (!vista.jtxCodigo.getText().equals("0")) {
                int vResp = JOptionPane.showConfirmDialog(null, "Seguro de Eliminar la materia " + vista.jtxCodigo.getText() + " - " + vista.jtxNombre.getText(), "Advertencia", JOptionPane.YES_NO_OPTION);
                if (vResp == 0) {
                    data.eliminarMateria(Integer.parseInt(vista.jtxCodigo.getText()));
                    vista.jtxCodigo.setText("0");
                    vista.jtxNombre.setText("");
                    vista.jtxAño.setText("1");
                    vista.jchEstado.setEnabled(true);
                    vista.jbtEliminar.setEnabled(false);
                    vista.jbtGuardar.setEnabled(false);
                    vista.jtxCodigo.requestFocus();
                }
            }
        }

    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == vista.jtxCodigo) {
            vista.jtxCodigo.selectAll();
        }

        if (e.getSource() == vista.jtxNombre) {
            vista.jtxNombre.selectAll();
        }
        if (e.getSource() == vista.jtxAño) {
            vista.jtxAño.selectAll();
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vista.jtxCodigo) {
            char caracter = e.getKeyChar(); // Convierte la tecla precionada en un caracter y luego los compara con los que siquiero que se cargen!!
            if (caracter < '0' || caracter > '9') {
                e.consume();
            }
        }
        if (e.getSource() == vista.jtxAño) {
            char caracter = e.getKeyChar(); // Convierte la tecla precionada en un caracter y luego los compara con los que siquiero que se cargen!!
            if (caracter < '0' || caracter > '9') {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void agregaIconos() {
        int alto = 20;
        int ancho = 20;
        // Obtén la ruta relativa a la ubicación de la clase Controlador eso es la carpeta SRC del proyecto ese seria la carpeta de inicio
        ClassLoader directorio = getClass().getClassLoader();
        URL lupaIconUbicacion = directorio.getResource("&IconButtons/Lupa-buscar.png"); // Creamos la ruta al recurso en este caso el icono de lupa
        // Crea un ImageIcon utilizando la URL de la imagen
        ImageIcon lupaIcono = new ImageIcon(lupaIconUbicacion); // creamos la Imagen Icono para asignarsela al contenerdor
        // Redimensionar el icono pasandolo a imagen con el nuevo tamaño y luego convirtiendolo en icono XD
        Image imagenRedimensionada = lupaIcono.getImage().getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
        lupaIcono = new ImageIcon(imagenRedimensionada);
        vista.jbtBuscar.setIcon(lupaIcono); // asignamos al boton el icono

        URL xIconUbicacion = directorio.getResource("&IconButtons/inactivo.png");
        ImageIcon xIcono = new ImageIcon(xIconUbicacion);
        imagenRedimensionada = xIcono.getImage().getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
        xIcono = new ImageIcon(imagenRedimensionada);
        vista.jbtEliminar.setIcon(xIcono);

        URL guardarIconUbicacion = directorio.getResource("&IconButtons/salvado.png");
        ImageIcon guardarIcon = new ImageIcon(guardarIconUbicacion);
        imagenRedimensionada = guardarIcon.getImage().getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
        guardarIcon = new ImageIcon(imagenRedimensionada);
        vista.jbtGuardar.setIcon(guardarIcon);

        guardarIconUbicacion = directorio.getResource("&IconButtons/nuevo.png");
        ImageIcon nuevoIcon = new ImageIcon(guardarIconUbicacion);
        imagenRedimensionada = nuevoIcon.getImage().getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
        nuevoIcon = new ImageIcon(imagenRedimensionada);
        vista.jbtNuevo.setIcon(nuevoIcon);

        guardarIconUbicacion = directorio.getResource("&IconButtons/salir1.png");
        ImageIcon salirIcon = new ImageIcon(guardarIconUbicacion);
        imagenRedimensionada = salirIcon.getImage().getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
        salirIcon = new ImageIcon(imagenRedimensionada);
        vista.jbtSalir.setIcon(salirIcon);

        vista.setClosable(true);
        vista.setIconifiable(true);
        vista.setMaximizable(true);

    }

    private void ponerFondo1() {
        ClassLoader directorio = getClass().getClassLoader();
        URL rutaImagenFondo = directorio.getResource("&Images/bckgcn.jpg");

        // Crea un ImageIcon a partir de la imagen de fondo
        ImageIcon imagenFondoIcon = new ImageIcon(rutaImagenFondo);
        // Obtiene la imagen de fondo
        Image imagenFondo = imagenFondoIcon.getImage();
        // Redimensiona la imagen de fondo al tamaño del JPanel
        imagenFondo = imagenFondo.getScaledInstance(vista.jPanel1.getWidth(), vista.jPanel1.getHeight(), Image.SCALE_SMOOTH);
        // Crea un nuevo ImageIcon con la imagen redimensionada
        ImageIcon imagenFondoRedimensionadaIcon = new ImageIcon(imagenFondo);
        // Crea una etiqueta JLabel para mostrar la imagen de fondo en el JPanel
        //JLabel imagenFondoLabel = new JLabel(imagenFondoRedimensionadaIcon);
        // Establece la ubicación y el tamaño de la imagen de fondo
        vista.jLbFondo.setIcon(imagenFondoIcon);
        
        //imagenFondoLabel.setBounds(0, 0, vista.jPanel1.getWidth(), vista.jPanel1.getHeight());
        vista.jLbFondo.setBounds(0, 0, vista.jPanel1.getWidth(), vista.jPanel1.getHeight());
        // Agrega la imagen de fondo al JPanel
        //vista.jPanel1.add(imagenFondoLabel);
        // Asegúrate de que la imagen de fondo esté en la parte posterior para no ocultar otros componentes
        //vista.jPanel1.setComponentZOrder(imagenFondoLabel, 0);
        //vista.jPanel1.setComponentZOrder(vista.jLbFondo, 0);
        // Establece el orden Z de todos los componentes en el JPanel
        Component[] components = vista.jPanel1.getComponents();
        for (Component component : components) {
            vista.jPanel1.setComponentZOrder(component, vista.jPanel1.getComponentCount() - 1);
        }
        // Actualiza el JPanel para mostrar la imagen
        vista.jPanel1.revalidate();
        vista.jPanel1.repaint();

    }
    
    private void ponerFondo() {
      // Carga la imagen de fondo
    ClassLoader classLoader = getClass().getClassLoader();
    URL imageUrl = classLoader.getResource("&Images/bckgcn.jpg");
    ImageIcon imageIcon = new ImageIcon(imageUrl);
    Image backgroundImage = imageIcon.getImage();

    // Establece el JPanel como no opaco para que se pueda ver la imagen de fondo
    vista.jPanel1.setOpaque(false);

    // Sobrescribe el método paintComponent para dibujar la imagen de fondo
    vista.jPanel1 = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    };

    vista.jPanel1.setLayout(new BorderLayout());

    // Agrega los demás componentes al JPanel
    // ...

    // Actualiza el JPanel para mostrar la imagen
    vista.jPanel1.revalidate();
    vista.jPanel1.repaint();
}
}
