package CONTROLLER;

import MODEL.Beneficiario;
import MODEL.BeneficiariosTabla;

import java.util.ArrayList;

public class ControllerUI {

    private ControllerBeneficiario beneficiarioCon = new ControllerBeneficiario();
    private ControllerUsuario usuarioCon = new ControllerUsuario();

    public ControllerUI(){}

    public boolean verificarUsuario(String contra, String usuario){
        String contrasenna = beneficiarioCon.getContrasenna(ControllerConexion.getInstance().connection, usuario);
        return contrasenna.equals(contra);
    }

    public ArrayList<String> devolverCuentas(String usuario){
        return usuarioCon.getVisibles(ControllerConexion.getInstance().connection, usuario);
    }

    public ArrayList<BeneficiariosTabla> llenarTabla(int cuenta){
        System.out.println("Entra");
        ArrayList<BeneficiariosTabla> beneficiarios = new ArrayList<>();
        ArrayList<String> cedulas = beneficiarioCon.getCedulasBeneficiarios(ControllerConexion.getInstance().connection, cuenta);
        System.out.println(cedulas);
        Beneficiario ben = beneficiarioCon.getBeneficiarios(ControllerConexion.getInstance().connection, Integer.parseInt(cedulas.get(1)));
        beneficiarios.add(new BeneficiariosTabla(ben.getNombre(), String.valueOf(ben.getValorDocIdent()), ben.getPorcentaje()));

        //        for (String ced: cedulas){
//            System.out.println("cedula: " + ced);
//            Beneficiario beneficiario = beneficiarioCon.getBeneficiarios(ControllerConexion.getInstance().connection, Integer.parseInt(ced));
//            System.out.println(beneficiario);
//            BeneficiariosTabla ben = new BeneficiariosTabla(beneficiario.getNombre(), String.valueOf(beneficiario.getValorDocIdent()), beneficiario.getPorcentaje());
//            beneficiarios.add(ben);
//        }
        return beneficiarios;
    }
}
