package CONTROLLER;

import MODEL.EstadoCuenta;

import java.sql.*;
import java.util.ArrayList;

public class ControllerEstadosCuenta {

    public ControllerEstadosCuenta(){}

    public ArrayList<EstadoCuenta> obtenerEstadosCuenta(Connection connection, int cuentaId){
        ArrayList<EstadoCuenta> estadosCuenta = new ArrayList<>();
        try{
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_EC_ObtenerEstadosCuenta ?, ?");
            callableStatement.setInt(1, cuentaId);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                EstadoCuenta estado = new EstadoCuenta(resultSet.getInt("ID"), resultSet.getDate("fechaIni"), resultSet.getDate("fechaFin"), resultSet.getFloat("saldoIni"), resultSet.getFloat("saldoFin"));
                estadosCuenta.add(estado);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return estadosCuenta;
    }
}
