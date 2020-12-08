package CONTROLLER;

import MODEL.Movimiento;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.regex.*;
import java.sql.*;
import java.util.ArrayList;


public class ControllerMovimiento {

    public ControllerMovimiento(){}

    private ArrayList<Movimiento> movimientosCajero = new ArrayList<>();

    public ArrayList<Movimiento> getMovimientosCajero() {
        return movimientosCajero;
    }


    public ArrayList<Movimiento> obtenerEstadosCuenta(Connection connection, int cuentaNum, String fechaInicio, String fechaFin){
        ArrayList<Movimiento> movimientos = new ArrayList<>();
        try{
            CallableStatement callableStatement = connection.prepareCall("EXEC SP_MO_ObtenerMovimientos ?,?,?,?");
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
        this.movimientosCajero = movimientos;
        return movimientos;
    }


    public ArrayList<Movimiento> filtro(String filtro){
        filtro = filtro.toLowerCase();
        ArrayList<Movimiento> movimientos = this.movimientosCajero;
        ArrayList<Movimiento> movsFiltro = new ArrayList<>();
        Pattern pattern = Pattern.compile(filtro);
        for (Movimiento movimiento: movimientos){
            if(pattern.matcher(movimiento.getDescripcion().toLowerCase()).find()){
                movsFiltro.add(movimiento);
            }
        }
        return movsFiltro;
    }
}
