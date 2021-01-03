package MODEL;

import java.util.Date;

public class tablaMultas {

    private int numeroCuenta;
    private int cantPromedioMes;
    private Date mayorCantRetiros;

    public tablaMultas() {}

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public int getCantPromedioMes() {
        return cantPromedioMes;
    }

    public void setCantPromedioMes(int cantPromedioMes) {
        this.cantPromedioMes = cantPromedioMes;
    }

    public Date getMayorCantRetiros() {
        return mayorCantRetiros;
    }

    public void setMayorCantRetiros(Date mayorCantRetiros) {
        this.mayorCantRetiros = mayorCantRetiros;
    }
}
