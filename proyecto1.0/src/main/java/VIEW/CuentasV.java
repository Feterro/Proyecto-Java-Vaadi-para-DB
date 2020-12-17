package VIEW;

import CONTROLLER.ControllerUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.util.ArrayList;

public class CuentasV{

    private ControllerUI controller = ControllerUI.getInstance();
    private BeneficiariosV beneficiariosV = BeneficiariosV.getInstance();
    private EstadCuentaV estadCuentaV = EstadCuentaV.getInstance();
    private CuentObjetivoV cuentObjetivoV = CuentObjetivoV.getInstance();

    private String numCuenta;
    private  ComboBox<String> cuentas;

    private static CuentasV cuentasV;

    public CuentasV(){}

    public static CuentasV getInstance(){
        if(cuentasV == null){
            cuentasV = new CuentasV();
        }
        return cuentasV;
    }


    public void Cuentas() {
        AbsoluteLayout cuentasContenedor = new AbsoluteLayout();
        cuentasContenedor.setWidth("1500px");
        cuentasContenedor.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoCuentas.png"));
        Image fondoCuentas = new Image("", resource);
        fondoCuentas.setWidth("1500px");
        fondoCuentas.setHeight("700px");

        cuentas = new ComboBox<>();
        ArrayList<String> cuentasNums = controller.devolverCuentas();
        cuentas.setItems(cuentasNums);
        cuentas.addStyleName(ValoTheme.LABEL_BOLD);
        cuentas.setPlaceholder("CUENTA A USAR");
        cuentas.setWidth("300px");


        Label lCuentas = new Label("SELECCIONE UNA CUENTA");
        lCuentas.addStyleName(ValoTheme.LABEL_H2);
        lCuentas.addStyleName(ValoTheme.LABEL_BOLD);

        Button seleccionar = new Button("SELECCIONAR");
        seleccionar.setIcon(VaadinIcons.CHECK_CIRCLE_O);
        seleccionar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        seleccionar.setHeight("50px");
        seleccionar.setWidth("300px");
        seleccionar.addClickListener(e-> SeleccionarCuenta());
        cuentasContenedor.addComponent(fondoCuentas, "top: 0px; left: 0px");
        cuentasContenedor.addComponent(cuentas, "top: 200px; left: 100px");
        cuentasContenedor.addComponent(lCuentas, "top: 50px; left: 100px");
        cuentasContenedor.addComponent(seleccionar, "top: 400px; left: 100px");

        controller.getMenu().addTab(cuentasContenedor, "CUENTAS");
    }

        public void SeleccionarCuenta() {
        numCuenta = cuentas.getSelectedItem().get();
        if (!numCuenta.equals("No seleccionado") || !cuentas.isEmpty()) {
            controller.setNumCuenta(Integer.parseInt(numCuenta));
            controller.setBeneficiarios();
            beneficiariosV.getCuentaL().setValue("Está en la cuenta numero " + numCuenta);
            Notification.show("USANDO CUENTA",numCuenta, Notification.Type.TRAY_NOTIFICATION);
            estadCuentaV.getCuenta().setValue("Está en la cuenta numero " + numCuenta);
            cuentObjetivoV.getCuentaOB().setValue("Está en la cuenta numero " + numCuenta);
            if (controller.getPorcentajeUsado() < 100){
                beneficiariosV.getPorcentajeL().setValue("No está usando el porcentaje completo para los beneficiarios");
            }
            else if (controller.getPorcentajeUsado() == 100) {
                beneficiariosV.getPorcentajeL().setVisible(false);
                beneficiariosV.getPorcentajeL().setValue("No está usando el porcentaje completo para los beneficiarios");
            }
            estadCuentaV.setEstadoCuentas(controller.getEstadosCuenta());
            estadCuentaV.setCantEstados(estadCuentaV.getEstadoCuentas().size());
            if (estadCuentaV.getEstadoCuentas().size() <=8){
                estadCuentaV.getEstados().setItems(estadCuentaV.getEstadoCuentas());
                estadCuentaV.setCantEstados(estadCuentaV.getCantEstados()-estadCuentaV.getEstadoCuentas().size());
                ArrayList<Integer> numeros = new ArrayList<>();
                for(int i = 0; i<estadCuentaV.getEstadoCuentas().size(); i++){
                    numeros.add(i+1);
                }
                estadCuentaV.getIds().setItems(numeros);
                estadCuentaV.getIds().setSelectedItem(1);
            }
            else{
                estadCuentaV.getEstados().setItems(estadCuentaV.getEstadoCuentas().subList(0,8));
                estadCuentaV.setCantEstados(estadCuentaV.getCantEstados() - 8);
                estadCuentaV.setEstadoActual(estadCuentaV.getEstadoActual() + 8);
            }
            controller.setNumerosCuenta();
            controller.getMenu().getTab(1).setEnabled(true);
            controller.getMenu().getTab(2).setEnabled(true);
            controller.getMenu().getTab(3).setEnabled(true);
        }
    }
}
