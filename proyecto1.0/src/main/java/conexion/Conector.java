package conexion;
import java.sql.*;

public class Conector {

    private static Conector conexion;
    public Connection connection;

    private Conector() {
        crearConexion();
    }

    public static Conector getInstance(){
        if(conexion == null){
            conexion = new Conector();
        }
        return conexion;
    }

    public void  crearConexion(){
        String url = "jdbc:sqlserver://localhost:1599;database=BDProyecto";
        try {
            Connection connection = DriverManager.getConnection(url,"JavaConexion","Admin");
            System.out.println("Conexion exitosa!");
            this.connection = connection;
        }
        catch (SQLException e) {
            System.out.println("Error al conectarse con la base de datos");
            e.printStackTrace();
        }
    }
}
