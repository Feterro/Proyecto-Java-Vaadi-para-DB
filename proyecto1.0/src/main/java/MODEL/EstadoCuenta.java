package MODEL;

import java.util.Date;

public class EstadoCuenta {
    private int numero;
    private Date fechaInicio;
    private Date fechaFinal;
//    private float Saldo_Inicial;
//    private float Saldo_Final;


    public EstadoCuenta(int Numero, Date fechaInicio, Date fechaFinal) {
        this.numero = Numero;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
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

//    public float getSaldo_Inicial() {
//        return Saldo_Inicial;
//    }
//
//    public float getSaldo_Final() {
//        return Saldo_Final;
//    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

//    public void setSaldo_Inicial(float saldo_Inicial) {
//        this.Saldo_Inicial = saldo_Inicial;
//    }
//
//    public void setSaldo_Final(float saldo_Final) {
//        this.Saldo_Final = saldo_Final;
//    }
}

