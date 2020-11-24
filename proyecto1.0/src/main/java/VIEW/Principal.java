package VIEW;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import conexion.Beneficiario;
import conexion.Conector;

import java.io.File;

public class Principal extends VerticalLayout implements View {

    //Controlador
    Beneficiario beneficiario = new Beneficiario();

    //Components
    private TextField usuario;
    private PasswordField contrasenna;
    private AbsoluteLayout contenedor;

    //Atributos para guardar
    private String nombreUsuario;
    private String contra;


    public Principal() {
        Login();
        Banco();
    }

    private void Login() {
        contenedor.setVisible(false);
        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondo2.0.jpg"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        Label inicio = new Label("INICIO DE SESIÓN");
        inicio.addStyleName(ValoTheme.LABEL_H1);
        inicio.addStyleName(ValoTheme.LABEL_BOLD);

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

        FormLayout contenedor = new FormLayout(usuario, contrasenna, entrar);
        contenedor.setMargin(false);
        contenedor.setWidth("500px");

        Panel panelInicioSesion = new Panel("", contenedor);
        panelInicioSesion.setHeight("400px");
        panelInicioSesion.setWidth("700px");
        panelInicioSesion.addStyleName(ValoTheme.PANEL_BORDERLESS);

        AbsoluteLayout contenedorFinal = new AbsoluteLayout();
        contenedorFinal.setHeight("1000px");
        contenedorFinal.setWidth("2000px");
        contenedorFinal.setStyleName(ValoTheme.PANEL_BORDERLESS);
        contenedorFinal.addComponent(image, "top: 0px; left: 0px");
        contenedorFinal.addComponent(panelInicioSesion, "top: 300px; left: 100px");
        contenedorFinal.addComponent(inicio, "top: 125px; left: 200px");

        addComponent(contenedorFinal);
    }

    public void Banco(){
        contenedor = new AbsoluteLayout();
        contenedor.setWidth("1500px");
        contenedor.setHeight("1000px");
        contenedor.setStyleName(ValoTheme.PANEL_BORDERLESS);
        TabSheet menu = Menu();
        contenedor.addComponent(menu);

    }

    public TabSheet Menu(){
        TabSheet menu = new TabSheet();
        Cuentas(menu);
        return menu;

    }

    public void Cuentas(TabSheet menu){
        AbsoluteLayout cuentasContenedor = new AbsoluteLayout();
        cuentasContenedor.setWidth("1500px");
        cuentasContenedor.setHeight("700px");
        CheckBox cuenta1 = new CheckBox("CUENTA 1");
        CheckBox cuenta2 = new CheckBox("CUENTA 2");
        cuentasContenedor.addComponent(cuenta1, "top: 200px; left: 100px");
        cuentasContenedor.addComponent(cuenta2, "top: 300px; left: 100px");
        menu.addComponent(cuentasContenedor);


    }

    public void obtenerDatos(Button.ClickEvent event) {
        nombreUsuario = usuario.getValue();
        contra = contrasenna.getValue();
        String contra = beneficiario.getContrasenna(Conector.getInstance().connection, nombreUsuario);
        contenedor.setVisible(true);
//        if (contra.equals(contra)){
//
//            System.out.println("esta bien");
//        }
//        else{
//            System.out.println("Esta mal");
//        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
