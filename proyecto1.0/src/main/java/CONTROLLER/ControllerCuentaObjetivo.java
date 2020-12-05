package CONTROLLER;

import MODEL.CuentaObjetivo;
import com.vaadin.ui.AbsoluteLayout;

import java.sql.*;
import java.util.ArrayList;

public class ControllerCuentaObjetivo {

    public ControllerCuentaObjetivo(){}

    public int actualizarCuentaObjetivo(Connection conection, String numCuenta, String objetivo, Date fechaFin, float cuota) {
        int devolver = 0;
        try {
            CallableStatement callableStatement = conection.prepareCall("SP_CO_actualizarCuentaObjetivo ?, ?, ?, ?, ?");
            callableStatement.setString(1, objetivo);
            callableStatement.setDate(2, fechaFin);
            callableStatement.setFloat(3, cuota);
            callableStatement.setString(4, numCuenta);
            callableStatement.registerOutParameter(5, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                devolver = resultSet.getInt("N");
            }
        } catch (Exception e) {
            devolver = 1;
            e.printStackTrace();
        }
        return devolver;
    }

    public int crearCuentaObjetivo(Connection connection, int numCuentaAso, String objetivo, Date fechaInicio, Date fechaFin, float cuota, String numCuenta){
        int devolver = 0;

        try {
            CallableStatement callableStatement = connection.prepareCall("SP_CO_CrearCuentaObjetivo ?, ?, ?, ?, ?, ?, ?");
            callableStatement.setInt(1, numCuentaAso);
            callableStatement.setString(2, objetivo);
            callableStatement.setDate(3, fechaInicio);
            callableStatement.setDate(4, fechaFin);
            callableStatement.setFloat(5, cuota);
            callableStatement.setString(6, numCuenta);
            callableStatement.registerOutParameter(7, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                devolver = resultSet.getInt("N");
            }

        }
        catch (Exception e){
            devolver = 1;
            e.printStackTrace();
        }

        return devolver;
    }

    public int desactivarCuentaObjetivo(Connection connection, String cuentaNum){
        int devolver = 0;

        try{
            CallableStatement callableStatement = connection.prepareCall("SP_CO_DesactivarCuenta ?, ?");
            callableStatement.setString(1, cuentaNum);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                devolver = resultSet.getInt("N");
            }
        }
        catch (Exception e){
            devolver = 1;
            e.printStackTrace();
        }
        return devolver;
    }

    public ArrayList<String> obtenerNumerosCuentaObjetivo(Connection connection, int numeroCuentaAso){
        ArrayList<String> numerosCuenta = new ArrayList<>();

        try{
            CallableStatement callableStatement = connection.prepareCall("SP_CO_ObtenerNumerosCuentaObjetivo ?, ?");
            callableStatement.setInt(1, numeroCuentaAso);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()){
                String numCuenta = resultSet.getString("numeroCuenta");
                numerosCuenta.add(numCuenta);
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }

        return numerosCuenta;
    }

    public CuentaObjetivo verDetalles(Connection connection, String cuentaOb){
        CuentaObjetivo cuentaObjetivo =  new CuentaObjetivo();
        try {
            CallableStatement callableStatement = connection.prepareCall("SP_CO_VerDetallesCuentaObjetivo ?, ?");
            callableStatement.setString(1, cuentaOb);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                cuentaObjetivo.setNumCuenta(resultSet.getString("numeroCuenta"));
                cuentaObjetivo.setCuota(resultSet.getFloat("cuota"));
                cuentaObjetivo.setFechaInicio(resultSet.getDate("fechaIn"));
                cuentaObjetivo.setFechaFinal(resultSet.getDate("fechaFin"));
                cuentaObjetivo.setSaldo(resultSet.getFloat("saldo"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return cuentaObjetivo;
    }

}
