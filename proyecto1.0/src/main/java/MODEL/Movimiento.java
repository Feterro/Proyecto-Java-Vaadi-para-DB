package MODEL;

import java.util.Date;

public class Movimiento {

    private Date fechaMov;
    private String descripcion;
    private String tipo;
    private float monto;

    public Movimiento(Date fechaMov, String descripcion, String tipo, float monto) {
        this.fechaMov = fechaMov;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.monto = monto;
    }

    public Movimiento() {
        this.fechaMov = fechaMov;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.monto = monto;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
    
}
