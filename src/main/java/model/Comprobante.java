package model;

public class Comprobante {
    private String id;
    private String supPartyName;
    private String supPartyRegName;
    private String nombreTienda;
    private String codigoMall;
    private String codigoTienda;
    private String rucEmisor;
    private String identificadorTerminal;
    private String numeroTerminal;
    private String serie;
    private String tipoTransaccion;
    private String numeroTransaccion;
    private String fecha;
    private String hora;
    private String cajero;
    private String vendedor;
    private String cusPartyId;
    private String cusPartyRegName;
    private String cusPartyRegAddrId;
    private String cusPartyRegAddrTypeCode;
    private String cusPartyRegAddrLine;
    private String cusPartyRegAddrCountry;
    private String bonus;
    private String moneda;
    private String medioPago;
    private String totalValorVentaBruta;
    private String cargosDescuentosGlobal;
    private String montoTotalIGV;
    private String totalValorVentaNeta;
    private String numeroOrdenItem;
    private String cantidadUnidadesItem;
    private String codigoProducto;
    private String descripcionProducto;
    private String precioVentaUnitarioItem;
    private String precioTotalItem;

    public Comprobante(){

    }

    public Comprobante(String id, String supPartyName, String supPartyRegName, String nombreTienda, String codigoMall, String codigoTienda, String rucEmisor, String identificadorTerminal, String numeroTerminal, String serie, String tipoTransaccion, String numeroTransaccion, String fecha, String hora, String cajero, String vendedor, String cusPartyId, String cusPartyRegName, String cusPartyRegAddrId, String cusPartyRegAddrTypeCode, String cusPartyRegAddrLine, String cusPartyRegAddrCountry, String bonus, String moneda, String medioPago, String totalValorVentaBruta, String cargosDescuentosGlobal, String montoTotalIGV, String totalValorVentaNeta, String numeroOrdenItem, String cantidadUnidadesItem, String codigoProducto, String descripcionProducto, String precioVentaUnitarioItem, String precioTotalItem) {
        this.id = id;
        this.supPartyName = supPartyName;
        this.supPartyRegName = supPartyRegName;
        this.nombreTienda = nombreTienda;
        this.codigoMall = codigoMall;
        this.codigoTienda = codigoTienda;
        this.rucEmisor = rucEmisor;
        this.identificadorTerminal = identificadorTerminal;
        this.numeroTerminal = numeroTerminal;
        this.serie = serie;
        this.tipoTransaccion = tipoTransaccion;
        this.numeroTransaccion = numeroTransaccion;
        this.fecha = fecha;
        this.hora = hora;
        this.cajero = cajero;
        this.vendedor = vendedor;
        this.cusPartyId = cusPartyId;
        this.cusPartyRegName = cusPartyRegName;
        this.cusPartyRegAddrId = cusPartyRegAddrId;
        this.cusPartyRegAddrTypeCode = cusPartyRegAddrTypeCode;
        this.cusPartyRegAddrLine = cusPartyRegAddrLine;
        this.cusPartyRegAddrCountry = cusPartyRegAddrCountry;
        this.bonus = bonus;
        this.moneda = moneda;
        this.medioPago = medioPago;
        this.totalValorVentaBruta = totalValorVentaBruta;
        this.cargosDescuentosGlobal = cargosDescuentosGlobal;
        this.montoTotalIGV = montoTotalIGV;
        this.totalValorVentaNeta = totalValorVentaNeta;
        this.numeroOrdenItem = numeroOrdenItem;
        this.cantidadUnidadesItem = cantidadUnidadesItem;
        this.codigoProducto = codigoProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioVentaUnitarioItem = precioVentaUnitarioItem;
        this.precioTotalItem = precioTotalItem;
    }

