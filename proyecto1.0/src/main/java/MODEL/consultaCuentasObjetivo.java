package MODEL;

public class consultaCuentasObjetivo {

    private int numCuenta;
    private int idCO;
    private String objetivo;
    private int cantDepReal;
    private int cantDepH;
    private float totalReal;
    private float totalH;
    private float interR;
    private float interH;

    public consultaCuentasObjetivo(int numCuenta, int idCO, String objetivo, int cantDepReal, int cantDepH, float totalReal, float totalH, float interR, float interH) {
        this.numCuenta = numCuenta;
        this.idCO = idCO;
        this.objetivo = objetivo;
        this.cantDepReal = cantDepReal;
        this.cantDepH = cantDepH;
        this.totalReal = totalReal;
        this.totalH = totalH;
        this.interR = interR;
        this.interH = interH;
    }

    public consultaCuentasObjetivo() {
    }

    public int getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(int numCuenta) {
        this.numCuenta = numCuenta;
    }

    public int getIdCO() {
        return idCO;
    }

    public void setIdCO(int idCO) {
        this.idCO = idCO;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public int getCantDepReal() {
        return cantDepReal;
    }

    public void setCantDepReal(int cantDepReal) {
        this.cantDepReal = cantDepReal;
    }

    public int getCantDepH() {
        return cantDepH;
    }

    public void setCantDepH(int cantDepH) {
        this.cantDepH = cantDepH;
    }

    public float getTotalReal() {
        return totalReal;
    }

    public void setTotalReal(float totalReal) {
        this.totalReal = totalReal;
    }

    public float getTotalH() {
        return totalH;
    }

    public void setTotalH(float totalH) {
        this.totalH = totalH;
    }

    public float getInterR() {
        return interR;
    }

    public void setInterR(float interR) {
        this.interR = interR;
    }

    public float getInterH() {
        return interH;
    }

    public void setInterH(float interH) {
        this.interH = interH;
    }

    public void imprimir() {
        System.out.println(getNumCuenta());
        System.out.println(getIdCO());
        System.out.println(getObjetivo());
        System.out.println(getCantDepReal());
        System.out.println(getCantDepH());
        System.out.println(getTotalReal());
        System.out.println(getCantDepH());
        System.out.println(getInterR());
        System.out.println(getInterH());
    }
}
