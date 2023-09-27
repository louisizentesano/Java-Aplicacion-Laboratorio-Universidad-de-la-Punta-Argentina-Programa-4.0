/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import universidadejemplo.AccesoAdatos.AlumnoData;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Vistas.GestionAlumnos;
import universidadejemplo.Vistas.MenuPrincipal;

/**
 *
 * @author Matias
 */
public class ControladorGestionAlumnos implements ActionListener, KeyListener {

    private Connection con;
    private final GestionAlumnos vista;
    private final AlumnoData data;
    private final MenuPrincipal menu;
    private int idAlumno;

    public ControladorGestionAlumnos(GestionAlumnos vista, AlumnoData data, MenuPrincipal menu) {
        this.vista = vista;
        this.data = data;
        this.menu = menu;
        vista.jtxDocumento.addKeyListener(this);
        vista.jbtBuscar.addActionListener(this);
        vista.jbtSalir.addActionListener(this);
        vista.jbtNuevo.addActionListener(this);
        vista.jbtEliminar.addActionListener(this);
        vista.jbtGuardar.addActionListener(this);
        vista.jrbEstado.addActionListener(this);
        //vista.jdcFechadeNacimiento.actionPerformed(this);
    }

    public void iniciar() {

        //menu.jFondo.removeAll();
        //menu.jFondo.repaint();
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus();
        vista.jtxDocumento.setText("0");
        vista.jbtGuardar.setEnabled(false);
        vista.jbtEliminar.setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vista.jbtBuscar) {
            if (vista.jtxDocumento.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "El Dni no puede estar en blanco");
            } else {
                int a = Integer.parseInt(vista.jtxDocumento.getText());
                Alumno alum = new Alumno();
                alum = data.buscarAlumnoPorDni(a);
                if (alum != null) {
                    vista.jtxNombre.setText(alum.getNombre());
                    vista.jtxApellido.setText(alum.getApellido());
                    vista.jrbEstado.setSelected(alum.isEstado());
                    vista.jdcFechadeNacimiento.setDate(Date.valueOf(alum.getFechaNacimiento()));
                    this.idAlumno = alum.getIdAlumno();
                    vista.jbtGuardar.setEnabled(true);
                    vista.jbtEliminar.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "El alumno no existe");
                    this.idAlumno = -1;
                }
            }
        }

        if (ae.getSource() == vista.jbtEliminar) {
            int dni = Integer.parseInt(vista.jtxDocumento.getText());
            if (dni > 0) {
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar al alumno?");

                if (confirmacion == JOptionPane.YES_OPTION) {
                    // El usuario confirmó eliminar, procede a eliminar el alumno
                    data.eliminarAlumno(dni);
                    JOptionPane.showMessageDialog(null, "Alumno eliminado con éxito.");

                    // Luego puedes limpiar los campos de la vista si lo deseas
                    vista.jtxDocumento.setText("");
                    vista.jtxNombre.setText("");
                    vista.jtxApellido.setText("");
                    vista.jrbEstado.setSelected(true); // Puedes establecer el estado a false si deseas
                    vista.jdcFechadeNacimiento.setDate(null); // También puedes borrar la fecha si lo deseas
                    data.eliminarAlumno(this.idAlumno);
                } else {
                    // El usuario canceló la eliminación, no hagas nada
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede eliminar un alumno con un DNI inválido.");
            }
        }

        if (ae.getSource() == vista.jbtSalir) {
            vista.dispose();
        }
        if (ae.getSource() == vista.jbtNuevo) {
            vista.jbtNuevo.setEnabled(false);
            vista.jbtEliminar.setEnabled(false);
            vista.jtxDocumento.setText("0");
            vista.jtxNombre.setText("");
            vista.jtxApellido.setText("");
            vista.jtxDocumento.requestFocus();
            vista.jbtGuardar.setEnabled(true);
            this.idAlumno = -1;

        }
        if (ae.getSource() == vista.jbtGuardar) {
            vista.jbtNuevo.setEnabled(true);
            vista.jbtEliminar.setEnabled(true);
            vista.jtxDocumento.setEnabled(true);

            if (idAlumno == 0 && vista.jtxDocumento.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "El número de documento no puede ser cero. Ingrese un valor válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int dni = Integer.parseInt(vista.jtxDocumento.getText());
                String nombre = vista.jtxNombre.getText();
                String apellido = vista.jtxApellido.getText();
                boolean estado = true; // Asumiendo que siempre quieres establecer el estado en verdadero
                java.util.Date nac = vista.jdcFechadeNacimiento.getDate();

                // Convierte el objeto Date a Instant
                Instant instant = nac.toInstant();

                // Convierte el Instant a LocalDate utilizando una zona horaria específica
                LocalDate fecha = instant.atZone(ZoneId.systemDefault()).toLocalDate();

                if (idAlumno == -1) {
                    Alumno a = new Alumno(dni, apellido, nombre, fecha, estado);
                    // Código para guardar el alumno existente
                    // Pregunta al usuario si quiere guardar el nuevo alumno
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de guardar el nuevo alumno?", "Confirmación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        data.guardarAlumno(a);
                        JOptionPane.showMessageDialog(null, "Alumno guardado con éxito.");

                    }
                } else {
                    // Código para modificar un nuevo alumno
                    Alumno b = new Alumno(idAlumno, dni, apellido, nombre, fecha, true);
                    data.modificarAlumno(b);
                    JOptionPane.showMessageDialog(null, "Se ha guardado la modificación.");
                }
            }
        }
    }

    public void focusGained(FocusEvent e) {
        try {
            if (e.getSource() == vista.jtxDocumento) {
                vista.jtxDocumento.selectAll();
            }

            if (e.getSource() == vista.jtxNombre) {
                vista.jtxNombre.selectAll();
            }
            if (e.getSource() == vista.jtxApellido) {
                vista.jtxApellido.selectAll();
            }
        } catch (Exception ex) {
            // Manejar la excepción aquí
            ex.printStackTrace(); // Esto es solo un ejemplo, puedes hacer algo más adecuado en tu aplicación.
        }
    }

    public void focusLost(FocusEvent e) {
        // No es necesario implementar esto si no lo necesitas
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vista.jtxDocumento) {
            char caracter = e.getKeyChar();
            if (caracter < '0' || caracter > '9') {
                e.consume();
            }
        }

    }

    public void agregarIconos() {
        ClassLoader directorio = getClass().getClassLoader();
        URL lupaIconUbicacion = directorio.getResource("&IconButtons/Lupa-Buscar.png");
        ImageIcon lupaIcono = new ImageIcon(lupaIconUbicacion);
        Image imagenRedimensionada = lupaIcono.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        lupaIcono = new ImageIcon(imagenRedimensionada);
        vista.jbtBuscar.setIcon(lupaIcono);

        URL xIconUbicacion = directorio.getResource("&IconButtons/inactivo.png");
        ImageIcon xIcono = new ImageIcon(xIconUbicacion);
        imagenRedimensionada = xIcono.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        xIcono = new ImageIcon(imagenRedimensionada);
        vista.jbtEliminar.setIcon(xIcono);

        URL guardarIconUbicacion = directorio.getResource("&IconButtons/guardarAlum.png");
        ImageIcon guardarIcon = new ImageIcon(guardarIconUbicacion);
        imagenRedimensionada = guardarIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        guardarIcon = new ImageIcon(imagenRedimensionada);
        vista.jbtGuardar.setIcon(guardarIcon);

        guardarIconUbicacion = directorio.getResource("&IconButtons/nuevoAlum.png");
        ImageIcon nuevoIcon = new ImageIcon(guardarIconUbicacion);
        imagenRedimensionada = nuevoIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        nuevoIcon = new ImageIcon(imagenRedimensionada);
        vista.jbtNuevo.setIcon(nuevoIcon);

        guardarIconUbicacion = directorio.getResource("&IconButtons/salida3.png");
        ImageIcon salirIcon = new ImageIcon(guardarIconUbicacion);
        imagenRedimensionada = nuevoIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        salirIcon = new ImageIcon(imagenRedimensionada);
        vista.jbtSalir.setIcon(salirIcon);

        vista.setClosable(true);
        vista.setIconifiable(true);
        vista.setMaximizable(true);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
