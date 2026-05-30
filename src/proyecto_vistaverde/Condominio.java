package proyecto_vistaverde;

import java.io.Serializable;
import java.util.ArrayList;

public class Condominio implements Serializable {

    public static final int    TOTAL_CASAS = 30;
    public static final String NOMBRE      = "Vista Verde";

    private ArrayList<Casa> casas;
    private double          cuota;

    public Condominio() {
        casas = new ArrayList<>();
        cuota = 1500.00;
        for (int i = 1; i <= TOTAL_CASAS; i++) {
            casas.add(new Casa(i));
        }
    }

    public ArrayList<Casa> getCasas()              { return casas; }
    public double          getCuota()              { return cuota; }
    public void            setCuota(double c)      { this.cuota = c; }

    public double getTotalEsperado() {
        return cuota * TOTAL_CASAS;
    }

    public Casa getCasa(int numero) {
        for (Casa c : casas) {
            if (c.getNumeroCasa() == numero) return c;
        }
        return null;
    }

    public boolean registrarPropietario(int numeroCasa, Propietario p) {
        Casa casa = getCasa(numeroCasa);
        if (casa == null || casa.tienePropietario()) return false;
        casa.setPropietario(p);
        return true;
    }

    public boolean registrarPago(int numeroCasa, int mes, int anio, double monto) {
        Casa casa = getCasa(numeroCasa);
        if (casa == null || !casa.tienePropietario()) return false;
        return casa.registrarPago(mes, anio, monto);
    }

    public double getTotalRecaudadoMes(int mes, int anio) {
        double total = 0;
        for (Casa c : casas) {
            for (Pago p : c.getPagos()) {
                if (p.getMes() == mes && p.getAnio() == anio)
                    total += p.getMonto();
            }
        }
        return total;
    }

public ArrayList<Casa> getCasasMorosas(int mes, int anio) {
    ArrayList<Casa> morosas = new ArrayList<>();
    for (Casa c : casas) {
        if (!c.tienePropietario()) continue;

        int mesReg  = c.getPropietario().getMesRegistro();
        int anioReg = c.getPropietario().getAnioRegistro();

        // Solo contar como morosa si ya estaba registrada ese mes
        if (anio > anioReg || (anio == anioReg && mes >= mesReg)) {
            if (!c.existePago(mes, anio)) {
                morosas.add(c);
            }
        }
    }
    return morosas;
}
}
