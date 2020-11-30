package CONTROLLER;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

public class ControllerUsuario {

    public ControllerUsuario(){}

    public ArrayList<String> getVisibles(Connection connection, String nomUsuario){
        ArrayList<String> listaVis = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_PV_SolicitaVisibles ?, ?");
            callableStatement.setString(1, nomUsuario);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                listaVis.add(resultSet.getString("numeroCuenta"));
//                System.out.println(resultSet.getInt("numeroCuenta"));
            }
        }
        catch (Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
        return listaVis;
    }
}
