package proyecto_vistaverde;

import java.io.Serializable;

public class Propietario implements Serializable {
    
    private String nombreCompleto;
    private int    numeroCasa;
    private String telefono;
    private String correoElectronico;

    public Propietario(String nombreCompleto, int numeroCasa, 
                       String telefono, String correoElectronico) {
        this.nombreCompleto    = nombreCompleto;
        this.numeroCasa        = numeroCasa;
        this.telefono          = telefono;
        this.correoElectronico = correoElectronico;
    }

    public String getNombreCompleto()             { return nombreCompleto; }
    public void   setNombreCompleto(String n)     { this.nombreCompleto = n; }

    public int  getNumeroCasa()                   { return numeroCasa; }
    public void setNumeroCasa(int n)              { this.numeroCasa = n; }

    public String getTelefono()                   { return telefono; }
    public void   setTelefono(String t)           { this.telefono = t; }

    public String getCorreoElectronico()          { return correoElectronico; }
    public void   setCorreoElectronico(String c)  { this.correoElectronico = c; }

    @Override
    public String toString() {
        return nombreCompleto + " (Casa " + numeroCasa + ")";
    }
}
