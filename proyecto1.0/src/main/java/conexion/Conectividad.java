package conexion;
import java.sql.*;
import java.util.ArrayList;

public class Conectividad {





    public static void main(String[] args){
        String url = "jdbc:sqlserver://pc-fabrizio;databaseName=BDProyecto";
        try {
            Connection connection = DriverManager.getConnection(url,"JavaConexion","Admin");
            System.out.println("Conexion exitosa!");

            ArrayList<String> listaParentezcos = new ArrayList<String>();
            try {

                PreparedStatement preparedStatement = connection.prepareStatement("EXEC SP_PA_SolicitaParentezcos ?");
                preparedStatement.setInt(1,1);
                ResultSet resultSet = preparedStatement.executeQuery();


            }
            catch (Exception ex){
                System.out.println("ERROR!");
            }


        }
        catch (SQLException e) {
            System.out.println("Error al conectarse con la base de datos");
            e.printStackTrace();
        }

    }
}
