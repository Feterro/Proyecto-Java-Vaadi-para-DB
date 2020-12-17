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

public class LoginV extends AbsoluteLayout implements View {

    private TextField usuario;
    private TextField contrasenna;

    private String nombreUsuario;
    private String contra;

    private ControllerUI controller = ControllerUI.getInstance();

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContra() {
        return contra;
    }

    public LoginV() {
        fondoTotal();
        this.logIn();
    }

    private void logIn() {
        HorizontalLayout contenedorFinal = new HorizontalLayout();
        contenedorFinal.setSizeFull();

        AbsoluteLayout contenedorLogIn = new AbsoluteLayout();
        contenedorLogIn.setWidth("1500px");
        contenedorLogIn.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/Login.png"));
        Image fondoLogin = new Image("", resource);
        fondoLogin.setHeight("700px");
        fondoLogin.setWidth("1500px");


        Label inicio = new Label("INICIO DE SESIÓN");
        inicio.addStyleName(ValoTheme.LABEL_H1);

        usuario = new TextField("");
        usuario.setIcon(VaadinIcons.USER);
        usuario.setPlaceholder("NOMBRE USUARIO");
        usuario.setSizeFull();
        usuario.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);

        contrasenna = new PasswordField("");
        contrasenna.setIcon(VaadinIcons.KEY);
        contrasenna.setPlaceholder("CONTRASEÑA");
        contrasenna.setSizeFull();
        contrasenna.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);

        Button entrar = new Button("INGRESAR");
        entrar.setStyleName("primary");
        entrar.setIcon(VaadinIcons.CHECK);
        entrar.setSizeFull();
        entrar.addClickListener(this::obtenerDatos);

        FormLayout contenedor = new FormLayout(usuario, contrasenna, entrar);
        contenedor.setMargin(false);
        contenedor.setWidth("500px");

        Panel panelInicioSesion = new Panel("", contenedor);
        panelInicioSesion.setHeight("400px");
        panelInicioSesion.setWidth("700px");
        panelInicioSesion.addStyleName(ValoTheme.PANEL_BORDERLESS);

        contenedorLogIn.addComponent(fondoLogin);
        contenedorLogIn.addComponent(panelInicioSesion, "top: 300px; left: 100px");
        contenedorLogIn.addComponent(inicio, "top: 50px; left: 150px");

        contenedorFinal.addComponent(contenedorLogIn);
        contenedorFinal.setComponentAlignment(contenedorLogIn, Alignment.MIDDLE_CENTER);

        addComponent(contenedorFinal);
    }

    public void fondoTotal(){
        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondoTotal.png"));
        Image fondoTotal = new Image("", resource);
        fondoTotal.setSizeFull();
        addComponent(fondoTotal);
    }

    public void obtenerDatos(Button.ClickEvent event) {
        this.nombreUsuario = usuario.getValue();
        this.contra = contrasenna.getValue();
        controller.setNombreUsuario(nombreUsuario);
        if (controller.verificarUsuario(contra, nombreUsuario)){
            Navigator navigator = new Navigator(UI.getCurrent(), this);
            navigator.addView("Banco", BancoV.class);
            navigator.navigateTo("Banco");
        }
        else{
            Notification.show("Alguno de los datos ingresados es incorrecto\nVerfique e intente de nuevo");
        }

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }


}
