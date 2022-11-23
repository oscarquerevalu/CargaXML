/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 *
 * @author OQUEREVALU
 */
public class Util {
    private static Logger log = Logger.getLogger(PropertyValues.class);
    
    public Propiedades leerPropiedades(){
        Propiedades registro=new Propiedades();
        Properties prop = new Properties();
        try (InputStream inputStream = new FileInputStream("consulta.properties")) {
            prop.load(inputStream);
            String cadena_conexion=prop.getProperty("cadena_conexion");
            String ftp_ipServer=prop.getProperty("ftp_ipServer");
            String ftp_user=prop.getProperty("ftp_user");
            String ftp_pass=prop.getProperty("ftp_pass");
            int nro_dias= Integer.parseInt(prop.getProperty("nro_dias"));

            log.info(cadena_conexion);
            registro=new Propiedades(cadena_conexion, ftp_ipServer, ftp_user, ftp_pass, nro_dias);
        } catch (IOException io) {
            io.printStackTrace();
            log.error(io);
        }
        return registro;
    }
    
    public void escribirPropiedades(Propiedades registro){
        Properties prop = new Properties();
        try (OutputStream output = new FileOutputStream("consulta.properties")) {
            prop.setProperty("cadena_conexion", registro.getCadena_conexion());
            prop.setProperty("ftp_ipServer", registro.getFtp_ipServer());
            prop.setProperty("ftp_user", registro.getFtp_user());
            prop.setProperty("ftp_pass", registro.getFtp_pass());
            prop.setProperty("nro_dias", String.valueOf(registro.getNro_dias()));
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
            log.error(io);
        }
    }
}
