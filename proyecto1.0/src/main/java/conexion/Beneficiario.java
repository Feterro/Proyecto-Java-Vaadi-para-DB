package conexion;
import java.sql.*;
import java.util.ArrayList;
import java.net.InetAddress;

public class Beneficiario {

    private int porcentaje;
    private String nombre;
    private int cuenta;
    private String parentesco;
    private ArrayList<String> listaParentescos;
    private ArrayList<String> listaTipoDoc;
    private boolean activo;

    public Beneficiario() {
        this.porcentaje = 0;
        this.nombre = "x";
        this.cuenta = 1;
        this.parentesco = "x";
        this.listaParentescos = new ArrayList<>();
        this.activo = true;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public void getListaParentescos(Connection connection){
        ArrayList<String> listaParentezcos = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_PA_SolicitaParentezcos ?");
            callableStatement.registerOutParameter(1,Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                listaParentezcos.add(resultSet.getString("nombre"));
                //System.out.println(resultSet.getString("nombre"));
                 }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
    }

    public void getListaTipoDocIden(Connection connection){
        ArrayList<String> listaParentezcos = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_TDI_SolicitaTiposDocIdent ?");
            callableStatement.registerOutParameter(1,Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                listaParentezcos.add(resultSet.getString("tipoDoc"));
                System.out.println(resultSet.getString("tipoDoc"));
                 }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
    }

    public void insertaBeneficiarios(Connection connection, int personaDoc, int cuentaNum, String parentescoNom, int porcentaje){
        try {
            String ip = InetAddress.getLocalHost().toString();
            String[] ipDividido =  ip.split("/");
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_BE_InsertaBeneficiario ?,?,?,?,?,?");
            callableStatement.setInt(1, personaDoc);
            callableStatement.setInt(2, cuentaNum);
            callableStatement.setString(3, parentescoNom);
            callableStatement.setInt(4, porcentaje);
            callableStatement.setString(5, ipDividido[1]);
            callableStatement.registerOutParameter(6,Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){

                System.out.println(resultSet.getInt("N"));}
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
    }

    public void insertaBeneficiarios(Connection connection, int personaDoc, int cuentaNum, String parentescoNom,
                                     int porcentaje, String nombre, String fechaNac, int tel1, int tel2, String tipoDoc, String correo){
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

    public static void main(String[] args){
        Beneficiario beneficiario = new Beneficiario();
        String url = "jdbc:sqlserver://Asus-VivoBook15;databaseName=BDProyecto";
        try {
            Connection connection = DriverManager.getConnection(url,"JavaConexion","Admin");
            System.out.println("Conexion exitosa!");
            beneficiario.getListaTipoDocIden(connection);
            //beneficiario.insertaBeneficiarios(connection, 7777777, 11000001, "Hija", 200, "Tolebrio", "2029-12-12", 83374328, 88184967, "Cedula Nacional", "ppp@ppp");
        }
        catch (SQLException e) {
            System.out.println("Error al conectarse con la base de datos");
            e.printStackTrace();
        }

    }
}
