package VIEW;

import CONTROLLER.ControllerConexion;
import CONTROLLER.ControllerEstadosCuenta;
import MODEL.EstadoCuenta;

import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class pruebas {

    static ControllerEstadosCuenta estadosCuenta = new ControllerEstadosCuenta();
    public static void main(String[] args){
        while (true){
            Scanner input = new Scanner(System.in);
            System.out.print("Escriba: ");
            String number = input.next();
            System.out.println(number);
            Pattern pattern = Pattern.compile(number);
            if(pattern.matcher("hola").find()){
                System.out.println("Si");
            }
            else{
                System.out.println("no");
            }
        }
    }
}
