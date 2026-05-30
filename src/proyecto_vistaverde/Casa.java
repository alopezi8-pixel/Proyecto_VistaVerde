package proyecto_vistaverde;

import java.io.Serializable;
import java.util.ArrayList;

public class Casa implements Serializable {

    private int             numeroCasa;
    private Propietario     propietario;
    private ArrayList<Pago> pagos;

    public Casa(int numeroCasa) {
        this.numeroCasa  = numeroCasa;
        this.propietario = null;
        this.pagos       = new ArrayList<>();
    }
    private double cuotaEspecial = 0;

public double getCuotaEspecial()         { return cuotaEspecial; }
public void   setCuotaEspecial(double c) { this.cuotaEspecial = c; }
public boolean tieneCuotaEspecial()      { return cuotaEspecial > 0; }

    public int          getNumeroCasa()              { return numeroCasa; }
    public Propietario  getPropietario()             { return propietario; }
    public void         setPropietario(Propietario p){ this.propietario = p; }
    public boolean      tienePropietario()           { return propietario != null; }
    public ArrayList<Pago> getPagos()               { return pagos; }

    // Registra pago solo si no existe ese mes/anio
    // y si los meses anteriores del mismo anio ya fueron pagados
    public boolean registrarPago(int mes, int anio, double monto) {
        if (existePago(mes, anio)) return false;
        if (propietario == null)   return false;

        int mesReg  = propietario.getMesRegistro();
        int anioReg = propietario.getAnioRegistro();

        if (mesReg == 0 || anioReg == 0) {
            // Propietario sin fecha de registro — validación normal
            for (int m = 1; m < mes; m++) {
                if (!existePago(m, anio)) return false;
            }
        } else if (anio < anioReg) {
            // Año anterior al registro — no permitir
            return false;
        } else if (anio == anioReg) {
            // Mismo año — no puede pagar mes anterior al registro
            if (mes < mesReg) return false;
            // Validar meses pendientes desde el mes de registro
            for (int m = mesReg; m < mes; m++) {
                if (!existePago(m, anio)) return false;
            }
        } else {
            // Año posterior — validar todos los meses
            for (int m = 1; m < mes; m++) {
                if (!existePago(m, anio)) return false;
            }
        }

        pagos.add(new Pago(mes, anio, monto));
        return true;
    }

    public boolean existePago(int mes, int anio) {
        for (Pago p : pagos) {
            if (p.getMes() == mes && p.getAnio() == anio) return true;
        }
        return false;
    }

    public double getTotalPagado() {
        double total = 0;
        for (Pago p : pagos) total += p.getMonto();
        return total;
    }

    public double getTotalPagadoAnio(int anio) {
        double total = 0;
        for (Pago p : pagos) {
            if (p.getAnio() == anio) total += p.getMonto();
        }
        return total;
    }

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