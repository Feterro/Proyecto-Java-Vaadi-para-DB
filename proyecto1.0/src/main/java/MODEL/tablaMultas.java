package MODEL;

public class tablaMultas {

    private int numeroCuenta;
    private float cantPromedioMes;
    private String mayorCantRetiros;

    public tablaMultas() {}

    public tablaMultas(int numeroCuenta, float cantPromedioMes, String mayorCantRetiros) {
        this.numeroCuenta = numeroCuenta;
        this.cantPromedioMes = cantPromedioMes;
        this.mayorCantRetiros = mayorCantRetiros;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public float getCantPromedioMes() {
        return cantPromedioMes;
    }

    public void setCantPromedioMes(int cantPromedioMes) {
        this.cantPromedioMes = cantPromedioMes;
    }

    public String getMayorCantRetiros() {
        return mayorCantRetiros;
    }

    public void setMayorCantRetiros(String mayorCantRetiros) {
        this.mayorCantRetiros = mayorCantRetiros;
    }
}
