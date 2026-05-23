package vistaverde.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase principal que administra las 30 casas del Condominio Vista Verde.
 */
public class Condominio implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int    TOTAL_CASAS = 30;
    public static final String NOMBRE      = "Vista Verde";

    private ArrayList<Casa> casas;
    private double          cuotaMantenimiento;

    public Condominio() {
        casas              = new ArrayList<>();
        cuotaMantenimiento = 1500.00;
        for (int i = 1; i <= TOTAL_CASAS; i++) {
            casas.add(new Casa(i));
        }
    }

    // --- Getters y Setters ---

    public ArrayList<Casa> getCasas()                        { return casas; }
    public double          getCuotaMantenimiento()           { return cuotaMantenimiento; }
    public void            setCuotaMantenimiento(double c)   { this.cuotaMantenimiento = c; }

    /** Retorna la casa con el numero indicado, o null si no existe. */
    public Casa getCasa(int numero) {
        for (Casa c : casas) {
            if (c.getNumeroCasa() == numero) return c;
        }
        return null;
    }

    /** Total esperado mensual = cuota x 30. */
    public double getTotalEsperadoMensual() {
        return cuotaMantenimiento * TOTAL_CASAS;
    }

    /** Total recaudado en un mes y anio especificos. */
    public double getTotalRecaudadoMes(int mes, int anio) {
        double total = 0;
        for (Casa c : casas) {
            for (Pago p : c.getPagos()) {
                if (p.getMes() == mes && p.getAnio() == anio) {
                    total += p.getMonto();
                }
            }
        }
        return total;
    }

    /** Casas con propietario que no pagaron el mes/anio indicados. */
    public ArrayList<Casa> getCasasMorosas(int mes, int anio) {
        ArrayList<Casa> morosas = new ArrayList<>();
        for (Casa c : casas) {
            if (c.tienePropietario() && !c.existePago(mes, anio)) {
                morosas.add(c);
            }
        }
        return morosas;
    }

    /** Registra un propietario en una casa. Retorna false si ya tiene. */
    public boolean registrarPropietario(int numeroCasa, Propietario p) {
        Casa casa = getCasa(numeroCasa);
        if (casa == null || casa.tienePropietario()) return false;
        casa.setPropietario(p);
        return true;
    }

    /** Registra un pago en una casa. */
    public boolean registrarPago(int numeroCasa, int mes, int anio, double monto) {
        Casa casa = getCasa(numeroCasa);
        if (casa == null || !casa.tienePropietario()) return false;
        return casa.registrarPago(mes, anio, monto);
    }
}
