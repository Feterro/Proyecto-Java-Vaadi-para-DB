package MODEL;

import java.net.InetAddress;
import java.sql.*;

public class Persona {

    public String nombre;
    public String email;
    public String fechaNac;
    public int tel1;
    public int tel2;
    public String tipoDocIdent;
    public int valorDocIdent;

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

    public String getContrasenna(Connection connection, String nomUsuario){
        String res = "";
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_US_SolicitaContrasenna ?, ?");
            callableStatement.setString(1, nomUsuario);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getString("contrasenna"));
                res =  resultSet.getString("contrasenna");
            }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
        return res;
    }

    public void insertaBeneficiarios(Connection connection, int personaDoc, int cuentaNum, String parentescoNom, int porcentaje, String nombre, String fechaNac, int tel1, int tel2, String tipoDoc, String correo){
        try {
            String ip = InetAddress.getLocalHost().toString();
            String[] ipDividido =  ip.split("/");
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_PE_InsertaBeneficiarioComplejo ?,?,?,?,?,?,?,?,?,?,?,?");
            callableStatement.setString(1, nombre);
            callableStatement.setInt(2, personaDoc);
            callableStatement.setDate(3, Date.valueOf(fechaNac));
            callableStatement.setInt(4, tel1);
            callableStatement.setInt(5, tel2);
            callableStatement.setString(6, tipoDoc);
            callableStatement.setString(7, correo);
            callableStatement.setInt(8, cuentaNum);
            callableStatement.setString(9, parentescoNom);
            callableStatement.setInt(10, porcentaje);
            callableStatement.setString(11, ipDividido[1]);
            callableStatement.registerOutParameter(12,Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){

                System.out.println(resultSet.getInt("N"));}
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
    }

    public void modificaPersonas(Connection connection, int personaDocOri, int personaDoc, String parentescoNom, int porcentaje, String nombre, String fechaNac, int tel1, int tel2, String tipoDoc, String correo){
        try {
            String ip = InetAddress.getLocalHost().toString();
            String[] ipDividido =  ip.split("/");
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_PE_BE_ActualizarPersona ?,?,?,?,?,?,?,?,?,?,?,?");
            callableStatement.setString(1, nombre);
            callableStatement.setInt(2, personaDocOri);
            callableStatement.setInt(3, personaDoc);
            callableStatement.setDate(4, Date.valueOf(fechaNac));
            callableStatement.setInt(5, tel1);
            callableStatement.setInt(6, tel2);
            callableStatement.setString(7, tipoDoc);
            callableStatement.setString(8, correo);
            callableStatement.setString(9, parentescoNom);
            callableStatement.setInt(10, porcentaje);
            callableStatement.setString(11, ipDividido[1]);
            callableStatement.registerOutParameter(12,Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){

                System.out.println(resultSet.getInt("N"));}
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
    }
}
