package MODEL;

import java.util.Date;

public class CuentaObjetivo {

    private String objetivo;
    private Date fechaInicio;
    private Date fechaFinal;
    private float cuota;
    private float saldo;
    private float intereses;
    private int numCuenta;

    public CuentaObjetivo() {}

    public CuentaObjetivo(String objetivo, Date fechaInicio, Date fechaFinal, float cuota, float saldo, float intereses, int numCuenta) {
        this.objetivo = objetivo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.cuota = cuota;
        this.saldo = saldo;
        this.intereses = intereses;
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

    public int getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(int numCuenta) {
        this.numCuenta = numCuenta;
    }
}
