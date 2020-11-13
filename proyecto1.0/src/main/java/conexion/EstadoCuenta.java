package conexion;

import java.util.Date;

public class EstadoCuenta {
    private int id;
    private Date fechaIn;
    private Date fechaFin;
    private float saldoIn;
    private float saldoFin;

    public EstadoCuenta(int id, Date fechaIn, Date fechaFin, float saldoIn, float saldoFin) {
        this.id = id;
        this.fechaIn = fechaIn;
        this.fechaFin = fechaFin;
        this.saldoIn = saldoIn;
        this.saldoFin = saldoFin;
    }

    public EstadoCuenta() {
    }

    public EstadoCuenta(int id, Date fechaIn, Date fechaFin) {
        this.id = id;
        this.fechaIn = fechaIn;
        this.fechaFin = fechaFin;
    }

    public int getId() {
        return id;
    }

    public Date getFechaIn() {
        return fechaIn;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public float getSaldoIn() {
        return saldoIn;
    }

    public float getSaldoFin() {
        return saldoFin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFechaIn(Date fechaIn) {
        this.fechaIn = fechaIn;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setSaldoIn(float saldoIn) {
        this.saldoIn = saldoIn;
    }

    public void setSaldoFin(float saldoFin) {
        this.saldoFin = saldoFin;
    }
}

