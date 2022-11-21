import business_logic.BLComprobante;
import model.Comprobante;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import util.Propiedades;
import util.Util;

import java.io.*;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.commons.net.ftp.FTPClient;
/**
 *
 * @author OQUEREVALU
 */
public class CargaXML {
    private static Logger log = Logger.getLogger(CargaXML.class);
    private static String ip = "127.0.0.1";
    private static String user = "TestUser";
    private static String pass = "123456";
    private static String localFileFtp = "D:\\shared\\SRV";
    //1011user01\\DATA-SFTP\\
    public static void main(String[] args) throws IOException {

        File file = new File(localFileFtp);
        conectar(ip,user,pass);
        for (String string : file.list()){
            log.info(string);
            leerArchivo(localFileFtp + "\\" + string);
        }

    }
    
    public static void conectar(String ip, String user, String pass) throws SocketException, IOException{
       FTPClient ftp = new FTPClient();
        ftp.connect(ip);

        if(ftp.login(user, pass))
            System.out.println("login OK");
        else
            System.out.println("login Error");
    }

    public static void leerArchivo(String ruta) throws IOException {
        // crea el flujo para leer desde el archivo
        File file = new File(ruta);
        Path p = Paths.get(file.getAbsolutePath());
        BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);

//        log.info("creationTime: " + attr.creationTime());
//        log.info("lastAccessTime: " + attr.lastAccessTime());
        log.info("lastModifiedTime: " + attr.lastModifiedTime());

        log.info("isDirectory: " + attr.isDirectory());
//        log.info("isOther: " + attr.isOther());
//        log.info("isRegularFile: " + attr.isRegularFile());
//        log.info("isSymbolicLink: " + attr.isSymbolicLink());
//        log.info("size: " + attr.size());

