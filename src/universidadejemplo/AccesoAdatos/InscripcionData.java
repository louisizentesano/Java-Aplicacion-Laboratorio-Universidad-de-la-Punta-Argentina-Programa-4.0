package universidadejemplo.AccesoAdatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Entidades.Inscripcion;
import universidadejemplo.Entidades.Materia;

/**
 * lLouisinette
 */
public class InscripcionData {

    private Connection con = null;
    private MateriaData matdata = new MateriaData();
    private AlumnoData aludata = new AlumnoData();

    public InscripcionData() {
    }

    public void guardarInscripcion(Inscripcion insc) {
        con = Conexion.getConexion();
        try {
            String sql = "INSERT INTO inscripcion (nota, idAlumno, idMateria) VALUES ( ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDouble(1, insc.getNota());
                ps.setInt(2, insc.getAlumno().getIdAlumno());
                ps.setInt(3, insc.getMateria().getIdMateria());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Inscripcion exitosa");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error no se pudo realizar la inscripcion " + ex.getMessage());
        }
    }

    public List<Inscripcion> obtenerInscripciones() {
        con = Conexion.getConexion();
        ArrayList<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT * FROM inscripcion";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            //se itera a través de las filas del conjunto de resultados (resultSet).
            while (resultSet.next()) {
                Inscripcion inscripcion = new Inscripcion(); //Para cada fila, se crea una instancia de la clase Inscripcion llamada inscripcion.
                inscripcion.setIdInscripcion(resultSet.getInt("idInscripto"));
                inscripcion.setNota(resultSet.getDouble("Nota"));
                Materia materia = matdata.buscarMateria(resultSet.getInt("idMateria"));
                Alumno alumno = aludata.buscarAlumnoPorDni(resultSet.getInt("idAlumno"));
                inscripcion.setMateria(materia);
                inscripcion.setAlumno(alumno);

                //Se agregan las instancias inscripcion a la lista inscripciones para llevar un registro de todas las inscripciones recuperadas.
                inscripciones.add(inscripcion);
            }
            cerrarRecursos(ps, resultSet);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a  la tabla de inscripciones " + ex.getMessage());
        }
        return inscripciones; //la lista inscripciones se devuelve como resultado de la función.
    }

    public List<Inscripcion> obtenerInscripcionesPorAlumno(int id) {
        con = Conexion.getConexion();
        ArrayList<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT idInscripto, idMateria FROM inscripcion WHERE idAlumno = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(resultSet.getInt("idInscripto"));
                Materia materia = matdata.buscarMateria(resultSet.getInt("idMateria"));
                Alumno alumno = aludata.buscarAlumnoPorDni(resultSet.getInt("idAlumno"));
                inscripcion.setMateria(materia);
                inscripcion.setAlumno(alumno);

                inscripciones.add(inscripcion);
            }
            cerrarRecursos(ps, resultSet);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al recuperar inscripciones por Id de alumno" + ex.getMessage());
        }
        return inscripciones;
    }

    // Método para obtener todas las materias cursadas con el ID Alumno especificado
    public List<Materia> obtenerMateriasCursadas(int id) {
        con = Conexion.getConexion();
        List<Materia> materiasCursadas = new ArrayList<>();
        String sql = "SELECT inscripcion.idMateria, materia.nombre, materia.año FROM inscripcion,materia WHERE inscripcion.idMateria=materia.idMateria AND inscripcion.idAlumno = ? AND materia.estado=1";
        // JOIN entre las tablas inscripcion y materia buscando por idAlumno         
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(resultSet.getInt("idMateria"));
                materia.setNombre(resultSet.getString("nombre"));
                materia.setAnioMateria(resultSet.getInt("año"));
                materiasCursadas.add(materia);

            }
            cerrarRecursos(ps, resultSet);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se encontraron materias cursadas " + ex.getMessage());
        }
        return materiasCursadas;
    }

    // Método para obtener todas las materias no cursadas con idAlumnoespecificado 
    //a consulta se enfoca en la tabla "materia" y busca las materias que no están en la lista de inscripciones del alumno.
    public List<Materia> obtenerMateriasNoCursadas(int id) {
        con = Conexion.getConexion();
        ArrayList<Materia> materiasNoCursadas = new ArrayList<>();
        String sql = "SELECT materia.idMateria, nombre, año FROM materia "
                + "WHERE materia.idMateria NOT IN (SELECT inscripcion.idMateria FROM inscripcion"
                + " WHERE inscripcion.idAlumno = ? AND materia.estado=1)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(resultSet.getInt("idMateria"));
                materia.setNombre(resultSet.getString("nombre"));
                materia.setAnioMateria(resultSet.getInt("año"));
                materiasNoCursadas.add(materia);
            }
            cerrarRecursos(ps, resultSet);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener materias no cursadas: " + ex.getMessage());
        }
        return materiasNoCursadas;
    }

    public void borrarInscripcionMateriaAlumno(int idAlumno, int idMateria) {
        con = Conexion.getConexion();
        try {
            String sql = "DELETE FROM inscripcion WHERE idAlumno = ? AND idMateria = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idAlumno);
                ps.setInt(2, idMateria);

                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión no se pudo borrar el registro " + ex.getMessage());
        }
    }

    public void actualizarNota(int idAlumno, int idMateria, double nota) {
        con = Conexion.getConexion();
        try {

            String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno = ? AND idMateria = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            {
                ps.setDouble(1, nota);
                ps.setInt(2, idAlumno);
                ps.setInt(3, idMateria);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error no se pudo actualizar el alumno " + ex.getMessage());
        }
    }

// Método para obtener una lista de alumnos inscritos en una materia específica
    public List<Alumno> obtenerAlumnosxMateria(int idMateria) {
        con = Conexion.getConexion();
        List<Alumno> alumnosInscritos = new ArrayList<>();
        try {

            String sql = "SELECT alumno.idAlumno, alumno.nombre, alumno.dni, alumno.apellido FROM Alumno JOIN inscripcion ON alumno.idAlumno = inscripcion.idAlumno WHERE inscripcion.idMateria = ? AND alumno.estado=1";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMateria);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(resultSet.getInt("idAlumno"));
                alumno.setNombre(resultSet.getString("nombre"));
                alumno.setDni(resultSet.getInt("dni"));
                alumno.setApellido(resultSet.getString("apellido"));
                alumnosInscritos.add(alumno);
            }
            cerrarRecursos(ps, resultSet);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error no se pudo obtener la materia del alumno solicitado " + ex.getMessage());// Manejo de excepciones
        }
        return alumnosInscritos;
    }

    // metodo auxiliar para cerrar tanto resultSet como prepare statement
    private void cerrarRecursos(PreparedStatement ps, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (ps != null) {
            ps.close();
        }
    }

    public Object notadeMateria(int idAlumno, int idMateria) {
        int Nota = 0;
        try {
            con = Conexion.getConexion();
            String sql = "SELECT * FROM inscripcion WHERE idAlumno=? AND idMateria=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Nota = resultSet.getInt("nota");
            }

        } catch (SQLException ex) {
            Logger.getLogger(InscripcionData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (Object) Nota;

    }
}
