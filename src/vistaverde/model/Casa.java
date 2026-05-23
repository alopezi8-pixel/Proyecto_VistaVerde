package vistaverde.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Representa una casa del Condominio Vista Verde.
 * Contiene al propietario y la lista de pagos realizados.
 */
public class Casa implements Serializable {

    private static final long serialVersionUID = 1L;

    private int              numeroCasa;
    private Propietario      propietario;
    private ArrayList<Pago>  pagos;

    public Casa(int numeroCasa) {
        this.numeroCasa  = numeroCasa;
        this.propietario = null;
        this.pagos       = new ArrayList<>();
    }

    // --- Getters y Setters ---

    public int getNumeroCasa() { return numeroCasa; }

    public Propietario getPropietario()              { return propietario; }
    public void        setPropietario(Propietario p) { this.propietario = p; }

    public boolean tienePropietario() { return propietario != null; }

    public ArrayList<Pago> getPagos() { return pagos; }

    // --- Lógica de pagos ---

    /**
     * Registra un pago. Retorna false si ya existe ese mes/anio
     * o si hay meses anteriores del mismo anio sin pagar.
     */
    public boolean registrarPago(int mes, int anio, double monto) {
        if (existePago(mes, anio)) return false;
        for (int m = 1; m < mes; m++) {
            if (!existePago(m, anio)) return false;
        }
        pagos.add(new Pago(mes, anio, monto));
        return true;
    }

    /** Verifica si existe un pago para el mes y anio indicados. */
    public boolean existePago(int mes, int anio) {
        for (Pago p : pagos) {
            if (p.getMes() == mes && p.getAnio() == anio) return true;
        }
        return false;
    }

    /** Suma total de todos los pagos registrados. */
    public double getTotalPagado() {
        double total = 0;
        for (Pago p : pagos) total += p.getMonto();
        return total;
    }

    /** Suma total de pagos en un anio especifico. */
    public double getTotalPagadoAnio(int anio) {
        double total = 0;
        for (Pago p : pagos) {
            if (p.getAnio() == anio) total += p.getMonto();
        }
        return total;
    }

    /** Lista de meses (1-12) pagados en el anio indicado. */
    public ArrayList<Integer> getMesesPagados(int anio) {
        ArrayList<Integer> lista = new ArrayList<>();
        for (Pago p : pagos) {
            if (p.getAnio() == anio) lista.add(p.getMes());
        }
        return lista;
    }

    /** Lista de meses pendientes del 1 al mesActual en el anio indicado. */
    public ArrayList<Integer> getMesesPendientes(int anio, int mesActual) {
        ArrayList<Integer> lista = new ArrayList<>();
        for (int m = 1; m <= mesActual; m++) {
            if (!existePago(m, anio)) lista.add(m);
        }
        return lista;
    }

    @Override
    public String toString() { return "Casa " + numeroCasa; }
}
