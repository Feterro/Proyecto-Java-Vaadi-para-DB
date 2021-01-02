package VIEW;

import CONTROLLER.ControllerUI;
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
        Label id = new Label("22");
        id.addStyleName(ValoTheme.LABEL_H4);

        Label objetivo = new Label("Objetivo");
        objetivo.addStyleName(ValoTheme.LABEL_H3);
        Label desc = new Label("Comprar casa");
        desc.addStyleName(ValoTheme.LABEL_H4);

        Label real = new Label("Depositos reales");
        real.addStyleName(ValoTheme.LABEL_H2);

        Label seHubiera = new Label("Depositos completos");
        seHubiera.addStyleName(ValoTheme.LABEL_H2);

        Label cantDepositos = new Label("Cantidad depositos");
        cantDepositos.addStyleName(ValoTheme.LABEL_H3);
        Label cantReal = new Label("20");
        cantReal.addStyleName(ValoTheme.LABEL_H4);
        Label cantHubiera = new Label("21");
        cantHubiera.addStyleName(ValoTheme.LABEL_H4);

        Label totalDebitado = new Label("Total debitado");
        totalDebitado.addStyleName(ValoTheme.LABEL_H3);
        Label realD = new Label("2545343242");
        realD.addStyleName(ValoTheme.LABEL_H4);
        Label hubieraD = new Label("45456454");
        hubieraD.addStyleName(ValoTheme.LABEL_H4);

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
        detalles.addComponent(desc, "top: 125px; right: 325px");
        detalles.addComponent(real, "top: 200px; left: 525px");
        detalles.addComponent(seHubiera, "top: 200px; right: 425px");
        detalles.addComponent(cantDepositos, "top: 300px; left: 325px");
        detalles.addComponent(cantReal, "top: 300px; left: 615px");
        detalles.addComponent(cantHubiera, "top: 300px; right: 525px");
        detalles.addComponent(totalDebitado, "top: 400px; left: 325px");
        detalles.addComponent(realD, "top: 400px; left: 575px");
        detalles.addComponent(hubieraD, "top: 400px; right: 500px");
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
