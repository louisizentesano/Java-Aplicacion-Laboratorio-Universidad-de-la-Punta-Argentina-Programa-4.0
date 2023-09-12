/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadejemplo.AccesoAdatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author PC1
 */
public class Conexion {

    private static final String URL = "jdbc:mariadb://localhost/";
    private static final String DB = "universidadulp";
    private static final String USUARIO = "dario";
    private static String PASSWORD = "Dario260983";

    private static Connection connection;

    //Metodo Constructor
    private Conexion() {
    }

    public static Connection getConexion() {

        if (connection == null) {

            try {
                Class.forName("org.mariadb.jdbc.Driver");
                //Setup the connection with the DB
                connection = DriverManager
                        .getConnection(URL + DB + "?useLegacyDatetimeCode=false&serverTimezone=UTC"
                                + "&user=" + USUARIO + "&password=" + PASSWORD);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al conectarse a la BD " + ex.getMessage());
            } catch (ClassNotFoundException ex) {

                JOptionPane.showMessageDialog(null, "Error al cargar los Drivers " + ex.getMessage());
            }
        }
        return connection;
        }

}
