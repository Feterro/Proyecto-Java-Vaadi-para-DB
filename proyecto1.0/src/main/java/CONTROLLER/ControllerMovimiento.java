package CONTROLLER;
import MODEL.EstadoCuenta;
import MODEL.Movimiento;

import java.sql.*;
import java.util.ArrayList;


public class ControllerMovimiento {

    public ControllerMovimiento(){}

    public ArrayList<Movimiento> obtenerEstadosCuenta(Connection connection, int cuentaNum, String fechaInicio, String fechaFin){
        ArrayList<Movimiento> movimientos = new ArrayList<>();
        try{
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_EC_ObtenerEstadosCuenta ?,?,?,?");
            callableStatement.setDate(1, Date.valueOf(fechaInicio));
            callableStatement.setDate(2, Date.valueOf(fechaFin));
            callableStatement.setInt(3, cuentaNum);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Movimiento movimiento = new Movimiento(resultSet.getDate("fechaMovimiento"), resultSet.getString("descripcion"), resultSet.getString("nombre"), resultSet.getFloat("monto"));
                movimientos.add(movimiento);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return movimientos;
    }

}
