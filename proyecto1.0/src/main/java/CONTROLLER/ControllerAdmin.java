package CONTROLLER;

import MODEL.consultaCuentasObjetivo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

public class ControllerAdmin {

    public ControllerAdmin() {}

    public ArrayList<consultaCuentasObjetivo> consulta1(Connection connection){
        ArrayList<consultaCuentasObjetivo> cuentas = new ArrayList<>();

        try{
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_AD_Consulta1 ?");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                consultaCuentasObjetivo cuenta = new consultaCuentasObjetivo(resultSet.getInt("codigo"),
                        resultSet.getInt("IDCO"), resultSet.getString("descripcion"),
                        resultSet.getInt("cantDepR"), resultSet.getInt("cantDepH"),
                        resultSet.getFloat("totalR"), resultSet.getFloat("totalH"),
                        resultSet.getFloat("interAcuR"), resultSet.getFloat("interAcuH"));
                cuentas.add(cuenta);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return cuentas;
    }

    public static void main(String[] args){
        ControllerAdmin admin = new ControllerAdmin();
        ArrayList<consultaCuentasObjetivo> con = admin.consulta1(ControllerConexion.getInstance().connection);
        for(consultaCuentasObjetivo c: con){
            c.imprimir();
        }

    }
}
