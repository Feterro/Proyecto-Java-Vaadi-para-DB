package CONTROLLER;

import MODEL.BeneficiarioConsulta;
import MODEL.consultaCuentasObjetivo;
import MODEL.tablaMultas;

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

    public ArrayList<tablaMultas> consulta2(Connection connection, int cantDias){
        ArrayList<tablaMultas> cuentas = new ArrayList<>();

        try{
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_AD_Consulta2 ?, ?");
            callableStatement.setInt(1,cantDias);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String fecha = String.valueOf(resultSet.getInt("mes")) + resultSet.getInt("ano");
                tablaMultas tabla = new tablaMultas(resultSet.getInt("numCuenta"), resultSet.getFloat("promedio"), fecha);
                cuentas.add(tabla);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return cuentas;
    }

    public ArrayList<BeneficiarioConsulta> consulta3(Connection connection){
        ArrayList<BeneficiarioConsulta> beneficiarios = new ArrayList<>();
        try{
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_AD_Consulta3 ?");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                BeneficiarioConsulta ben = new BeneficiarioConsulta(resultSet.getString("nombre"),
                        resultSet.getInt("cedula"), resultSet.getFloat("cantDineroRec"),
                        resultSet.getInt("numCuentaMas"), resultSet.getInt("cantCuentasBen"));
                beneficiarios.add(ben);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return beneficiarios;
    }
}
