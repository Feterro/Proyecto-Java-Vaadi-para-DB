package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conectividad {





    public static void main(String[] args){
        String url = "jdbc:sqlserver:pc-fabrizio;databaseName=BDProyecto";
        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conexion exitosa!");

        }
        catch (SQLException e) {
            System.out.println("Error al conectarse con la base de datos");
            e.printStackTrace();
        }

    }
}
