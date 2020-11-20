package conexion;
import java.sql.*;
import java.util.ArrayList;
import java.net.InetAddress;

public class Beneficiario extends Persona {

    private int porcentaje;

    public int cuenta;
    public String parentesco;
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

    public ArrayList<String> getListaParentescos(Connection connection){
        ArrayList<String> listaParentezcos = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_PA_SolicitaParentezcos ?");
            callableStatement.registerOutParameter(1,Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                listaParentezcos.add(resultSet.getString("nombre"));
                System.out.println(resultSet.getString("nombre"));

            }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
        return listaParentezcos;
    }

    public ArrayList<String> getListaTipoDocIden(Connection connection){
        ArrayList<String> listaTip = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_TDI_SolicitaTiposDocIdent ?");
            callableStatement.registerOutParameter(1,Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                listaTip.add(resultSet.getString("tipoDoc"));
                System.out.println(resultSet.getString("tipoDoc"));

            }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
        return listaTip;
    }

    public ArrayList<String> getVisibles(Connection connection, String nomUsuario){
        ArrayList<String> listaVis = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_PV_SolicitaVisibles ?, ?");
            callableStatement.setString(1, nomUsuario);
            callableStatement.registerOutParameter(2,Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                listaVis.add(resultSet.getString("numeroCuenta"));
                System.out.println(resultSet.getInt("numeroCuenta"));
            }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
        return listaVis;
    }

    public ArrayList<String> getCedulasBeneficiarios(Connection connection, int numCuenta){
        ArrayList<String> listaBenCed = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_EC_ObtenerCedulasBeneficiarios ?, ?");
            callableStatement.setInt(1, numCuenta);
            callableStatement.registerOutParameter(2,Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                int dev = resultSet.getInt("valorDocIdent");
                listaBenCed.add(String.valueOf(dev));

                System.out.println(resultSet.getString("valorDocIdent"));
            }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
        return listaBenCed;
    }

    public String getContrasenna(Connection connection, String nomUsuario){
        String res = "";
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_US_SolicitaContrasenna ?, ?");
            callableStatement.setString(1, nomUsuario);
            callableStatement.registerOutParameter(2,Types.VARCHAR);
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

    public  void eliminarBeneficiario(Connection connection, int docIdent){

        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_BE_EliminarBeneficiario ?, ?");
            callableStatement.setInt(1, docIdent);
            callableStatement.registerOutParameter(2,Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getString("N"));
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

    public Beneficiario getBeneficiarios(Connection conection, int docIdent){
        Beneficiario beneficiari= new Beneficiario();
        try {
            String ip = InetAddress.getLocalHost().toString();
            String[] ipDividido =  ip.split("/");
            CallableStatement callableStatement = conection.prepareCall("SP_BE_SolicitarPersonas ?,?");
            callableStatement.setInt(1, docIdent);
            callableStatement.registerOutParameter(2,Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                beneficiari.setNombre(resultSet.getString("nombre"));
                beneficiari.setEmail(resultSet.getString("email"));
                beneficiari.setFechaNac(resultSet.getString("nacimiento"));
                beneficiari.setTel1(resultSet.getInt("telefono1"));
                beneficiari.setTel2(resultSet.getInt("telefono2"));
                beneficiari.setValorDocIdent(resultSet.getInt("valorDocIdent"));
                beneficiari.setPorcentaje(resultSet.getInt("porcentaje"));
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
        return beneficiari;
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

    public Connection getConection(){
        String url = "jdbc:sqlserver://localhost:1599;database=BDProyecto";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,"JavaConexion","Admin");
            System.out.println("Conexion exitosa!");
        }
        catch (SQLException e) {
            System.out.println("Error al conectarse con la base de datos");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args){
        Beneficiario beneficiario = new Beneficiario();
//        beneficiario.getBeneficiarios();
        beneficiario.getVisibles(Conector.getInstance().connection, "fquiros");
//        String url = "jdbc:sqlserver://localhost:1599;database=BDProyecto";
//        try {
//            Connection connection = DriverManager.getConnection(url,"JavaConexion","Admin");
//            System.out.println("Conexion exitosa!");
//        }
//        catch (SQLException e) {
//            System.out.println("Error al conectarse con la base de datos");
//            e.printStackTrace();
//        }
    }
}