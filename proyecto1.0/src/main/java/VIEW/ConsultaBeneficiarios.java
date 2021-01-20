package VIEW;

import CONTROLLER.ControllerUI;
import MODEL.BeneficiarioConsulta;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;

public class ConsultaBeneficiarios extends AbsoluteLayout implements View {

    private ControllerUI controller = ControllerUI.getInstance();

    public ConsultaBeneficiarios() {
        ventana();
    }

    private void ventana() {
        HorizontalLayout fondo = new HorizontalLayout();
        fondo.setSizeFull();

        AbsoluteLayout consulta = new AbsoluteLayout();
        consulta.setHeight("700px");
        consulta.setWidth("1500px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondo.png"));
        Image fondoTotal = new Image("", resource);
        fondoTotal.setSizeFull();

        FileResource resource1 = new FileResource(new File("src/main/java/VIEW/Imagenes/fondoConsBene.png"));
        Image fondoConsu = new Image("", resource1);
        fondoConsu.setWidth("1500px");
        fondoConsu.setHeight("700px");

        Label label = new Label("BENEFICIARIOS");
        label.addStyleName(ValoTheme.LABEL_H2);

        Label detalles = new Label("Detalles de todos los beneficiarios");
        detalles.addStyleName(ValoTheme.LABEL_H4);

        Grid<BeneficiarioConsulta> tablaConsulta = new Grid<>(BeneficiarioConsulta.class);
        tablaConsulta.setWidth("1000px");
        tablaConsulta.setHeight("400px");
        tablaConsulta.setColumnOrder("cedula", "nombre", "numCuentaMas", "cantCuentas", "montoDinero");
        tablaConsulta.getColumn("cedula").setCaption("CÉDULA");
        tablaConsulta.getColumn("nombre").setCaption("NOMBRE");
        tablaConsulta.getColumn("numCuentaMas").setCaption("CUENTA CON MÁS %");
        tablaConsulta.getColumn("cantCuentas").setCaption("CANTIDAD DE CUENTAS");
        tablaConsulta.getColumn("montoDinero").setCaption("MONTO TOTAL RECIBIDO");
        tablaConsulta.setItems(controller.consulta3());

        Button atras = new Button("");
        atras.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        atras.setIcon(VaadinIcons.ARROW_BACKWARD);
        atras.setWidth("50px");
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(e->{
            Navigator navigator = new Navigator(UI.getCurrent(), this);
            navigator.addView("Consultas", Admistrador.class);
            navigator.navigateTo("Consultas");
        });

        consulta.addComponent(tablaConsulta, "top: 200px; left: 250px");
        consulta.addComponent(fondoConsu);
        consulta.addComponent(label, "top: 25px; left: 675px");
        consulta.addComponent(detalles, "top: 80px; left: 625px");
        consulta.addComponent(atras, "top: 600px; right: 50px");

        fondo.addComponent(consulta);
        fondo.setComponentAlignment(consulta, Alignment.MIDDLE_CENTER);

        addComponent(fondoTotal);
        addComponent(fondo);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {}
}
