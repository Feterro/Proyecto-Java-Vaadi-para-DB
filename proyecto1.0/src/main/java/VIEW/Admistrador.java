package VIEW;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;

public class Admistrador extends AbsoluteLayout implements View {

    public Admistrador() {
        administrador();
    }

    private void administrador() {
        HorizontalLayout fondo = new HorizontalLayout();
        fondo.setSizeFull();

        AbsoluteLayout consultas = new AbsoluteLayout();
        consultas.setWidth("1500px");
        consultas.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondo.png"));
        Image fondoTotal = new Image("", resource);
        fondoTotal.setSizeFull();

        FileResource resource1 = new FileResource(new File("src/main/java/VIEW/Imagenes/fondoAdm.png"));
        Image fondoAdm = new Image("", resource1);
        fondoAdm.setHeight("700px");
        fondoAdm.setWidth("1500px");

        Label label = new Label("CONSULTAS DE ADMINISTRADOR");
        label.addStyleName(ValoTheme.LABEL_H2);

        Button cuentasO = new Button("CUENTAS OBJETIVO");
        cuentasO.addStyleName(ValoTheme.BUTTON_PRIMARY);
        cuentasO.setIcon(VaadinIcons.FOLDER_SEARCH);
        cuentasO.setWidth("225px");
        cuentasO.setHeight("70px");


        Button multas = new Button("MULTAS CUENTAS");
        multas.addStyleName(ValoTheme.BUTTON_PRIMARY);
        multas.setIcon(VaadinIcons.COIN_PILES);
        multas.setWidth("225px");
        multas.setHeight("70px");

        Button beneficiarios = new Button("BENEFICIARIOS");
        beneficiarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
        beneficiarios.setIcon(VaadinIcons.USERS);
        beneficiarios.setWidth("225px");
        beneficiarios.setHeight("70px");

        Button atras = new Button("");
        atras.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        atras.setIcon(VaadinIcons.ARROW_BACKWARD);
        atras.setWidth("50px");
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(e->{
            Navigator navigator = new Navigator(UI.getCurrent(), this);
            navigator.addView("Inicio", Inicio.class);
            navigator.navigateTo("Inicio");
        });

        consultas.addComponent(fondoAdm);
        consultas.addComponent(label, "top: 50px; left: 50px");
        consultas.addComponent(cuentasO, "top: 200px; left: 100px");
        consultas.addComponent(multas, "top: 350px; left:100px");
        consultas.addComponent(beneficiarios, "top: 500px; left: 100px");
        consultas.addComponent(atras, "top: 600px; right: 50px");

        fondo.addComponent(consultas);
        fondo.setComponentAlignment(consultas, Alignment.MIDDLE_CENTER);

        addComponent(fondoTotal);
        addComponent(fondo);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
