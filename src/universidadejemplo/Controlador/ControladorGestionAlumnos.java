/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;
import universidadejemplo.AccesoAdatos.AlumnoData;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Vistas.GestionAlumnos;
import universidadejemplo.Vistas.MenuPrincipal;

/**
 *
 * @author Matias
 */
public class ControladorGestionAlumnos implements ActionListener {

    private Connection con;
    private final GestionAlumnos vista;
    private final AlumnoData data;
    private final MenuPrincipal menu;
    private int idAlumno;

    public ControladorGestionAlumnos(GestionAlumnos vista, AlumnoData data, MenuPrincipal menu) {
        this.vista = vista;
        this.data = data;
        this.menu = menu;
        vista.jbtBuscar.addActionListener(this);
        vista.jbtSalir.addActionListener(this);
        vista.jbtNuevo.addActionListener(this);
        vista.jbtEliminar.addActionListener(this);
        vista.jbtGuardar.addActionListener(this);
        vista.jrbEstado.addActionListener(this);
        //vista.jdcFechadeNacimiento.actionPerformed(this);
    }

    public void iniciar() {

        menu.jFondo.removeAll();
        menu.jFondo.repaint();
        menu.jFondo.add(vista);
        vista.setVisible(true);
        menu.jFondo.moveToFront(vista);
        vista.requestFocus();
        vista.jtxDocumento.setText("0");

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vista.jbtBuscar) {
            int a = Integer.parseInt(vista.jtxDocumento.getText());
            Alumno alum = new Alumno();
            alum = data.buscarAlumnoPorDni(a);
            if (alum != null) {
                vista.jtxNombre.setText(alum.getNombre());
                vista.jtxApellido.setText(alum.getApellido());
                vista.jrbEstado.setSelected(alum.isEstado());
                vista.jdcFechadeNacimiento.setDate(Date.valueOf(alum.getFechaNacimiento()));
                this.idAlumno=alum.getIdAlumno();
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no existe");
                this.idAlumno=-1;
            }
        }
        if (ae.getSource() == vista.jbtEliminar) {
            int dni = Integer.parseInt(vista.jtxDocumento.getText());
            if (dni > 0) {
                // Llamamos al método para eliminar el alumno
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
            this.idAlumno=-1;

        }
        if (ae.getSource() == vista.jbtGuardar) {
            vista.jbtNuevo.setEnabled(true);
            vista.jbtEliminar.setEnabled(true);
            vista.jtxDocumento.setEnabled(true);

            int dni = Integer.parseInt(vista.jtxDocumento.getText());
            String nombre = vista.jtxNombre.getText();
            String apellido = vista.jtxApellido.getText();
            boolean estado = true; // Asumiendo que siempre quieres establecer el estado en verdadero
            java.util.Date nac = vista.jdcFechadeNacimiento.getDate();

            // Convierte el objeto Date a Instant
            Instant instant = nac.toInstant();

            // Convierte el Instant a LocalDate utilizando una zona horaria específica
            LocalDate fecha = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            if (dni > 0) {
                // Código para modificar el alumno existente
                Alumno a = new Alumno(dni, apellido, nombre, fecha, estado);
                data.guardarAlumno(a);
            } else {
                // Código para guardar un nuevo alumno
                Alumno b = new Alumno(dni, apellido, nombre, fecha, true);
                data.guardarAlumno(b);
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

    public void keyTyped(KeyEvent e) {
        try {
            if (e.getSource() == vista.jtxDocumento) {
                char caracter = e.getKeyChar();
                if (caracter < '0' || caracter > '9') {
                    e.consume();
                }
            }
            if (e.getSource() == vista.jtxDocumento) {
                char caracter = e.getKeyChar();
                if (caracter < '0' || caracter > '9') {
                    e.consume();
                }
            }
        } catch (Exception ex) {
            // Manejar la excepción aquí
            ex.printStackTrace(); // Esto es solo un ejemplo, puedes hacer algo más adecuado en tu aplicación.
        }
    }

}
