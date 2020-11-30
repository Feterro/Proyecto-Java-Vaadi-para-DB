package CONTROLLER;

import MODEL.Beneficiario;
import MODEL.BeneficiariosTabla;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class ControllerUI {

    private ArrayList<BeneficiariosTabla> beneficiarios = mientras(); //igual al sp

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

    public ArrayList<BeneficiariosTabla> mientras(){
        ArrayList<BeneficiariosTabla> beneficiarios = new ArrayList<>();
        BeneficiariosTabla ben1 = new BeneficiariosTabla("Pedro Ignacio Solano", "12456", 10);
        beneficiarios.add(ben1);
        return beneficiarios;
    }

    public ArrayList<BeneficiariosTabla> llenarTabla(int cuenta) {
        mientras();
        //agregar con el sp
//        BeneficiariosTabla ben2 = new BeneficiariosTabla("Crystel Montero Obando", "5431215", 51);
//        beneficiarios.add(ben2);
//        BeneficiariosTabla ben3 = new BeneficiariosTabla("Fabrizio guapo Ferreto", "45456", 25);
//        beneficiarios.add(ben3);

        if (beneficiarios.size() < 3){
            int mas = 3 - beneficiarios.size();
            for (int i = 0; i < mas; i++){
                BeneficiariosTabla beneficiarioNoHay = new BeneficiariosTabla("Sin beneficiario", "0",0);
                beneficiarios.add(beneficiarioNoHay);
            }
        }
        return beneficiarios;
    }

    public int cantBene(ArrayList<BeneficiariosTabla> beneficiarios){
        int cant = 0;

        for (BeneficiariosTabla ben: beneficiarios){
            if (!ben.getdocumentoIdentidad().equals("0")){
                cant++;
            }
        }

        return cant;
    }

    public float porcentaje(ArrayList<BeneficiariosTabla> beneficiarios){
        float porcentaje = 0;

        for (BeneficiariosTabla ben: beneficiarios){
            porcentaje += porcentaje+ben.getPorcentaje();
        }

        return porcentaje;
    }

    public ArrayList<String> Parentezcos(){
        return beneficiarioCon.getListaParentescos(ControllerConexion.getInstance().connection);
    }
}
