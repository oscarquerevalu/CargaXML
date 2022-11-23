
package conexion;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectSqlServer {
    Connection conexion;
    private static Logger log = Logger.getLogger(ConnectSqlServer.class);
    
    public ConnectSqlServer(String cadena_conexion){
	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String url = cadena_conexion;
	try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url);
	} catch (Exception exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexion con la BD");
            log.error(exc);
	}
    }

    public Connection getConexion() {
        return conexion;
    }
}