        if(attr.isDirectory()){
            for (String string : file.list()){
                log.info(string);
                String emp_data2 = "";
                if(string.contains("user"))
                    emp_data2 = string;
                leerArchivo(ruta+"\\"+string);
            }
        }else{

            if(ruta.toUpperCase().contains(".XML")){
                String content = readFile(ruta, StandardCharsets.UTF_8);
                //log.info(content);
                String[] emp_array = ruta.split("\\\\");
                String emp_data="";
                for (int i = 0; i < emp_array.length; i++) {
                    String path = emp_array[i];
                    if(path.contains("user")){
                        emp_data=path;
                    }
                }

                try {
                    JSONObject json = XML.toJSONObject(content);
                    //String jsonString = json.toString(4);
                    cargaComprobante(json, emp_data);

                }catch (JSONException e) {
                    log.error("Error JSON:");
                    log.error(e);
                    try {
                        content = content.replace("</cac:AccountingSupplierParty>","</cac:AccountingSupplierParty><cac:AccountingCustomerParty>");
                        //log.info(content);
                        JSONObject json = XML.toJSONObject(content);
                        String jsonString = json.toString(4);
                        //log.info(jsonString);
                        cargaComprobante(json, emp_data);
                    }catch (JSONException ex){
                        log.error("Error JSON2:");
                        log.error(e);
                    }
                }
            }
        }
    }

    static void cargaComprobante(JSONObject json, String emp_data){
        Comprobante comprobante = new Comprobante();
        JSONObject invoice = new JSONObject();
        Propiedades archivo_propiedades;

        File propiedades=new File("consulta.properties");

        if(!propiedades.exists()){
            archivo_propiedades=new Propiedades(
                    "jdbc:sqlserver://localhost;encrypt=false;databaseName=FMEDITERRANEO;user=sa; password=P@ssword2019;"
            );
            new Util().escribirPropiedades(archivo_propiedades);
        }else{
            archivo_propiedades=new Util().leerPropiedades();
        }
        try {
            invoice = json.getJSONObject("Invoice");

        }catch (JSONException e){
            invoice = json.getJSONObject("CreditNote");
        }

        try{
            log.info("=========================================");
            String[] emp_array = emp_data.split("user");
            if(emp_array.length == 2){
                comprobante.setCodigoTienda(emp_array[0]);
                comprobante.setCodigoMall(emp_array[1]);
            }


            log.info("TipoTransaccion:"+(invoice.toString().contains("CreditNote")?"01":"03"));
            comprobante.setTipoTransaccion(invoice.toString().contains("CreditNote")?"01":"03");

            JSONObject accountingSupplierParty = invoice.getJSONObject("cac:AccountingSupplierParty");
            JSONObject party = accountingSupplierParty.getJSONObject("cac:Party");
            JSONObject partyLegalEntity = party.getJSONObject("cac:PartyLegalEntity");
            log.info("SupPartyRegName:"+partyLegalEntity.get("cbc:RegistrationName"));
            comprobante.setSupPartyRegName(partyLegalEntity.get("cbc:RegistrationName")!=null?partyLegalEntity.get("cbc:RegistrationName").toString():"");

            try{
                JSONObject partyName = party.getJSONObject("cac:PartyName");
                log.info("SupPartyName:"+partyName.get("cbc:Name"));
                comprobante.setSupPartyName(partyName.get("cbc:Name")!=null?partyName.get("cbc:Name").toString():"");
            }catch (JSONException exp){}


            JSONObject partyIdentification = party.getJSONObject("cac:PartyIdentification");
            JSONObject id = partyIdentification.getJSONObject("cbc:ID");
            log.info("RucEmisor:"+id.get("content"));
            comprobante.setRucEmisor(id.get("content")!=null?id.get("content").toString():"");

            String[] serie = invoice.getString("cbc:ID").split("-");
            log.info("Serie:"+(serie[0]!=null?serie[0]:""));
            comprobante.setSerie((serie[0]!=null?serie[0]:""));

            log.info("NumeroTransaccion:"+(serie[1]!=null?serie[1]:""));
            comprobante.setNumeroTransaccion((serie[1]!=null?serie[1]:""));

            comprobante.setId(comprobante.getTipoTransaccion()+"-"+comprobante.getRucEmisor()+"-"+invoice.getString("cbc:ID"));
            Comprobante existeComprobante = new BLComprobante().obtenerComprobante(archivo_propiedades, comprobante.getId());
            if(existeComprobante.getId() != null){
                log.info("Comprobante:"+existeComprobante.getId());
                log.info("Comprobante:"+existeComprobante);
                return;
            }

            String issueDate = invoice.getString("cbc:IssueDate");
            String issueTime = invoice.getString("cbc:IssueTime");
            log.info("Fecha:"+issueDate);
            comprobante.setFecha(issueDate);
            log.info("Hora:"+issueTime);
            comprobante.setHora(issueTime);

            //log.info(invoice);

            JSONObject accountingCustomerParty_partyIdentification = party.getJSONObject("cac:PartyIdentification");
            JSONObject accountingCustomerParty_id = accountingCustomerParty_partyIdentification.getJSONObject("cbc:ID");
            log.info("CusPartyId:"+accountingCustomerParty_id.get("content"));
            comprobante.setCusPartyId(accountingCustomerParty_id.get("content")!=null?accountingCustomerParty_id.get("content").toString():"");

            JSONObject accountingCustomerParty_partyLegalEntity = party.getJSONObject("cac:PartyLegalEntity");
            log.info("CusPartyRegName:"+accountingCustomerParty_partyLegalEntity.get("cbc:RegistrationName"));
            comprobante.setCusPartyRegName(accountingCustomerParty_partyLegalEntity.get("cbc:RegistrationName")!=null?accountingCustomerParty_partyLegalEntity.get("cbc:RegistrationName").toString():"");

            JSONObject taxTotal = invoice.getJSONObject("cac:TaxTotal");
            JSONObject taxAmount = taxTotal.getJSONObject("cbc:TaxAmount");
            log.info("MontoTotalIGV:"+taxAmount.get("content"));
            comprobante.setMontoTotalIGV(taxAmount.get("content")!=null?taxAmount.get("content").toString():"");

            if("03".equals(comprobante.getTipoTransaccion())){
                JSONObject legalMonetaryTotal = invoice.getJSONObject("cac:LegalMonetaryTotal");
                JSONObject taxInclusiveAmount = legalMonetaryTotal.getJSONObject("cbc:TaxInclusiveAmount");
                log.info("TotalValorVentaBruta:"+taxInclusiveAmount.get("content"));
                comprobante.setTotalValorVentaBruta(taxInclusiveAmount.get("content")!=null?taxInclusiveAmount.get("content").toString():"");

                JSONObject lineExtensionAmount = legalMonetaryTotal.getJSONObject("cbc:LineExtensionAmount");
                log.info("TotalValorVentaNeta:"+lineExtensionAmount.get("content"));
                comprobante.setTotalValorVentaNeta(lineExtensionAmount.get("content")!=null?lineExtensionAmount.get("content").toString():"");
            }else{
                JSONObject legalMonetaryTotal = invoice.getJSONObject("cac:LegalMonetaryTotal");
                JSONObject taxInclusiveAmount = legalMonetaryTotal.getJSONObject("cbc:PayableAmount");
                log.info("TotalValorVentaBruta:"+taxInclusiveAmount.get("content"));
                comprobante.setTotalValorVentaBruta(taxInclusiveAmount.get("content")!=null?taxInclusiveAmount.get("content").toString():"");

                try{
                    JSONObject taxSubtotal = taxTotal.getJSONObject("cac:TaxSubtotal");
                    JSONObject taxableAmount = taxSubtotal.getJSONObject("cbc:TaxableAmount");
                    log.info("TotalValorVentaNeta:"+taxableAmount.get("content"));
                    comprobante.setTotalValorVentaNeta(taxableAmount.get("content")!=null?taxableAmount.get("content").toString():"");
                }catch (JSONException exe){

                    //Puede ser una lista
                    JSONArray jsonArray = taxTotal.getJSONArray("cac:TaxSubtotal");
                    Double mount = 0.0;
                    //log.info("cac:TaxSubtotal:"+jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject taxSubtotal = jsonArray.getJSONObject(i);
                        //log.info("taxSubtotal:"+taxSubtotal);
                        JSONObject taxableAmount = taxSubtotal.getJSONObject("cbc:TaxableAmount");
                        //log.info("TotalValorVentaNeta:"+i+":"+taxableAmount.getDouble("content"));
                        mount = mount + taxableAmount.getDouble("content");
                    }
                    log.info("TotalValorVentaNeta:"+mount);
                    comprobante.setTotalValorVentaNeta(mount+"");

                }

            }

            try {

                JSONObject jsonObject;
                try {
                    jsonObject = invoice.getJSONObject("cac:InvoiceLine");

                }catch (JSONException e){
                    jsonObject = invoice.getJSONObject("cac:CreditNoteLine");
                }

                //log.info("invoiceLine:"+invoiceLine);
                log.info("****************_INVOICELINE_****************");
                log.info("NumeroOrdenItem:1");
                comprobante.setNumeroOrdenItem("1");
                cargaDetalleComprobante(comprobante, jsonObject);
                log.info("****************_INVOICELINE_****************");
                new BLComprobante().registrar(archivo_propiedades, comprobante);

            }catch (JSONException e){
                //Puede ser una lista
                JSONArray jsonArray;
                try {
                    jsonArray = invoice.getJSONArray("cac:InvoiceLine");

                }catch (JSONException ex){
                    jsonArray = invoice.getJSONArray("cac:CreditNoteLine");
                }
                //log.info("invoiceLine:"+jsonArray);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    log.info("****************_INVOICELINE_****************");
                    log.info("NumeroOrdenItem:"+i);
                    comprobante.setNumeroOrdenItem(i+"");
                    cargaDetalleComprobante(comprobante, jsonObject);
                    log.info("****************_INVOICELINE_****************");
                    new BLComprobante().registrar(archivo_propiedades, comprobante);
                }
            }
            log.info("=========================================");

        }catch (JSONException e){
            cargaCreditNote(json, emp_data);
        }

    }

    static void cargaDetalleComprobante(Comprobante comprobante,JSONObject jsonObject){


        JSONObject invoicedQuantity;
        if("03".equals(comprobante.getTipoTransaccion()))
            invoicedQuantity = jsonObject.getJSONObject("cbc:InvoicedQuantity");
        else
            invoicedQuantity = jsonObject.getJSONObject("cbc:CreditedQuantity");
        log.info("CantidadUnidadesItem:"+invoicedQuantity.get("content"));
        comprobante.setCantidadUnidadesItem(invoicedQuantity.get("content")!=null?invoicedQuantity.get("content").toString():"");

        JSONObject item = jsonObject.getJSONObject("cac:Item");
        JSONObject sellersItemIdentification = item.getJSONObject("cac:SellersItemIdentification");
        log.info("CodigoProducto:"+sellersItemIdentification.get("cbc:ID"));
        comprobante.setCodigoProducto(sellersItemIdentification.get("cbc:ID")!=null?sellersItemIdentification.get("cbc:ID").toString():"");

        log.info("DescripcionProducto:"+item.get("cbc:Description"));
        comprobante.setDescripcionProducto(item.get("cbc:Description")!=null?item.get("cbc:Description").toString():"");

        JSONObject lineExtensionAmountObj = jsonObject.getJSONObject("cbc:LineExtensionAmount");
        log.info("Moneda:"+lineExtensionAmountObj.get("currencyID"));
        comprobante.setMoneda(lineExtensionAmountObj.get("currencyID")!=null?lineExtensionAmountObj.get("currencyID").toString():"");
        log.info("PrecioTotalItem:"+lineExtensionAmountObj.get("content"));
        comprobante.setPrecioTotalItem(lineExtensionAmountObj.get("content")!=null?lineExtensionAmountObj.get("content").toString():"");

        if(jsonObject.toString().contains("cac:AllowanceCharge")){
            JSONObject allowanceCharge = jsonObject.getJSONObject("cac:AllowanceCharge");
            JSONObject baseAmount = allowanceCharge.getJSONObject("cbc:BaseAmount");
            log.info("PrecioVentaUnitarioItem:"+baseAmount.get("content"));
            comprobante.setPrecioVentaUnitarioItem(baseAmount.get("content")!=null?baseAmount.get("content").toString():"");
        }else{
            log.info("PrecioVentaUnitarioItem:"+lineExtensionAmountObj.get("content"));
            comprobante.setPrecioVentaUnitarioItem(lineExtensionAmountObj.get("content")!=null?lineExtensionAmountObj.get("content").toString():"");
        }
    }

    static void cargaCreditNote(JSONObject json, String emp_data){
        Comprobante comprobante = new Comprobante();
        Propiedades archivo_propiedades;

        File propiedades=new File("consulta.properties");

        if(!propiedades.exists()){
            archivo_propiedades=new Propiedades(
                    "jdbc:sqlserver://localhost;encrypt=false;databaseName=FMEDITERRANEO;user=sa; password=P@ssword2019;"
            );
            new Util().escribirPropiedades(archivo_propiedades);
        }else{
            archivo_propiedades=new Util().leerPropiedades();
        }
        JSONObject invoice = json.getJSONObject("CreditNote");

        log.info("=========================================");
        String[] emp_array = emp_data.split("user");
        if(emp_array.length == 2){
            comprobante.setCodigoTienda(emp_array[0]);
            comprobante.setCodigoMall(emp_array[1]);
        }
        log.info("TipoTransaccion:01");
        comprobante.setTipoTransaccion("01");
        log.info(json);

        JSONObject accountingSupplierParty = invoice.getJSONObject("AccountingSupplierParty");
        JSONObject party = accountingSupplierParty.getJSONObject("Party");
        JSONObject partyLegalEntity = party.getJSONObject("PartyLegalEntity");
        JSONObject registrationName = partyLegalEntity.getJSONObject("RegistrationName");
        log.info("SupPartyRegName:"+registrationName.get("__cdata"));
        comprobante.setSupPartyRegName(registrationName.get("__cdata")!=null?registrationName.get("__cdata").toString():"");

        JSONObject partyName = party.getJSONObject("PartyName");
        JSONObject name = partyName.getJSONObject("cbc:Name");
        log.info("SupPartyName:"+name.get("__cdata"));
        comprobante.setSupPartyName(name.get("__cdata")!=null?name.get("__cdata").toString():"");

        JSONObject partyIdentification = party.getJSONObject("PartyIdentification");
        log.info("RucEmisor:"+partyIdentification.get("ID"));
        comprobante.setRucEmisor(partyIdentification.get("ID")!=null?partyIdentification.get("ID").toString():"");

        String[] serie = invoice.getString("ID").split("-");
        log.info("Serie:"+(serie[0]!=null?serie[0]:""));
        comprobante.setSerie((serie[0]!=null?serie[0]:""));

        log.info("NumeroTransaccion:"+(serie[1]!=null?serie[1]:""));
        comprobante.setNumeroTransaccion((serie[1]!=null?serie[1]:""));

        comprobante.setId(comprobante.getTipoTransaccion()+"-"+comprobante.getRucEmisor()+"-"+invoice.getString("ID"));
        Comprobante existeComprobante = new BLComprobante().obtenerComprobante(archivo_propiedades, comprobante.getId());
        if(existeComprobante.getId() != null){
            log.info("Comprobante:"+existeComprobante.getId());
            log.info("Comprobante:"+existeComprobante);
            return;
        }

        String issueDate = invoice.getString("IssueDate");
        String issueTime = invoice.getString("IssueTime");
        log.info("Fecha:"+issueDate);
        comprobante.setFecha(issueDate);
        log.info("Hora:"+issueTime);
        comprobante.setHora(issueTime);

        //log.info(invoice);

        JSONObject accountingCustomerParty_partyIdentification = party.getJSONObject("PartyIdentification");
        log.info("CusPartyId:"+accountingCustomerParty_partyIdentification.getJSONObject("ID"));
        comprobante.setCusPartyId(accountingCustomerParty_partyIdentification.getJSONObject("ID")!=null?accountingCustomerParty_partyIdentification.getJSONObject("ID").toString():"");

        log.info("CusPartyRegName:"+registrationName.get("__cdata"));
        comprobante.setCusPartyRegName(registrationName.get("__cdata")!=null?registrationName.get("__cdata").toString():"");

        JSONObject taxTotal = invoice.getJSONObject("TaxTotal");
        log.info("MontoTotalIGV:"+taxTotal.get("TaxAmount"));
        comprobante.setMontoTotalIGV(taxTotal.get("TaxAmount")!=null?taxTotal.get("TaxAmount").toString():"");

        JSONObject legalMonetaryTotal = invoice.getJSONObject("LegalMonetaryTotal");
        log.info("TotalValorVentaBruta:"+legalMonetaryTotal.get("PayableAmount"));
        comprobante.setTotalValorVentaBruta(legalMonetaryTotal.get("PayableAmount")!=null?legalMonetaryTotal.get("PayableAmount").toString():"");

        JSONObject taxSubtotal = taxTotal.getJSONObject("TaxSubtotal");
        log.info("TotalValorVentaNeta:"+taxSubtotal.get("TaxableAmount"));
        comprobante.setTotalValorVentaNeta(taxSubtotal.get("TaxableAmount")!=null?taxSubtotal.get("TaxableAmount").toString():"");

        try {
            JSONObject jsonObject = invoice.getJSONObject("CreditNoteLine");
            //log.info("invoiceLine:"+invoiceLine);
            log.info("****************_CreditNoteLine_****************");
            log.info("NumeroOrdenItem:1");
            comprobante.setNumeroOrdenItem("1");
            cargaDetalleCreditNote(comprobante, jsonObject);
            log.info("****************_CreditNoteLine_****************");
            new BLComprobante().registrar(archivo_propiedades, comprobante);

        }catch (JSONException e){
            //Puede ser una lista
            JSONArray jsonArray = invoice.getJSONArray("CreditNoteLine");
            //log.info("invoiceLine:"+jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                log.info("****************_CreditNoteLine_****************");
                log.info("NumeroOrdenItem:"+i);
                comprobante.setNumeroOrdenItem(i+"");
                cargaDetalleCreditNote(comprobante, jsonObject);
                log.info("****************_CreditNoteLine_****************");
                new BLComprobante().registrar(archivo_propiedades, comprobante);
            }
        }
        log.info("=========================================");
    }

    static void cargaDetalleCreditNote(Comprobante comprobante,JSONObject jsonObject){

        log.info("Moneda:"+jsonObject.get("DocumentCurrencyCode"));
        comprobante.setMoneda(jsonObject.get("DocumentCurrencyCode")!=null?jsonObject.get("DocumentCurrencyCode").toString():"");

        log.info("CantidadUnidadesItem:"+jsonObject.get("CreditedQuantity"));
        comprobante.setCantidadUnidadesItem(jsonObject.get("CreditedQuantity")!=null?jsonObject.get("CreditedQuantity").toString():"");

        JSONObject item = jsonObject.getJSONObject("Item");
        JSONObject sellersItemIdentification = item.getJSONObject("SellersItemIdentification");
        log.info("CodigoProducto:"+sellersItemIdentification.get("ID"));
        comprobante.setCodigoProducto(sellersItemIdentification.get("ID")!=null?sellersItemIdentification.get("ID").toString():"");

        JSONObject description = item.getJSONObject("Description");
        log.info("DescripcionProducto:"+description.get("__cdata"));
        comprobante.setDescripcionProducto(description.get("__cdata")!=null?description.get("__cdata").toString():"");

        log.info("PrecioTotalItem:"+jsonObject.get("LineExtensionAmount"));
        comprobante.setPrecioTotalItem(jsonObject.get("LineExtensionAmount")!=null?jsonObject.get("LineExtensionAmount").toString():"");

        if(jsonObject.toString().contains("AllowanceCharge")){
            JSONObject allowanceCharge = jsonObject.getJSONObject("AllowanceCharge");
            log.info("PrecioVentaUnitarioItem:"+allowanceCharge.get("BaseAmount"));
            comprobante.setPrecioVentaUnitarioItem(allowanceCharge.get("BaseAmount")!=null?allowanceCharge.get("BaseAmount").toString():"");
        }else{
            log.info("PrecioVentaUnitarioItem:"+jsonObject.get("LineExtensionAmount"));
            comprobante.setPrecioVentaUnitarioItem(jsonObject.get("LineExtensionAmount")!=null?jsonObject.get("LineExtensionAmount").toString():"");
        }
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
