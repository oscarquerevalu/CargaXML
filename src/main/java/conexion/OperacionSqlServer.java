package conexion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperacionSqlServer
{
    Connection conexion;
            
    public OperacionSqlServer(String cadena_conexion)
    {
        this.conexion=new ConnectSqlServer(cadena_conexion).getConexion();
    }
    
    public void cerrarConexion()
    {
        try {
            this.conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(OperacionSqlServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet EjecutarConsulta(String query,Object condiciones[]) throws SQLException
    {
        CallableStatement  consulta= conexion.prepareCall(query);
        System.out.println(query);
        llenarConsultaConParametros(consulta,condiciones);
        consulta.execute();
        ResultSet rs = consulta.getResultSet();
        consulta.clearParameters();
        return rs;
    }

    public ResultSet EjecutarConsulta2(String query,Object condiciones[]) throws SQLException
    {
        CallableStatement  consulta= conexion.prepareCall(query);
        llenarConsultaConParametros(consulta,condiciones);
        consulta.execute();
        ResultSet rs = consulta.getGeneratedKeys();

        consulta.clearParameters();
        return rs;
    }


    public void llenarConsultaConParametros(PreparedStatement consulta,Object argumentos[]) throws SQLException
    {
        for(int i=0;i<argumentos.length;i++)
        {
            if(argumentos[i]==null){
                consulta.setString(i+1, "");
                System.out.println("string: cadena vacia");
            }else{
                if(argumentos[i].getClass()==Integer.class){
                    consulta.setInt(i+1, Integer.parseInt(argumentos[i].toString()));
                    System.out.println("int: "+argumentos[i].toString());
                }else{
                    if(argumentos[i].getClass()==Float.class){
                        consulta.setFloat(i+1, Float.parseFloat(argumentos[i].toString()));
                        System.out.println("float: "+argumentos[i].toString());
                    }else{
                        if(argumentos[i].getClass()==String.class){
                            System.out.println(argumentos[i].toString().length());
                            consulta.setString(i+1, argumentos[i].toString());

                            System.out.println("string: "+argumentos[i].toString());
                        }else{
                            if(argumentos[i].getClass()==Double.class){
                                consulta.setDouble(i+1, Double.parseDouble(argumentos[i].toString()));
                                System.out.println("double: "+argumentos[i].toString());
                            }else{
                                byte[] data =new byte[0];
                                if(argumentos[i].getClass().equals(data.getClass())){
                                    ObjectOutputStream os = null;
                                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                                    try {
                                        os = new ObjectOutputStream(out);
                                        os.writeObject( argumentos[i]);
                                    } catch (IOException ex) {
                                        System.out.println(ex);
                                    }
                                    consulta.setBytes(i+1, out.toByteArray());
                                    System.out.println("byte array");

                                }else{

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}