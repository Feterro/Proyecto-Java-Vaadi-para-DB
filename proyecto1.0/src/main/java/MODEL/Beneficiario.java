package MODEL;

import java.util.ArrayList;

public class Beneficiario extends Persona {

    private int porcentaje;
    public int cuenta;
    public String parentesco;
    private boolean activo;


    public Beneficiario() {
        this.porcentaje = 0;
        this.cuenta = 1;
        this.parentesco = "x";
        this.activo = true;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public boolean isActivo() {
        return activo;
    }

    public void imprimir(){
        System.out.println(getParentesco() + getTipoDocIdent());
    }
}

