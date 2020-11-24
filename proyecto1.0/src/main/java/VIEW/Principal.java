package VIEW;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ImageRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class Principal extends VerticalLayout implements View {
    private Panel panelInicioSesion;
    private TextField usuario;
    private PasswordField contrasenna;

    public Principal() {
        login();
    }

    private void login() {
       

        usuario = new TextField("Usuario");
        usuario.setIcon(VaadinIcons.USER);
        usuario.setPlaceholder("NOMBRE USUARIO");
        usuario.setSizeFull();
        usuario.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);

        contrasenna = new PasswordField("Contraseña");
        contrasenna.setIcon(VaadinIcons.KEY);
        contrasenna.setPlaceholder("CONTRASEÑA");
        contrasenna.setSizeFull();
        contrasenna.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);

        Button entrar = new Button("Ingresar");
        entrar.setStyleName("primary");
        entrar.setIcon(VaadinIcons.CHECK);
        entrar.setSizeFull();

        FormLayout contenedor = new FormLayout(usuario, contrasenna, entrar, image);
        contenedor.setMargin(false);

        panelInicioSesion = new Panel("INICIO DE SESIÓN", contenedor);
        panelInicioSesion.setHeight("400px");
        panelInicioSesion.setWidth("700px");
        panelInicioSesion.addStyleName(ValoTheme.PANEL_BORDERLESS);


        addComponent(panelInicioSesion);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