    public Comprobante(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupPartyName() {
        return supPartyName;
    }

    public void setSupPartyName(String supPartyName) {
        this.supPartyName = supPartyName;
    }

    public String getSupPartyRegName() {
        return supPartyRegName;
    }

    public void setSupPartyRegName(String supPartyRegName) {
        this.supPartyRegName = supPartyRegName;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getCodigoMall() {
        return codigoMall;
    }

    public void setCodigoMall(String codigoMall) {
        this.codigoMall = codigoMall;
    }

    public String getCodigoTienda() {
        return codigoTienda;
    }

    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    public String getRucEmisor() {
        return rucEmisor;
    }

    public void setRucEmisor(String rucEmisor) {
        this.rucEmisor = rucEmisor;
    }

    public String getIdentificadorTerminal() {
        return identificadorTerminal;
    }

    public void setIdentificadorTerminal(String identificadorTerminal) {
        this.identificadorTerminal = identificadorTerminal;
    }

    public String getNumeroTerminal() {
        return numeroTerminal;
    }

    public void setNumeroTerminal(String numeroTerminal) {
        this.numeroTerminal = numeroTerminal;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getCusPartyId() {
        return cusPartyId;
    }

    public void setCusPartyId(String cusPartyId) {
        this.cusPartyId = cusPartyId;
    }

    public String getCusPartyRegName() {
        return cusPartyRegName;
    }

    public void setCusPartyRegName(String cusPartyRegName) {
        this.cusPartyRegName = cusPartyRegName;
    }

    public String getCusPartyRegAddrId() {
        return cusPartyRegAddrId;
    }

    public void setCusPartyRegAddrId(String cusPartyRegAddrId) {
        this.cusPartyRegAddrId = cusPartyRegAddrId;
    }

    public String getCusPartyRegAddrTypeCode() {
        return cusPartyRegAddrTypeCode;
    }

    public void setCusPartyRegAddrTypeCode(String cusPartyRegAddrTypeCode) {
        this.cusPartyRegAddrTypeCode = cusPartyRegAddrTypeCode;
    }

    public String getCusPartyRegAddrLine() {
        return cusPartyRegAddrLine;
    }

    public void setCusPartyRegAddrLine(String cusPartyRegAddrLine) {
        this.cusPartyRegAddrLine = cusPartyRegAddrLine;
    }

    public String getCusPartyRegAddrCountry() {
        return cusPartyRegAddrCountry;
    }

    public void setCusPartyRegAddrCountry(String cusPartyRegAddrCountry) {
        this.cusPartyRegAddrCountry = cusPartyRegAddrCountry;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getTotalValorVentaBruta() {
        return totalValorVentaBruta;
    }

    public void setTotalValorVentaBruta(String totalValorVentaBruta) {
        this.totalValorVentaBruta = totalValorVentaBruta;
    }

    public String getCargosDescuentosGlobal() {
        return cargosDescuentosGlobal;
    }

    public void setCargosDescuentosGlobal(String cargosDescuentosGlobal) {
        this.cargosDescuentosGlobal = cargosDescuentosGlobal;
    }

    public String getMontoTotalIGV() {
        return montoTotalIGV;
    }

    public void setMontoTotalIGV(String montoTotalIGV) {
        this.montoTotalIGV = montoTotalIGV;
    }

    public String getTotalValorVentaNeta() {
        return totalValorVentaNeta;
    }

    public void setTotalValorVentaNeta(String totalValorVentaNeta) {
        this.totalValorVentaNeta = totalValorVentaNeta;
    }

    public String getNumeroOrdenItem() {
        return numeroOrdenItem;
    }

    public void setNumeroOrdenItem(String numeroOrdenItem) {
        this.numeroOrdenItem = numeroOrdenItem;
    }

    public String getCantidadUnidadesItem() {
        return cantidadUnidadesItem;
    }

    public void setCantidadUnidadesItem(String cantidadUnidadesItem) {
        this.cantidadUnidadesItem = cantidadUnidadesItem;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getPrecioVentaUnitarioItem() {
        return precioVentaUnitarioItem;
    }

    public void setPrecioVentaUnitarioItem(String precioVentaUnitarioItem) {
        this.precioVentaUnitarioItem = precioVentaUnitarioItem;
    }

    public String getPrecioTotalItem() {
        return precioTotalItem;
    }

    public void setPrecioTotalItem(String precioTotalItem) {
        this.precioTotalItem = precioTotalItem;
    }

    @Override
    public String toString() {
        return "Comprobante{" +
                "id='" + id + '\'' +
                ", supPartyName='" + supPartyName + '\'' +
                ", supPartyRegName='" + supPartyRegName + '\'' +
                ", nombreTienda='" + nombreTienda + '\'' +
                ", codigoMall='" + codigoMall + '\'' +
                ", codigoTienda='" + codigoTienda + '\'' +
                ", rucEmisor='" + rucEmisor + '\'' +
                ", identificadorTerminal='" + identificadorTerminal + '\'' +
                ", numeroTerminal='" + numeroTerminal + '\'' +
                ", serie='" + serie + '\'' +
                ", tipoTransaccion='" + tipoTransaccion + '\'' +
                ", numeroTransaccion='" + numeroTransaccion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", cajero='" + cajero + '\'' +
                ", vendedor='" + vendedor + '\'' +
                ", cusPartyId='" + cusPartyId + '\'' +
                ", cusPartyRegName='" + cusPartyRegName + '\'' +
                ", cusPartyRegAddrId='" + cusPartyRegAddrId + '\'' +
                ", cusPartyRegAddrTypeCode='" + cusPartyRegAddrTypeCode + '\'' +
                ", cusPartyRegAddrLine='" + cusPartyRegAddrLine + '\'' +
                ", cusPartyRegAddrCountry='" + cusPartyRegAddrCountry + '\'' +
                ", bonus='" + bonus + '\'' +
                ", moneda='" + moneda + '\'' +
                ", medioPago='" + medioPago + '\'' +
                ", totalValorVentaBruta='" + totalValorVentaBruta + '\'' +
                ", cargosDescuentosGlobal='" + cargosDescuentosGlobal + '\'' +
                ", montoTotalIGV='" + montoTotalIGV + '\'' +
                ", totalValorVentaNeta='" + totalValorVentaNeta + '\'' +
                ", numeroOrdenItem='" + numeroOrdenItem + '\'' +
                ", cantidadUnidadesItem='" + cantidadUnidadesItem + '\'' +
                ", codigoProducto='" + codigoProducto + '\'' +
                ", descripcionProducto='" + descripcionProducto + '\'' +
                ", precioVentaUnitarioItem='" + precioVentaUnitarioItem + '\'' +
                ", precioTotalItem='" + precioTotalItem + '\'' +
                '}';
    }
}
