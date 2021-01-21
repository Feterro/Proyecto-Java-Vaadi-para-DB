package VIEW;

import CONTROLLER.ControllerUI;
import MODEL.consultaCuentasObjetivo;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;

public class detallesCuentaObjetivo extends AbsoluteLayout implements View {

    private ControllerUI controller = ControllerUI.getInstance();

    public detallesCuentaObjetivo() {
        ventana();
    }

    private void ventana() {

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondo.png"));
        Image fondoTotal = new Image("", resource);
        fondoTotal.setSizeFull();

        FileResource resource1 = new FileResource(new File("src/main/java/VIEW/Imagenes/fondoConsCODe.png"));
        Image fondoConsu = new Image("", resource1);
        fondoConsu.setWidth("1500px");
        fondoConsu.setHeight("700px");

        consultaCuentasObjetivo cuenta = null;
        for(consultaCuentasObjetivo cuen: controller.getDetalles()){
            if (cuen.getNumCuenta() == Integer.parseInt(controller.getNumCuentaOb())){
                cuenta = cuen;
            }
        }

        HorizontalLayout fondo = new HorizontalLayout();
        fondo.setSizeFull();

        AbsoluteLayout detalles = new AbsoluteLayout();
        detalles.setWidth("1500px");
        detalles.setHeight("700px");

        Label detalle = new Label("DETALLES");
        detalle.addStyleName(ValoTheme.LABEL_H1);
        detalle.addStyleName(ValoTheme.LABEL_BOLD);

        Label numCuenta = new Label("Numero de cuenta");
        numCuenta.addStyleName(ValoTheme.LABEL_H3);
        Label numero = new Label();
        numero.addStyleName(ValoTheme.LABEL_H4);
        numero.setValue(controller.getNumCuentaOb());

        Label IDco = new Label("ID");
        IDco.addStyleName(ValoTheme.LABEL_H3);
        Label id = new Label(String.valueOf(cuenta.getIdCO()));
        id.addStyleName(ValoTheme.LABEL_H4);

        Label objetivo = new Label("Objetivo");
        objetivo.addStyleName(ValoTheme.LABEL_H3);
        Label desc = new Label(cuenta.getObjetivo());
        desc.addStyleName(ValoTheme.LABEL_H4);

        Label real = new Label("Depositos reales");
        real.addStyleName(ValoTheme.LABEL_H2);

        Label seHubiera = new Label("Depositos completos");
        seHubiera.addStyleName(ValoTheme.LABEL_H2);

        Label cantDepositos = new Label("Cantidad depositos");
        cantDepositos.addStyleName(ValoTheme.LABEL_H3);
        Label cantReal = new Label(String.valueOf(cuenta.getCantDepReal()));
        cantReal.addStyleName(ValoTheme.LABEL_H4);
        Label cantHubiera = new Label(String.valueOf(cuenta.getCantDepH()));
        cantHubiera.addStyleName(ValoTheme.LABEL_H4);

        Label totalDebitado = new Label("Total debitado");
        totalDebitado.addStyleName(ValoTheme.LABEL_H3);

        float conInteresReal = cuenta.getTotalReal() + cuenta.getInterR();

        Label realDSI = new Label(String.valueOf(conInteresReal));
        realDSI.addStyleName(ValoTheme.LABEL_H4);


        Label conInteresDR = new Label("Con interéses");
        conInteresDR.addStyleName(ValoTheme.LABEL_H4);
        conInteresDR.addStyleName(ValoTheme.LABEL_COLORED);

        Label realDCI = new Label(String.valueOf(cuenta.getTotalReal()));
        realDCI.addStyleName(ValoTheme.LABEL_H4);

        Label sinInteresDR = new Label("Sin interéses");
        sinInteresDR.addStyleName(ValoTheme.LABEL_H4);
        sinInteresDR.addStyleName(ValoTheme.LABEL_COLORED);

        Label hubieraDSI = new Label(String.valueOf(cuenta.getTotalH()));
        hubieraDSI.addStyleName(ValoTheme.LABEL_H4);

        Label conInteresH = new Label("Con interéses");
        conInteresH.addStyleName(ValoTheme.LABEL_H4);
        conInteresH.addStyleName(ValoTheme.LABEL_COLORED);

        float totalHCi = cuenta.getTotalH() + cuenta.getInterH();

        Label hubieraDCI = new Label(String.valueOf(totalHCi));
        hubieraDCI.addStyleName(ValoTheme.LABEL_H4);

        Label sinInteresH = new Label("Sin interéses");
        sinInteresH.addStyleName(ValoTheme.LABEL_H4);
        sinInteresH.addStyleName(ValoTheme.LABEL_COLORED);

        Button atras = new Button("");
        atras.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        atras.setIcon(VaadinIcons.ARROW_BACKWARD);
        atras.setWidth("50px");
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(e->{
            Navigator navigator = new Navigator(UI.getCurrent(), this);
            navigator.addView("ConsultaCO", ConsultaCuentasObj.class);
            navigator.navigateTo("ConsultaCO");
        });


        detalles.addComponent(fondoConsu);
        detalles.addComponent(detalle, "top: 10px; left: 650px");
        detalles.addComponent(numCuenta, "top: 100px; left: 650px;");
        detalles.addComponent(numero, "top: 125px; left: 700px");
        detalles.addComponent(IDco, "top: 100px; left: 375px");
        detalles.addComponent(id, "top: 125px; left: 375");
        detalles.addComponent(objetivo, "top: 100x; right: 350px");
        detalles.addComponent(desc, "top: 125px; right: 350px");
        detalles.addComponent(real, "top: 200px; left: 525px");
        detalles.addComponent(seHubiera, "top: 200px; right: 425px");
        detalles.addComponent(cantDepositos, "top: 300px; left: 325px");
        detalles.addComponent(cantReal, "top: 300px; left: 615px");
        detalles.addComponent(cantHubiera, "top: 300px; right: 525px");
        detalles.addComponent(totalDebitado, "top: 400px; left: 325px");
        detalles.addComponent(realDSI, "top: 400px; left: 500px");
        detalles.addComponent(conInteresDR, "top: 450px; left: 490px");
        detalles.addComponent(realDCI, "top: 400px; left:650px");
        detalles.addComponent(sinInteresDR, "top: 450px; left: 640px");
        detalles.addComponent(hubieraDSI, "top: 400px; right: 400px");
        detalles.addComponent(hubieraDCI, "top: 400px; right: 550px");
        detalles.addComponent(conInteresH, "top: 450px; right: 540px");
        detalles.addComponent(sinInteresH, "top:450px; right: 390px");
        detalles.addComponent(atras, "top: 600px; right: 50px");

        fondo.addComponent(detalles);
        fondo.setComponentAlignment(detalles, Alignment.MIDDLE_CENTER);

        addComponent(fondoTotal);
        addComponent(fondo);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
