package conexion;
import java.sql.*;
import java.util.ArrayList;
import java.net.InetAddress;

public class Beneficiario extends Persona {

    private int porcentaje;

    private int cuenta;
    private String parentesco;
    private ArrayList<String> listaParentescos;
    private ArrayList<String> listaTipoDoc;
    private ArrayList<Integer> listaCuentasVisibles;
    private boolean activo;

    public Beneficiario() {
        this.porcentaje = 0;
        this.cuenta = 1;
        this.parentesco = "x";
        this.listaParentescos = new ArrayList<>();
        this.listaCuentasVisibles = new ArrayList<>();
        this.listaTipoDoc = new ArrayList<>();
        this.activo = true;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
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

    public ArrayList<String> getListaParentescos() {
        return listaParentescos;
    }

    public void setListaParentescos(ArrayList<String> listaParentescos) {
        this.listaParentescos = listaParentescos;
    }

    public ArrayList<String> getListaTipoDoc() {
        return listaTipoDoc;
    }

    public void setListaTipoDoc(ArrayList<String> listaTipoDoc) {
        this.listaTipoDoc = listaTipoDoc;
    }

    public ArrayList<Integer> getListaCuentasVisibles() {
        return listaCuentasVisibles;
    }

    public void setListaCuentasVisibles(ArrayList<Integer> listaCuentasVisibles) {
        this.listaCuentasVisibles = listaCuentasVisibles;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
                setListaParentescos(listaParentezcos);
                 }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }

    }

    public void getListaTipoDocIden(Connection connection){
        ArrayList<String> listaTip = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_TDI_SolicitaTiposDocIdent ?");
            callableStatement.registerOutParameter(1,Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                listaTip.add(resultSet.getString("tipoDoc"));
                System.out.println(resultSet.getString("tipoDoc"));
                setListaTipoDoc(listaTip);
            }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
    }

    public void getVisibles(Connection connection, String nomUsuario){
        ArrayList<String> listaVis = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_PV_SolicitaVisibles ?, ?");
            callableStatement.setString(1, nomUsuario);
            callableStatement.registerOutParameter(2,Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                listaVis.add(resultSet.getString("numeroCuenta"));
                System.out.println(resultSet.getString("numeroCuenta"));
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
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_BE_InsertaBeneficiario ?, ?, ?, ?, ?, ?");
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
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_BE_InsertaBeneficiarioComplejo ?,?,?,?,?,?,?,?,?,?,?,?");
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
    public void getBeneficiarios(Connection connection){
        try {
            String ip = InetAddress.getLocalHost().toString();
            String[] ipDividido =  ip.split("/");
            CallableStatement callableStatement = connection.prepareCall("SP_BE_SolicitarPersonas ?");
            callableStatement.registerOutParameter(1,Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                setNombre(resultSet.getString("nombre"));
                setEmail(resultSet.getString("email"));
                setFechaNac(resultSet.getString("nacimiento"));
                setTel1(resultSet.getInt("telefono1"));
                setTel2(resultSet.getInt("telefono2"));
                setValorDocIdent(resultSet.getInt("valorDocIdent"));
                setPorcentaje(resultSet.getInt("porcentaje"));
                System.out.println(resultSet.getString("nombre"));
                System.out.println(resultSet.getString("email"));
                System.out.println(resultSet.getString("nacimiento"));
                System.out.println(resultSet.getInt("telefono1"));
                System.out.println(resultSet.getInt("telefono2"));
                System.out.println(resultSet.getInt("valorDocIdent"));
                System.out.println(resultSet.getInt("porcentaje"));
            }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
    }

    public void modificaPersonas(Connection connection, int personaDocOri, int personaDoc, String parentescoNom,
                                     int porcentaje, String nombre, String fechaNac, int tel1, int tel2, String tipoDoc, String correo){
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

    public ArrayList<EstadoCuenta> obtenerEstadosCuenta(Connection connection, int cuentaId){
        ArrayList<EstadoCuenta> estadosCuenta = new ArrayList<>();
        try{
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_EC_ObtenerEstadosCuenta ?, ?");
            callableStatement.setInt(1, cuentaId);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                EstadoCuenta estado = new EstadoCuenta();
                estado.setFecha_Inicio(resultSet.getDate("fechaIni"));
                estado.setFecha_Final(resultSet.getDate("fechaFin"));
                estadosCuenta.add(estado);
                System.out.println("Estado: " + estado.getFecha_Final() + " " + estado.getFecha_Inicio());

            }

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return estadosCuenta;
    }

    public static void main(String[] args){
        Beneficiario beneficiario = new Beneficiario();
        String url = "jdbc:sqlserver://localhost:1433;database=BDProyecto";
        try {
            Connection connection = DriverManager.getConnection(url,"BDP","gatoscools");
            System.out.println("Conexion exitosa!");
            ArrayList<EstadoCuenta> estd = new ArrayList<>();
            estd = beneficiario.obtenerEstadosCuenta(connection, 11000001);
//            beneficiario.getBeneficiarios(connection);
            //beneficiario.modificaPersonas(connection, 7777777, 1212121212, "Hijo", 20, "Francesco Virgolini", "2029-12-12", 11111111, 22222222, "Cedula Nacional", "ppp@ppp");
        }
        catch (SQLException e) {
            System.out.println("Error al conectarse con la base de datos");
            e.printStackTrace();
        }

    }
}
