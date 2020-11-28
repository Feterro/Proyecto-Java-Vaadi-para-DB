package MODEL;

import java.util.ArrayList;

public class Beneficiario extends Persona {

    private int porcentaje;

    public int cuenta;
    public String parentesco;
    private ArrayList<String> listaParentescos;
    private ArrayList<String> listaTipoDoc;
    private ArrayList<Integer> listaCuentasVisibles;
    private boolean activo;


    public Beneficiario() {
        this.porcentaje = 0;
        this.cuenta = 1;
        this.parentesco = "x";
        this.listaParentescos = new ArrayList<>();
        this.listaCuentasVisibles = new ArrayList<>();
        this.listaTipoDoc = new ArrayList<>();
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

    public ArrayList<String> getListaParentescos() {
        return listaParentescos;
    }

    public void setListaParentescos(ArrayList<String> listaParentescos) {
        this.listaParentescos = listaParentescos;
    }

    public ArrayList<String> getListaTipoDoc() {
        return listaTipoDoc;
    }

    public void setListaTipoDoc(ArrayList<String> listaTipoDoc) {
        this.listaTipoDoc = listaTipoDoc;
    }

    public ArrayList<Integer> getListaCuentasVisibles() {
        return listaCuentasVisibles;
    }

    public void setListaCuentasVisibles(ArrayList<Integer> listaCuentasVisibles) {
        this.listaCuentasVisibles = listaCuentasVisibles;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


}