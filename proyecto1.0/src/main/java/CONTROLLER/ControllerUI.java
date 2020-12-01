package CONTROLLER;

import MODEL.Beneficiario;
import MODEL.BeneficiariosTabla;

import java.util.ArrayList;

public class ControllerUI {


    private ArrayList<BeneficiariosTabla> beneficiarios; //igual al sp

    private ControllerBeneficiario beneficiarioCon = new ControllerBeneficiario();
    private ControllerUsuario usuarioCon = new ControllerUsuario();

    public ControllerUI(){}

    public ArrayList<BeneficiariosTabla> getBeneficiarios() {
        return beneficiarios;
    }

    public boolean verificarUsuario(String contra, String usuario){
        String contrasenna = beneficiarioCon.getContrasenna(ControllerConexion.getInstance().connection, usuario);
        return contrasenna.equals(contra);
    }

    public ArrayList<String> devolverCuentas(String usuario){
        return usuarioCon.getVisibles(ControllerConexion.getInstance().connection, usuario);
    }

    public void setBeneficiarios(int cuenta){
        ArrayList<String> cedulas = beneficiarioCon.getCedulasBeneficiarios(ControllerConexion.getInstance().connection,cuenta);
        beneficiarios = new ArrayList<>();

        for (String ced: cedulas){
            Beneficiario beneficiario = beneficiarioCon.getBeneficiarios(ControllerConexion.getInstance().connection, Integer.parseInt(ced));
            BeneficiariosTabla ben = new BeneficiariosTabla(beneficiario.getNombre(), String.valueOf(beneficiario.valorDocIdent), beneficiario.getPorcentaje());
            beneficiarios.add(ben);
        }
    }

    public ArrayList<BeneficiariosTabla> llenarTabla(int cuenta) {
        setBeneficiarios(cuenta);
        if (beneficiarios.size() < 3){
            int mas = 3 - beneficiarios.size();
            for (int i = 0; i < mas; i++){
                BeneficiariosTabla beneficiarioNoHay = new BeneficiariosTabla("Sin beneficiario", "0",0);
                beneficiarios.add(beneficiarioNoHay);
            }
        }
        return beneficiarios;
    }

    public int getCantActBene(ArrayList<BeneficiariosTabla> beneficiarios){
        int cant = 0;

        for (BeneficiariosTabla ben: beneficiarios){
            if (!ben.getdocumentoIdentidad().equals("0")){
                cant++;
            }
        }

        return cant;
    }

    public float getPorcentajeUsado(ArrayList<BeneficiariosTabla> beneficiarios){
        float porcentaje = 0;

        for (BeneficiariosTabla ben: beneficiarios){
            porcentaje = porcentaje + ben.getPorcentaje();
        }

        return porcentaje;
    }

    public ArrayList<String> getParentezcos(){
        return beneficiarioCon.getParentescos(ControllerConexion.getInstance().connection);
    }

    public boolean AgregarBeneficiario(int cedula, String parentezco, float porcentaje, int numeroCuen){
        if (beneficiarioCon.insertaBeneficiarios(ControllerConexion.getInstance().connection, cedula, numeroCuen, parentezco, (int) porcentaje) != 0){
            return false;
        }
        return true;
    }
}
