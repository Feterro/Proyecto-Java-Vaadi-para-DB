package CONTROLLER;

import MODEL.*;
import com.vaadin.ui.TabSheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ControllerUI {

    private TabSheet menu;

    private ArrayList<Beneficiario> beneficiarios; //igual al sp
    private ArrayList<String> numerosCuentaObjetivo;

    private ControllerBeneficiario beneficiarioCon = new ControllerBeneficiario();
    private ControllerUsuario usuarioCon = new ControllerUsuario();
    private ControllerEstadosCuenta estadosCuenta = new ControllerEstadosCuenta();
    private ControllerCuentaObjetivo cuentaObjetivo = new ControllerCuentaObjetivo();
    private ControllerMovimiento movimiento = new ControllerMovimiento();

    private String nombreUsuario;
    private int numCuenta;
    private String numCuentaOb;


    private static ControllerUI controllerUI;

    public ControllerUI(){}

    public static ControllerUI getInstance(){
        if(controllerUI == null){
            controllerUI = new ControllerUI();
        }
        return controllerUI;
    }

    public String getNumCuentaOb() {
        return numCuentaOb;
    }

    public void setNumCuentaOb(String numCuentaOb) {
        this.numCuentaOb = numCuentaOb;
    }

    public void setNumCuenta(int numCuenta) {
        this.numCuenta = numCuenta;
    }

    public void setMenu(TabSheet menu) {
        this.menu = menu;
    }

    public TabSheet getMenu() {
        return menu;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }


    public ArrayList<String> getNumerosCuentaObjetivo() {
        return numerosCuentaObjetivo;
    }

    public ArrayList<Beneficiario> getBeneficiarios() {
        return beneficiarios;
    }

    public boolean verificarUsuario(String contra, String usuario){
        String contrasenna = beneficiarioCon.getContrasenna(ControllerConexion.getInstance().connection, usuario);
        return contrasenna.equals(contra);
    }

    public ArrayList<String> devolverCuentas(){

        return usuarioCon.getVisibles(ControllerConexion.getInstance().connection, nombreUsuario);
    }

    public void setBeneficiarios(){
        ArrayList<String> cedulas = beneficiarioCon.getCedulasBeneficiarios(ControllerConexion.getInstance().connection, numCuenta);
        beneficiarios = new ArrayList<>();

        for (String ced: cedulas){
            Beneficiario beneficiario = beneficiarioCon.getBeneficiarios(ControllerConexion.getInstance().connection, Integer.parseInt(ced));
            beneficiarios.add(beneficiario);
        }
    }

    public ArrayList<Beneficiario> llenarTabla() {
        if (beneficiarios.size() < 3){
            int mas = 3 - beneficiarios.size();
            for (int i = 0; i < mas; i++){
                Beneficiario beneficiarioNoHay = new Beneficiario();
                beneficiarioNoHay.setValorDocIdent(0);
                beneficiarioNoHay.setNombre("Sin beneficiario");
                beneficiarioNoHay.setPorcentaje(0);
                beneficiarios.add(beneficiarioNoHay);
            }
        }
        return beneficiarios;
    }

    public int getCantActBene(){
        int cant = 0;

        for (Beneficiario ben: beneficiarios){
            if (ben.getValorDocIdent() != 0){
                cant++;
            }
        }

        return cant;
    }

    public int getPorcentajeUsado(){
        int porcentaje = 0;

        for (Beneficiario ben: beneficiarios){
            porcentaje = (porcentaje + ben.getPorcentaje());
        }

        return porcentaje;
    }

    public ArrayList<String> getParentezcos(){
        return beneficiarioCon.getParentescos(ControllerConexion.getInstance().connection);
    }

    public boolean AgregarBeneficiario(int cedula, String parentezco, float porcentaje){
        if (beneficiarioCon.insertaBeneficiarios(ControllerConexion.getInstance().connection, cedula, numCuenta, parentezco, (int) porcentaje) != 0){
            return false;
        }
        return true;
    }

    public boolean agregarBeneficiarioComplejo(int cedula, String parentezco, String nombre, String tipDocIdent, String fechaNac, int porcentaje, String email, int tel1, int tel2){
        if (beneficiarioCon.insertaBeneficiariosComplejo(ControllerConexion.getInstance().connection, cedula, numCuenta, parentezco, porcentaje, nombre, fechaNac, tel1, tel2, tipDocIdent, email) == 0){
            return true;
        }
        return false;
    }

    public ArrayList<String> getTiposDoc() {
        return beneficiarioCon.getTipoDoc(ControllerConexion.getInstance().connection);
    }

    public ArrayList<String> getCedulasBen(){
        return beneficiarioCon.getCedulasBeneficiarios(ControllerConexion.getInstance().connection, numCuenta);
    }

    public Beneficiario getBeneficiario(int ced){
        return beneficiarioCon.getBeneficiarios(ControllerConexion.getInstance().connection, ced);
    }

    public boolean actualizarBen(int cedulaOri, int cedulaN, String nombre, String parentezco, String fechaNa, String tipoDocIndent, int porcentaje,String correo, int tel1, int tel2){
        if (beneficiarioCon.modificaPersonas(ControllerConexion.getInstance().connection, cedulaOri, cedulaN, parentezco, porcentaje, nombre, fechaNa, tel1, tel2, tipoDocIndent, correo) == 0){
            return true;
        }
        return false;
    }

    public boolean eliminarBene(int cedula){
        int devolver = beneficiarioCon.eliminarBeneficiario(ControllerConexion.getInstance().connection, cedula);
        if(devolver == 0){
            return true;
        }
        return false;
    }



    public ArrayList<EstadoCuenta> getEstadosCuenta(){
        ArrayList<EstadoCuenta> estados = estadosCuenta.obtenerEstadosCuenta(ControllerConexion.getInstance().connection, numCuenta);
        ArrayList<Date> fechaInicio = new ArrayList<>();
        ArrayList<EstadoCuenta> estadosOrdenados =  new ArrayList<>();
        for (EstadoCuenta estado: estados){
            fechaInicio.add(estado.getFechaInicio());
        }
        Arrays.sort(new ArrayList[]{estados});
        for(Date fecha: fechaInicio){
            for (EstadoCuenta estado: estados){
                if(estado.getFechaInicio().equals(fecha)){
                    EstadoCuenta estad = new EstadoCuenta(estado.getNumero(), fecha, estado.getFechaFinal(), estado.getSaldoInicial(), estado.getSaldoFinal());
                    estadosOrdenados.add(estad);
                }
            }
        }
        int num = 1;
        for (EstadoCuenta estado: estadosOrdenados){
            estado.setNumero(num);
            num++;
        }
        return estadosOrdenados;
    }

    public ArrayList<Movimiento> getMovimientos(String fechaIn, String fechaFin){
        ArrayList<Movimiento> movimientos = movimiento.obtenerEstadosCuenta(ControllerConexion.getInstance().connection, numCuenta, fechaIn, fechaFin);
        return movimientos;
    }

    public ArrayList<Movimiento> getMovimientos(String  descripcion){
        ArrayList<Movimiento> movimientos = movimiento.filtro(descripcion);
        return movimientos;
    }


    public void setNumerosCuenta(){
        this.numerosCuentaObjetivo = cuentaObjetivo.obtenerNumerosCuentaObjetivo(ControllerConexion.getInstance().connection, numCuenta);
    }

    public ArrayList<CuentaObjetivo> verDetalles(){
        System.out.println("Aqui");
        ArrayList<CuentaObjetivo> cuentasActuales = new ArrayList<>();
        System.out.println("Cuentas: " + numerosCuentaObjetivo);
        for(String numCuenta: numerosCuentaObjetivo){
            System.out.println(numCuenta);
            CuentaObjetivo cuentaGuardar = cuentaObjetivo.verDetalles(ControllerConexion.getInstance().connection, numCuenta);
            System.out.println(cuentaGuardar.imprimir());
            cuentasActuales.add(cuentaGuardar);
        }
        return cuentasActuales;
    }

    public boolean crearCuentaObj(String objtivo, String fechaIni, String fechFin, float cuota){
        System.out.println("aqui");
        CuentaObjetivo cuentaNueva = new CuentaObjetivo();
        String numeroCuentaObj = cuentaNueva.generarNumero(numerosCuentaObjetivo);
        int codigo = cuentaObjetivo.crearCuentaObjetivo(ControllerConexion.getInstance().connection, numCuenta, objtivo, fechaIni, fechFin, cuota, numeroCuentaObj);
        if (codigo == 0){
            return true;
        }
        return false;
    }

    public CuentaObjetivo llenarCamposAct(String numCuenta){
        CuentaObjetivo cuenta = cuentaObjetivo.verDetalles(ControllerConexion.getInstance().connection, numCuenta);
        CuentaObjetivo actualizar = new CuentaObjetivo();
        actualizar.setObjetivo(cuenta.getObjetivo());
        actualizar.setFechaFinal(cuenta.getFechaFinal());
        actualizar.setCuota(cuenta.getCuota());
        actualizar.setFechaInicio(cuenta.getFechaInicio());
        return actualizar;
    }

    public boolean actualizarCuentaObj(String numCuenta, String objetivo, String fechaFin, float cuota){
        int codigo = cuentaObjetivo.actualizarCuentaObjetivo(ControllerConexion.getInstance().connection, numCuenta, objetivo, fechaFin, cuota);
        if (codigo == 0){
            return true;
        }
        return false;
    }

    public boolean desactivarCuentaObj(String cuentaNum){
        int codigo = cuentaObjetivo.desactivarCuentaObjetivo(ControllerConexion.getInstance().connection, cuentaNum);
        if (codigo == 0){
            return true;
        }
        return false;
    }

}
