package conexion;

public class Persona {

    private String nombre;
    private String email;
    private String fechaNac;
    private int tel1;
    private int tel2;
    private String tipoDocIdent;
    private int valorDocIdent;

    public Persona() {
        this.nombre = "";
        this.email = "";
        this.fechaNac = "";
        this.tel1 = 0;
        this.tel2 = 0;
        this.tipoDocIdent = "";
        this.valorDocIdent = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public int getTel1() {
        return tel1;
    }

    public void setTel1(int tel1) {
        this.tel1 = tel1;
    }

    public int getTel2() {
        return tel2;
    }

    public void setTel2(int tel2) {
        this.tel2 = tel2;
    }

    public String getTipoDocIdent() {
        return tipoDocIdent;
    }

    public void setTipoDocIdent(String tipoDocIdent) {
        this.tipoDocIdent = tipoDocIdent;
    }

    public int getValorDocIdent() {
        return valorDocIdent;
    }

    public void setValorDocIdent(int valorDocIdent) {
        this.valorDocIdent = valorDocIdent;
    }
}
