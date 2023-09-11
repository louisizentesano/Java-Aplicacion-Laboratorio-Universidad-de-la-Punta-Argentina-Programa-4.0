package universidadejemplo.AccesoAdatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import universidadejemplo.Entidades.Materia;

public class MateriaData {
    private Connection conexion;

    public MateriaData() {
            }

    public void guardarMateria(Materia materia) {
        try {
            String sql = "INSERT INTO materia (nombre, año, estado) VALUES (?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, materia.getNombre());
            statement.setInt(2, materia.getAnio());
            statement.setInt(3, materia.getEstado());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Materia buscarMateria(int id) {
        Materia materia = null;
        try {
            String sql = "SELECT * FROM materia WHERE idMateria = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                materia = new Materia(
                    resultSet.getInt("idMateria"),
                    resultSet.getString("nombre"),
                    resultSet.getInt("año"),
                    boolean estado = resultSet.getBoolean("estado");
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materia;
    }

    public void modificarMateria(Materia materia) {
        try {
            String sql = "UPDATE materia SET nombre = ?, año = ?, estado = ? WHERE idMateria = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, materia.getNombre());
            statement.setInt(2, materia.getAnio());
            statement.setInt(3, materia.getEstado());
            statement.setInt(4, materia.getIdMateria());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarMateria(int id) {
        try {
            String sql = "DELETE FROM materia WHERE idMateria = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Materia> listarMaterias() {
        List<Materia> materias = new ArrayList<>();
        try {
            String sql = "SELECT * FROM materia";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Materia materia = new Materia(
                    resultSet.getInt("idMateria"),
                    resultSet.getString("nombre"),
                    resultSet.getInt("año"),
                    resultSet.getInt("estado")
                );
                materias.add(materia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materias;
    }

    // Cierra la conexión
    public void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
