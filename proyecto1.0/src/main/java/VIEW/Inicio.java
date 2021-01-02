package VIEW;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;

public class Inicio extends AbsoluteLayout implements View {

    public Inicio() {
        ventanaInicio();
    }

    public void ventanaInicio(){
        HorizontalLayout fondo = new HorizontalLayout();
        fondo.setSizeFull();

        AbsoluteLayout inicio = new AbsoluteLayout();
        inicio.setHeight("1000px");
        inicio.setWidth("500px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondo.png"));
        Image fondoI = new Image("", resource);
        fondoI.setSizeFull();

        FileResource resource1 = new FileResource(new File("src/main/java/VIEW/Imagenes/fondoInicio.png"));
        Image fondoB = new Image("", resource1);
        fondoB.setWidth("500px");
        fondoB.setHeight("500px");

        Button administrador = new Button("ADMINISTRADOR");
        administrador.setHeight("70px");
        administrador.setWidth("200px");
        administrador.addStyleName(ValoTheme.BUTTON_PRIMARY);
        administrador.setIcon(VaadinIcons.RECORDS);
        administrador.addClickListener(e->{
           Navigator navigator = new Navigator(UI.getCurrent(), this);
           navigator.addView("Administrador", Admistrador.class);
           navigator.navigateTo("Administrador");
        });

        Button usuario = new Button("CLIENTE");
        usuario.setWidth("200px");
        usuario.setHeight("70px");
        usuario.addStyleName(ValoTheme.BUTTON_PRIMARY);
        usuario.setIcon(VaadinIcons.USERS);
        usuario.addClickListener(e-> {
            Navigator navigator = new Navigator(UI.getCurrent(), this);
            navigator.addView("Login", LoginV.class);
            navigator.navigateTo("Login");

        });

        Label label = new Label("TIPO DE USUARIO");
        label.addStyleName(ValoTheme.LABEL_H2);
        label.addStyleName(ValoTheme.LABEL_BOLD);
//        label.addStyleName(ValoTheme.LABEL_COLORED);

        inicio.addComponent(fondoB);
        inicio.addComponent(label, "top: 150px; left: 150px");
        inicio.addComponent(administrador, "top: 275px; left: 25px");
        inicio.addComponent(usuario, "top: 275; right: 25px");

        fondo.addComponent(inicio);
        fondo.setComponentAlignment(inicio, Alignment.BOTTOM_CENTER);

        addComponent(fondoI);
        addComponent(fondo);


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
