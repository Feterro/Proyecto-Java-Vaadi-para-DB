package conexion;
import java.sql.*;
import java.util.ArrayList;

public class Conectividad {





    public static void main(String[] args){
        String url = "jdbc:sqlserver://pc-fabrizio;databaseName=BDProyecto";
        try {
            Connection connection = DriverManager.getConnection(url,"JavaConexion","Admin");
            System.out.println("Conexion exitosa!");

            ArrayList<String> listaParentezcos = new ArrayList<>();
            try {

                CallableStatement callableStatement = connection.prepareCall("EXEC SP_PA_SolicitaParentezcos ?");
                callableStatement.registerOutParameter(1,Types.VARCHAR);
                ResultSet resultSet = callableStatement.executeQuery();
                while(resultSet.next()){

                    listaParentezcos.add(resultSet.getString("nombre"));
                }

            }
            catch (Exception ex){
                System.out.println("ERROR!");
                ex.printStackTrace();
            }


        }
        catch (SQLException e) {
            System.out.println("Error al conectarse con la base de datos");
            e.printStackTrace();
        }

    }
}
