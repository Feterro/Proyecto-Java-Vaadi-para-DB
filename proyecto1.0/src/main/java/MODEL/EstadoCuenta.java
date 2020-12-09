package MODEL;

import java.util.Date;

public class EstadoCuenta {
    private int numero;
    private Date fechaInicio;
    private Date fechaFinal;
    private float saldoInicial;
    private float saldoFinal;


    public EstadoCuenta(int Numero, Date fechaInicio, Date fechaFinal, float saldoInicial, float saldoFinal) {
        this.numero = Numero;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.saldoInicial = saldoInicial;
        this.saldoFinal = saldoFinal;
    }

    public int getNumero() {
        return numero;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public float getSaldoInicial() {
        return saldoInicial;
    }

    public float getSaldoFinal() {
        return saldoFinal;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setSaldoInicial(float saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public void setSaldoFinal(float saldoFinal) {
        this.saldoFinal = saldoFinal;
    }
}

