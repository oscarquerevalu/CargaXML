/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_logic;

import com.google.gson.Gson;
import data_accesss.DAComprobante;
import model.Comprobante;
import org.apache.log4j.Logger;
import util.Propiedades;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author OQUEREVALU
 */
public class BLComprobante {

    private static Logger log = Logger.getLogger(BLComprobante.class);
    
    Gson gson =  new Gson();

    public BLComprobante() {
    }
    
    public Comprobante registrar(Propiedades propiedades, Comprobante comprobante){
                        Object condiciones[]={    comprobante.getId(),
                                comprobante.getSupPartyName(),
                                comprobante.getSupPartyRegName(),
                                comprobante.getNombreTienda(),
                                comprobante.getCodigoMall(),
                                comprobante.getCodigoTienda(),
                                comprobante.getRucEmisor(),
                                comprobante.getIdentificadorTerminal(),
                                comprobante.getNumeroTerminal(),
                                comprobante.getSerie(),
                                comprobante.getTipoTransaccion(),
                                comprobante.getNumeroTransaccion(),
                                comprobante.getFecha(),
                                comprobante.getHora(),
                                comprobante.getCajero(),
                                comprobante.getVendedor(),
                                comprobante.getCusPartyId(),
                                comprobante.getCusPartyRegName(),
                                comprobante.getCusPartyRegAddrId(),
                                comprobante.getCusPartyRegAddrTypeCode(),
                                comprobante.getCusPartyRegAddrLine(),
                                comprobante.getCusPartyRegAddrCountry(),
                                comprobante.getBonus(),
                                comprobante.getMoneda(),
                                comprobante.getMedioPago(),
                                comprobante.getTotalValorVentaBruta(),
                                comprobante.getCargosDescuentosGlobal(),
                                comprobante.getMontoTotalIGV(),
                                comprobante.getTotalValorVentaNeta(),
                                comprobante.getNumeroOrdenItem(),
                                comprobante.getCantidadUnidadesItem(),
                                comprobante.getCodigoProducto(),
                                comprobante.getDescripcionProducto(),
                                comprobante.getPrecioVentaUnitarioItem(),
                                comprobante.getPrecioTotalItem(),
                            };
        Comprobante registro=new DAComprobante(propiedades.getCadena_conexion()).Insertar("INSERT INTO Comprobantes\n" +
                "           (ID, supPartyName, supPartyRegName, nombreTienda, codigoMall, codigoTienda, rucEmisor, identificadorTerminal, numeroTerminal, serie, tipoTransaccion, numeroTransaccion, fecha, hora, cajero, vendedor, cusPartyId,\n" +
                "    cusPartyRegName, cusPartyRegAddrId, cusPartyRegAddrTypeCode, cusPartyRegAddrLine, cusPartyRegAddrCountry, bonus, moneda, medioPago, totalValorVentaBruta, cargosDescuentosGlobal, montoTotalIGV, totalValorVentaNeta,  numeroOrdenItem, cantidadUnidadesItem, codigoProducto, descripcionProducto, precioVentaUnitarioItem, precioTotalItem)\n" +
                "     VALUES\n" +
                "           (?, ? ,? ,? ,? ,? ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?)", condiciones);
        return registro;
    }

    
    public Comprobante obtenerComprobante(Propiedades propiedades,String id){
        Object condiciones[]={id};
        DAComprobante da= new DAComprobante(propiedades.getCadena_conexion());
        log.info(propiedades.getCadena_conexion());
        ArrayList<Comprobante> lista=new ArrayList<Comprobante>();
        Comprobante Comprobante=new Comprobante();
        lista = da.listar(
                "select ID from Comprobantes where ID = ?", condiciones);
        if(lista.isEmpty()){
            log.info("vacio");
            log.info(id);
            Comprobante.setId(null);

        }else{
            Comprobante=lista.get(0);
        }
        return Comprobante;
    }
}
