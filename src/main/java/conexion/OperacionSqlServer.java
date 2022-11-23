package conexion;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;

public class OperacionSqlServer
{
    private static Logger log = Logger.getLogger(OperacionSqlServer.class);
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
            log.error(ex);
        }
    }

    public ResultSet EjecutarConsulta(String query,Object condiciones[]) throws SQLException
    {
        CallableStatement  consulta= conexion.prepareCall(query);
        log.info(query);
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
                log.info("string: cadena vacia");
            }else{
                if(argumentos[i].getClass()==Integer.class){
                    consulta.setInt(i+1, Integer.parseInt(argumentos[i].toString()));
                    log.info("int: "+argumentos[i].toString());
                }else{
                    if(argumentos[i].getClass()==Float.class){
                        consulta.setFloat(i+1, Float.parseFloat(argumentos[i].toString()));
                        log.info("float: "+argumentos[i].toString());
                    }else{
                        if(argumentos[i].getClass()==String.class){
                            log.info(argumentos[i].toString().length());
                            consulta.setString(i+1, argumentos[i].toString());

                            log.info("string: "+argumentos[i].toString());
                        }else{
                            if(argumentos[i].getClass()==Double.class){
                                consulta.setDouble(i+1, Double.parseDouble(argumentos[i].toString()));
                                log.info("double: "+argumentos[i].toString());
                            }else{
                                byte[] data =new byte[0];
                                if(argumentos[i].getClass().equals(data.getClass())){
                                    ObjectOutputStream os = null;
                                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                                    try {
                                        os = new ObjectOutputStream(out);
                                        os.writeObject( argumentos[i]);
                                    } catch (IOException ex) {
                                        log.info(ex);
                                    }
                                    consulta.setBytes(i+1, out.toByteArray());
                                    log.info("byte array");

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