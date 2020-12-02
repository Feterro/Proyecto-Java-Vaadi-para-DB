package VIEW;

import CONTROLLER.ControllerConexion;
import CONTROLLER.ControllerEstadosCuenta;
import MODEL.EstadoCuenta;

import java.awt.image.AreaAveragingScaleFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class pruebas {

    static ControllerEstadosCuenta estadosCuenta = new ControllerEstadosCuenta();
    public static void main(String[] args){
        ArrayList<EstadoCuenta> estados = estadosCuenta.obtenerEstadosCuenta(ControllerConexion.getInstance().connection, 11794632);
        ArrayList<Date> fechaInicio = new ArrayList<>();
        ArrayList<EstadoCuenta> estadosOrdenados =  new ArrayList<>();
        for (EstadoCuenta estado: estados){
            fechaInicio.add(estado.getFechaInicio());
        }
        Arrays.sort(new ArrayList[]{estados});
        for (Date fecha: fechaInicio){
            System.out.println(fecha);
        }
        for(Date fecha: fechaInicio){
           for (EstadoCuenta estado: estados){
               if(estado.getFechaInicio().equals(fecha)){
                   EstadoCuenta estad = new EstadoCuenta(estado.getNumero(), fecha, estado.getFechaFinal());
                   estadosOrdenados.add(estad);
               }
           }
        }
        for(EstadoCuenta e: estadosOrdenados){
            System.out.println(e.getNumero() + " " + e.getFechaInicio() + " " + e.getFechaFinal());
        }
    }
}
