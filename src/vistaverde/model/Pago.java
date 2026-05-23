package vistaverde.model;

import java.io.Serializable;

/**
 * Representa un pago de cuota de mantenimiento mensual.
 */
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    private int    mes;
    private int    anio;
    private double monto;
    private String estado;

    private static final String[] NOMBRES_MESES = {
        "Enero","Febrero","Marzo","Abril","Mayo","Junio",
        "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
    };

    public Pago(int mes, int anio, double monto) {
        this.mes    = mes;
        this.anio   = anio;
        this.monto  = monto;
        this.estado = "Pagado";
    }

    // --- Getters y Setters ---

    public int    getMes()              { return mes; }
    public void   setMes(int mes)       { this.mes = mes; }

    public int    getAnio()             { return anio; }
    public void   setAnio(int anio)     { this.anio = anio; }

    public double getMonto()            { return monto; }
    public void   setMonto(double m)    { this.monto = m; }

    public String getEstado()           { return estado; }
    public void   setEstado(String e)   { this.estado = e; }

    /** Retorna el nombre del mes en español. */
    public String getNombreMes() {
        if (mes >= 1 && mes <= 12) return NOMBRES_MESES[mes - 1];
        return "Desconocido";
    }

    @Override
    public String toString() {
        return getNombreMes() + " " + anio + " - Q" + String.format("%.2f", monto);
    }
}
