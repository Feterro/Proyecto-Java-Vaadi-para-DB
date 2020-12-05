package MODEL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Collections;

public class CuentaObjetivo {

    private String objetivo;
    private Date fechaInicio;
    private Date fechaFinal;
    private float cuota;
    private float saldo;
    private float intereses;
    private int numCuentaAsociada;
    private String numCuenta;

    public CuentaObjetivo() {}

    public CuentaObjetivo(String numCuenta, String objetivo, Date fechaInicio, Date fechaFinal, float cuota, float saldo, float intereses, int numCuentaAsociada) {
        this.numCuenta = numCuenta;
        this.objetivo = objetivo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.cuota = cuota;
        this.saldo = saldo;
        this.intereses = intereses;
        this.numCuentaAsociada = numCuentaAsociada;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public float getCuota() {
        return cuota;
    }

    public void setCuota(float cuota) {
        this.cuota = cuota;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float getIntereses() {
        return intereses;
    }

    public void setIntereses(float intereses) {
        this.intereses = intereses;
    }

    public int getNumCuentaAsociada() {
        return numCuentaAsociada;
    }

    public void setNumCuentaAsociada(int numCuentaAsociada) {
        this.numCuentaAsociada = numCuentaAsociada;
    }

    public String generarNumero(ArrayList<String> numeros){
        Collections.sort(numeros);
        int ultimoNumero = Integer.parseInt(numeros.get(numeros.size()-1));
        int nuevoNumCuenta = ultimoNumero + 1;
        String numFinal =  String.format("%05d", nuevoNumCuenta);
        return numFinal;
    }

}
