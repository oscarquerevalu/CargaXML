/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author OQUEREVALU
 */
public class Propiedades {
    String cadena_conexion;
    String ftp_ipServer;
    String ftp_user;
    String ftp_pass;
    int nro_dias;

    public Propiedades() {
    }

    public Propiedades(String cadena_conexion, String ftp_ipServer, String ftp_user, String ftp_pass, int nro_dias) {
        this.cadena_conexion = cadena_conexion;
        this.ftp_ipServer = ftp_ipServer;
        this.ftp_user = ftp_user;
        this.ftp_pass = ftp_pass;
        this.nro_dias = nro_dias;
    }

    public String getCadena_conexion() {
        return cadena_conexion;
    }

    public void setCadena_conexion(String cadena_conexion) {
        this.cadena_conexion = cadena_conexion;
    }

    public String getFtp_ipServer() {
        return ftp_ipServer;
    }

    public void setFtp_ipServer(String ftp_ipServer) {
        this.ftp_ipServer = ftp_ipServer;
    }

    public String getFtp_user() {
        return ftp_user;
    }

    public void setFtp_user(String ftp_user) {
        this.ftp_user = ftp_user;
    }

    public String getFtp_pass() {
        return ftp_pass;
    }

    public void setFtp_pass(String ftp_pass) {
        this.ftp_pass = ftp_pass;
    }

    public int getNro_dias() {
        return nro_dias;
    }

    public void setNro_dias(int nro_dias) {
        this.nro_dias = nro_dias;
    }
}
