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

    public static void main(String[] args){
        Beneficiario beneficiario = new Beneficiario();
        String url = "jdbc:sqlserver://pc-fabrizio;databaseName=BDProyecto";
        try {
            Connection connection = DriverManager.getConnection(url,"JavaConexion","Admin");
            System.out.println("Conexion exitosa!");
            //beneficiario.getListaParentescos(connection);
            beneficiario.insertaBeneficiarios(connection, 117370445, 11000001, "Hija",100);
        }
        catch (SQLException e) {
            System.out.println("Error al conectarse con la base de datos");
            e.printStackTrace();
        }

    }
}
