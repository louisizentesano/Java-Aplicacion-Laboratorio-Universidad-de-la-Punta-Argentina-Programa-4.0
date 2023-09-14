package universidadejemplo.AccesoAdatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Entidades.Inscripcion;
import universidadejemplo.Entidades.Materia;

/**
 * louisinette
 */
public class InscripcionData {

    private Connection con = null;
    private MateriaData matdata;
    private AlumnoData aludata;

    public InscripcionData() {
           }

    public void guardarInscripcion(Inscripcion insc) {
         con = Conexion.getConexion();
        try {
            String sql = "INSERT INTO inscripcion (idInscripto, nota, idAlumno, idMateria) VALUES (?, ?, ?, ?)";
            //El valor del parámetro insc en la siguiente consulta SQL proviene del argumento que se pasa al método
            //guardarInscripcion(Inscripcion insc)
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                //El valor del parámetro insc en la siguiente consulta SQL proviene del argumento que se pasa al método
                //guardarInscripcion(Inscripcion insc)
                ps.setInt(1, insc.getIdInscripcion());//idInscripto??
                ps.setDouble(2, insc.getNota());
                ps.setInt(3, insc.getAlumno().getIdAlumno());
                ps.setInt(4, insc.getMateria().getIdMateria());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    insc.setIdInscripcion(rs.getInt("idInscripto"));
                    JOptionPane.showMessageDialog(null, "Inscripcion exitosa");
                }
            } //idInscripto??

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error no se pudo realizar la inscripcion " + ex.getMessage());
        }
    }

    public List<Inscripcion> obtenerInscripciones() {
         con = Conexion.getConexion();
        //Se crea una lista vacía llamada inscripciones para almacenar los objetos Inscripcion que se recuperarán de la base de datos.
        List<Inscripcion> inscripciones = new ArrayList<>();

        try {
            String sql = "SELECT * FROM inscripcion WHERE idInscripto IS NOT NULL";
            //Se define una consulta SQL que selecciona todas las columnas de la tabla inscripcion donde el estado tenga un valor
            PreparedStatement ps = con.prepareStatement(sql);
            //Se prepara una sentencia SQL utilizando la conexión a la base de datos (con) y la consulta SQL definida.
            ResultSet resultSet = ps.executeQuery();
            /**
             * Se ejecuta la consulta SQL y se obtiene un objeto ResultSet
             * llamado resultSet que contendrá los resultados de la consulta.
             * Este objeto ResultSet, dispone de varios métodos a través de los
             * cuales, entre otras cosas, podemos recorrer cada fila y obtener
             * un dato que se encuentra en una columna determinada de la fila
             * sobre la que se está posicionado
             */

            //se itera a través de las filas del conjunto de resultados (resultSet).
            ///si invocamos el método next() de este ResultSet, nos volverá true y se parará en la primer fila 
            while (resultSet.next()) {

                //Se establecen los valores del objeto Alumno a partir de los resultados de la consulta
                /**
                 * no me esta tomando los parametros del constructor de
                 * inscripcion me pide Inscripcion inscripcion = new
                 * Inscripcion(idInscripto,nota,alumno,materia); int idInscripto
                 * = resultSet.getInt("idInscripto"); double nota =
                 * resultSet.getDouble("nota"); int idAlumno =
                 * resultSet.getInt("idAlumno"); int idMateria =
                 * resultSet.getInt("idMateria");
                 */
                Inscripcion inscripcion = new Inscripcion(); //Para cada fila, se crea una instancia de la clase Inscripcion llamada inscripcion.
                inscripcion.setIdInscripcion(resultSet.getInt("idInscripto"));
                //Se establecen los atributos de la instancia inscripcion utilizando los valores de las columnas en la fila actual del resultado. 
                //Por ejemplo, setIdInscripcion() se utiliza para establecer el valor del atributo "idInscripcion" de la instancia inscripcion a partir 
                //del valor en la columna "idInscripto" del resultado.
                inscripcion.setNota(resultSet.getDouble("Nota"));
                inscripcion.setMateria((Materia) resultSet.getObject("idMateria"));
                inscripcion.setAlumno((Alumno) resultSet.getObject("idAlumno"));

                // Se agrega el objeto inscripcion a la lista inscripciones
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
        List<Inscripcion> inscripciones = new ArrayList<>();

        try {
            String sql = "SELECT idInscripto, idMateria, FROM inscripcion WHERE idAlumno = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(resultSet.getInt("idInscripto"));
                int idMateria = resultSet.getInt("idMateria");

                Materia materia = new Materia();
                Alumno alumno = new Alumno();

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

    // Método para obtener todas las materias cursadas con el ID especificado
    public List<Materia> obtenerMateriasCursadas(int id) {
         con = Conexion.getConexion();
        List<Materia> materias = new ArrayList<>();
        try {
            String sql = "SELECT inscripcion.idMateria,nombre,año FROM inscripcion,materia"
                    + "WHERE inscripcion.idMateria=materia.idMateria "
                    + " AND inscripcion.idAlumno = ?";
            //JOIN entre las tablas inscripcion y materia en función del idMateria y idAlumno         
            // si llega a haber algun error de consulta probar:
            //String sql = "SELECT materia.idMateria,nombre,año FROM inscripcion, "
            //        + "materia WHERE inscripcion.idMateria=materia.idMateria "
            //        + " AND inscripcion.idAlumno = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(resultSet.getInt("idMateria"));
                materia.setNombre(resultSet.getString("nombre"));
                materia.setAnioMateria(resultSet.getInt("año"));
                materias.add(materia);
            }
            cerrarRecursos(ps, resultSet);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se encontraron materias cursadas " + ex.getMessage());
        }
        return materias;
    }

    // Método para obtener todas las materias no cursadas con id especificado 
    //a consulta se enfoca en la tabla "materia" y busca las materias que no están en la lista de inscripciones del alumno.
    public List<Materia> obtenerMateriasNoCursadas(int id) {
         con = Conexion.getConexion();
        List<Materia> materiasNoCursadas = new ArrayList<>();
        try {
            String sql = "SELECT materia.idMateria, nombre, año FROM materia "
                    + "WHERE materia.idMateria NOT IN (SELECT inscripcion.idMateria FROM inscripcion"
                    + " WHERE inscripcion.idAlumno = ?)";

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

//otra forma materias cursadas:    
//public InscripcionData(MateriaData matdata) {
// this.matdata = matdata;
// }
//Iterable<Inscripcion> inscripciones = null;
//         // tengo una lista de inscripciones con  objetos Inscripcion
//        // Itero sobre las inscripciones y verifico si el alumno está inscrito en una materia
//        for (Inscripcion inscripcion : inscripciones) {
//            if (inscripcion.getAlumno().getId() == id) {
//                // Si el alumno está inscrito en una materia, obtener la materia utilizando el método de MateriaData.
//         List<Materia> materiasinsc = matdata.listarMaterias();
//                for (Materia materia : materias) {
//                    if (materia.getId() == inscripcion.getMateria().getId()) {
//                        materias.add(materia);
//                        break;  //Termina el bucle al encontrar la materia.
//                    }
//                }
//            }
//        }
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

    //  ///borrado logico bajas lógicas, es decir, este campo tomará valor “false” cuando el alumno esté de baja  
//    //uso método público setActivo(boolean estado) de ls clases Alumno y Materia para cambiar el estado  si tambien
//    //tuviese en inscripcionData boolean
//    public void borrarInscripcionMateriaAlumno(int idAlumno, int IdMateria) {
//
//        try {
//        Alumno alumno = buscarAlumno(idAlumno);
//        Materia materia = buscarMateria(idMateria);
//                
//             if (alumno != null && materia != null) {
//            alumno.setActivo(false);
//            materia.setActivo(false);
//            
//          String sql = "UPDATE inscripcion SET estado = false WHERE idAlumno = ? AND idMateria = ?";
//           PreparedStatement ps = con.prepareStatement(sql);
//              
//            
//                ps.setInt(1, IdAlumno);
//                ps.setInt(2, IdMateria);
//                
//                ps.executeUpdate();
//                
//            }else{ JOptionPane.showMessageDialog(null, "El alumno o la materia no existen y no hay inscripcion que borrar");
//        } 
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Error no se pudo acceder a la BD para borrar el registro" + ex.getMessage());
//        }
//        }
    public void actualizarNota(int idAlumno, int idMateria, double nota) {
         con = Conexion.getConexion();
        try {

            String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno = ? OR idMateria = ?";
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

            String sql = "SELECT alumno.idAlumno, alumno.nombre, alumno.apellido FROM Alumno JOIN inscripcion ON alumno.idAlumno = inscripcion.idAlumno WHERE inscripcion.idMateria = ? ";
                  
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMateria);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(resultSet.getInt("idAlumno"));
                alumno.setNombre(resultSet.getString("nombre"));
                alumno.setApellido(resultSet.getString("apellido"));
                // Establece otros campos del objeto Alumno según tu modelo
                alumnosInscritos.add(alumno);
            }

            cerrarRecursos(ps, resultSet);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error no se pudo obtener la materia del alumno solicitado " + ex.getMessage());// Manejo de excepciones
        }
        return alumnosInscritos;
    }

    /**
     * nota:* el uso del bloque try-with-resources 2do try, que asegura que el
     * recurso (PreparedStatement en este caso) se cierre automáticamente al
     * finalizar el bloque, independientemente de si ocurre una excepción o no.
     * Esto es una buena práctica en Java para gestionar recursos y evitar fugas
     * de recursos no es necesario cerrar manualmente el PreparedStatement con
     * ps.close()..
     */
    // metodo auxiliar para cerrar tanto resultSet como prepare statement
    private void cerrarRecursos(PreparedStatement ps, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (ps != null) {
            ps.close();
        }
    }
}
