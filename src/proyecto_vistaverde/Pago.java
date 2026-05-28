package proyecto_vistaverde;

import java.io.Serializable;

public class Pago implements Serializable {

    private int    mes;
    private int    anio;
    private double monto;

    private static final String[] NOMBRES_MESES = {
        "Enero","Febrero","Marzo","Abril","Mayo","Junio",
        "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
    };

    public Pago(int mes, int anio, double monto) {
        this.mes   = mes;
        this.anio  = anio;
        this.monto = monto;
    }

    public int    getMes()           { return mes; }
    public int    getAnio()          { return anio; }
    public double getMonto()         { return monto; }
    public void   setMonto(double m) { this.monto = m; }

    public String getNombreMes() {
        if (mes >= 1 && mes <= 12) return NOMBRES_MESES[mes - 1];
        return "Desconocido";
    }

    @Override
    public String toString() {
        return getNombreMes() + " " + anio + " - Q" + String.format("%.2f", monto);
    }
}
