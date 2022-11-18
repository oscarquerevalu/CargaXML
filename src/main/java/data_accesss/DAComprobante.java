/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_accesss;

import com.google.gson.Gson;
import conexion.OperacionSqlServer;
import model.Comprobante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author OQUEREVALU
 */
public class DAComprobante {
    OperacionSqlServer operacion3;
    String jsonRespuesta="";
    Gson gson =  new Gson();
    int total;
    
    public DAComprobante(String cadena_conexion){
        operacion3=new OperacionSqlServer(cadena_conexion);	
    }
    

    public Comprobante Insertar(String consultaObjetos, Object condiciones[]){
        Comprobante comprobante= new Comprobante();
        try {
            ResultSet rsCombo=operacion3.EjecutarConsulta(consultaObjetos, condiciones);
            while (rsCombo!=null&&rsCombo.next()){
                comprobante=
                new Comprobante(
                        rsCombo.getString("id"),
                        rsCombo.getString("supPartyName"),
                        rsCombo.getString("supPartyRegName"),
                        rsCombo.getString("nombreTienda"),
                        rsCombo.getString("codigoMall"),
                        rsCombo.getString("codigoTienda"),
                        rsCombo.getString("rucEmisor"),
                        rsCombo.getString("identificadorTerminal"),
                        rsCombo.getString("numeroTerminal"),
                        rsCombo.getString("serie"),
                        rsCombo.getString("tipoTransaccion"),
                        rsCombo.getString("numeroTransaccion"),
                        rsCombo.getString("fecha"),
                        rsCombo.getString("hora"),
                        rsCombo.getString("cajero"),
                        rsCombo.getString("vendedor"),
                        rsCombo.getString("cusPartyId"),
                        rsCombo.getString("cusPartyRegName"),
                        rsCombo.getString("cusPartyRegAddrId"),
                        rsCombo.getString("cusPartyRegAddrTypeCode"),
                        rsCombo.getString("cusPartyRegAddrLine"),
                        rsCombo.getString("cusPartyRegAddrCountry"),
                        rsCombo.getString("bonus"),
                        rsCombo.getString("moneda"),
                        rsCombo.getString("medioPago"),
                        rsCombo.getString("totalValorVentaBruta"),
                        rsCombo.getString("cargosDescuentosGlobal"),
                        rsCombo.getString("montoTotalIGV"),
                        rsCombo.getString("totalValorVentaNeta"),
                        rsCombo.getString("numeroOrdenItem"),
                        rsCombo.getString("cantidadUnidadesItem"),
                        rsCombo.getString("codigoProducto"),
                        rsCombo.getString("descripcionProducto"),
                        rsCombo.getString("precioVentaUnitarioItem"),
                        rsCombo.getString("precioTotalItem"))
                            ;
                }
            if(rsCombo!=null){rsCombo.close();}
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            comprobante=null;
        }
        operacion3.cerrarConexion();
        return comprobante;
    }
    
    public ArrayList<Comprobante> listar(String consultaObjetos,Object condiciones[]){
        ArrayList<Comprobante> lista=new ArrayList<>();
        try {
            ResultSet rsCombo = operacion3.EjecutarConsulta(consultaObjetos,condiciones);
            while (rsCombo!=null&&rsCombo.next()){
                lista.add(
                        new Comprobante(rsCombo.getString("ID"))
                        );
            }
            if(rsCombo!=null){rsCombo.close();}
            operacion3.cerrarConexion();
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            operacion3.cerrarConexion();
            return lista;
        }
    }
}
