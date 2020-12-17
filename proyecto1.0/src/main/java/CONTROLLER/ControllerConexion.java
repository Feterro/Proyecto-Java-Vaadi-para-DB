package CONTROLLER;
import java.sql.*;

public class ControllerConexion {

    private static ControllerConexion conexion;
    public Connection connection;

    private ControllerConexion() {
        crearConexion();
    }

    public static ControllerConexion getInstance(){
        if(conexion == null){
            conexion = new ControllerConexion();
        }
        return conexion;
    }

    public void  crearConexion(){
        String url = "jdbc:sqlserver://localhost:1433;database=BDProyecto";
        try {
            Connection connection = DriverManager.getConnection(url,"BDP","gatoscools");
            System.out.println("Conexion exitosa!");
            this.connection = connection;
        }
        catch (SQLException e) {
            System.out.println("Error al conectarse con la base de datos");
            e.printStackTrace();
        }
    }
}
