package MODEL;

import java.util.Date;

public class EstadoCuenta {
    private int Numero;
    private Date Fecha_Inicio;
    private Date Fecha_Final;
    private float Saldo_Inicial;
    private float Saldo_Final;

    public EstadoCuenta(int Numero, Date Fecha_Inicio, Date Fecha_Final, float Saldo_Inicial, float Saldo_Final) {
        this.Numero = Numero;
        this.Fecha_Inicio = Fecha_Inicio;
        this.Fecha_Final = Fecha_Final;
        this.Saldo_Inicial = Saldo_Inicial;
        this.Saldo_Final = Saldo_Final;
    }

    public EstadoCuenta() {
    }

    public EstadoCuenta(int Numero, Date Fecha_Inicio, Date Fecha_Final) {
        this.Numero = Numero;
        this.Fecha_Inicio = Fecha_Inicio;
        this.Fecha_Final = Fecha_Final;
    }

    public int getNumero() {
        return Numero;
    }

    public Date getFecha_Inicio() {
        return Fecha_Inicio;
    }

    public Date getFecha_Final() {
        return Fecha_Final;
    }

    public float getSaldo_Inicial() {
        return Saldo_Inicial;
    }

    public float getSaldo_Final() {
        return Saldo_Final;
    }

    public void setNumero(int numero) {
        this.Numero = numero;
    }

    public void setFecha_Inicio(Date fecha_Inicio) {
        this.Fecha_Inicio = fecha_Inicio;
    }

    public void setFecha_Final(Date fecha_Final) {
        this.Fecha_Final = fecha_Final;
    }

    public void setSaldo_Inicial(float saldo_Inicial) {
        this.Saldo_Inicial = saldo_Inicial;
    }

    public void setSaldo_Final(float saldo_Final) {
        this.Saldo_Final = saldo_Final;
    }
}

