package MODEL;

public class BeneficiarioConsulta {

    private String nombre;
    private int cedula;
    private float montoDinero;
    private int numCuentaMas;
    private int cantCuentas;

    public BeneficiarioConsulta() {
    }

    public BeneficiarioConsulta(String nombre, int cedula, float montoDinero, int numCuentaMas, int cantCuentas) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.montoDinero = montoDinero;
        this.numCuentaMas = numCuentaMas;
        this.cantCuentas = cantCuentas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public float getMontoDinero() {
        return montoDinero;
    }

    public void setMontoDinero(float montoDinero) {
        this.montoDinero = montoDinero;
    }

    public int getNumCuentaMas() {
        return numCuentaMas;
    }

    public void setNumCuentaMas(int numCuentaMas) {
        this.numCuentaMas = numCuentaMas;
    }

    public int getCantCuentas() {
        return cantCuentas;
    }

    public void setCantCuentas(int cantCuentas) {
        this.cantCuentas = cantCuentas;
    }
}
