package CONTROLLER;

import conexion.Beneficiario;

import java.util.ArrayList;

public class ControllerUI {

    private Beneficiario beneficiario = new Beneficiario();
    private ControllerUsuario usuarioCon = new ControllerUsuario();

    public ControllerUI(){}

    public boolean verificarUsuario(String contra, String usuario){
        String contrasenna = beneficiario.getContrasenna(ControllerConexion.getInstance().connection, usuario);
        return contrasenna.equals(contra);
    }

    public ArrayList<String> devolverCuentas(String usuario){
        return usuarioCon.getVisibles(ControllerConexion.getInstance().connection, usuario);
    }

}
