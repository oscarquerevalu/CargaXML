
package conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectSqlServer {
    Connection conexion;
    
    public ConnectSqlServer(String cadena_conexion){
	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String url = cadena_conexion;
	try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url);
	} catch (Exception exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexion con la BD");
            System.out.println(exc);
	}
    }

    public Connection getConexion() {
        return conexion;
    }
}