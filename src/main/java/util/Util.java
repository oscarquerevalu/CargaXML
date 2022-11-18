/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.*;
import java.util.Properties;

/**
 *
 * @author OQUEREVALU
 */
public class Util {
    
    public Propiedades leerPropiedades(){
        Propiedades registro=new Propiedades();
        Properties prop = new Properties();
        try (InputStream inputStream = new FileInputStream("consulta.properties")) {
            prop.load(inputStream);
            String cadena_conexion=prop.getProperty("cadena_conexion");
            System.out.println(cadena_conexion);
            registro=new Propiedades(cadena_conexion);
        } catch (IOException io) {
            io.printStackTrace();
        }
        return registro;
    }
    
    public void escribirPropiedades(Propiedades registro){
        Properties prop = new Properties();
        try (OutputStream output = new FileOutputStream("consulta.properties")) {
            prop.setProperty("cadena_conexion", registro.getCadena_conexion());
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
