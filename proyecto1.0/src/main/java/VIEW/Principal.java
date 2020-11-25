package VIEW;

import CONTROLLER.ControllerUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.util.ArrayList;

public class Principal extends VerticalLayout implements View {

    //Controlador
    private ControllerUI controller = new ControllerUI();

    //Contenedores diferentes páginas
    private AbsoluteLayout contenedorLogIn;
    private AbsoluteLayout contenedorTabsBanco;

    //Components
    private TextField usuario;
    private PasswordField contrasenna;
    private RadioButtonGroup<String> cuentas;


    //Atributos para guardar
    private String nombreUsuario;
    private String contra;
    private String numCuenta;


    public Principal() {
        Login();
        Banco();
    }

    private void Login() {
        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondo2.0.jpg"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

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

        contenedorLogIn = new AbsoluteLayout();
        contenedorLogIn.setHeight("1000px");
        contenedorLogIn.setWidth("2000px");
        contenedorLogIn.setStyleName(ValoTheme.PANEL_BORDERLESS);
        contenedorLogIn.addComponent(image, "top: 0px; left: 0px");
        contenedorLogIn.addComponent(panelInicioSesion, "top: 300px; left: 100px");
        contenedorLogIn.addComponent(inicio, "top: 125px; left: 150px");

        addComponent(contenedorLogIn);
    }

    public void Banco(){
        contenedorTabsBanco = new AbsoluteLayout();
        contenedorTabsBanco.setWidth("1500px");
        contenedorTabsBanco.setHeight("1000px");
        contenedorTabsBanco.setStyleName(ValoTheme.PANEL_BORDERLESS);
        TabSheet menu = Menu();
        contenedorTabsBanco.addComponent(menu);
        addComponent(contenedorTabsBanco);

    }

    public TabSheet Menu(){
        TabSheet menu = new TabSheet();
        menu.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
        menu.addStyleName(ValoTheme.TABSHEET_FRAMED);
        Cuentas(menu);
        Beneficiarios(menu);
        return menu;

    }

    public void Cuentas(TabSheet menu){
        AbsoluteLayout cuentasContenedor = new AbsoluteLayout();
        cuentasContenedor.setWidth("1500px");
        cuentasContenedor.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoCuentas.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        cuentas = new RadioButtonGroup<>();
        cuentas.setItems("1", "2", "3");
        cuentas.addStyleName(ValoTheme.CHECKBOX_LARGE);


        Label lCuentas = new Label("Seleccione una cuenta");
        lCuentas.addStyleName(ValoTheme.LABEL_H2);
        lCuentas.addStyleName(ValoTheme.LABEL_BOLD);

        Button seleccionar = new Button("SELECCIONAR");
        seleccionar.setIcon(VaadinIcons.CHECK_CIRCLE_O);
        seleccionar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        seleccionar.setHeight("50px");
        seleccionar.setWidth("300px");
        seleccionar.addClickListener(this::SeleccionarCuenta);

        cuentasContenedor.addComponent(image, "top: 0px; left: 0px");
        cuentasContenedor.addComponent(cuentas, "top: 200px; left: 100px");
        cuentasContenedor.addComponent(lCuentas, "top: 50px; left: 100px");
        cuentasContenedor.addComponent(seleccionar, "top: 600px; left: 100px");


        menu.addTab(cuentasContenedor, "CUENTAS");
    }

    public void Beneficiarios(TabSheet menu){
        AbsoluteLayout contenedorBeneficiarios = new AbsoluteLayout();
        contenedorBeneficiarios.setWidth("1500px");
        contenedorBeneficiarios.setHeight("700px");

        AbsoluteLayout contenedorOpciones = new AbsoluteLayout();
        contenedorOpciones.setWidth("1500px");
        contenedorOpciones.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoCuentas.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        Label opciones = new Label("Opciones de beneficiarios");
        opciones.addStyleName(ValoTheme.LABEL_H2);

        Button verBeneficiarios = new Button("VER");
        verBeneficiarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
        verBeneficiarios.setIcon(VaadinIcons.EYE);
        verBeneficiarios.setWidth("300px");
        verBeneficiarios.setHeight("50px");
        verBeneficiarios.addClickListener(e->VerBeneficiarios(contenedorOpciones, contenedorBeneficiarios));

        Button agregarBeneficiarios = new Button("AGREGAR");
        agregarBeneficiarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
        agregarBeneficiarios.setIcon(VaadinIcons.ADD_DOCK);
        agregarBeneficiarios.setWidth("300px");
        agregarBeneficiarios.setHeight("50px");

        Button actualizarBeneficiarios = new Button("ACTUALIZAR");
        actualizarBeneficiarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
        actualizarBeneficiarios.setIcon(VaadinIcons.ANGLE_DOUBLE_UP);
        actualizarBeneficiarios.setWidth("300px");
        actualizarBeneficiarios.setHeight("50px");

        Button eliminarBeneficiarios = new Button("ELIMINAR");
        eliminarBeneficiarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
        eliminarBeneficiarios.setIcon(VaadinIcons.CLOSE_CIRCLE_O);
        eliminarBeneficiarios.setWidth("300px");
        eliminarBeneficiarios.setHeight("50px");


        contenedorOpciones.addComponent(image, "top: 0px; left: 0px");
        contenedorOpciones.addComponent(opciones, "top: 50px; left: 100px");
        contenedorOpciones.addComponent(verBeneficiarios, "top: 170px; left: 100px");
        contenedorOpciones.addComponent(agregarBeneficiarios, "top: 270px; left: 100px");
        contenedorOpciones.addComponent(actualizarBeneficiarios, "top: 370px; left: 100px");
        contenedorOpciones.addComponent(eliminarBeneficiarios, "top: 470px; left: 100px");

        contenedorBeneficiarios.addComponent(contenedorOpciones, "top: 0px; left: 0px");


        menu.addTab(contenedorBeneficiarios, "BENEFICIARIOS");
    }

    public void VerBeneficiarios (AbsoluteLayout opciones, AbsoluteLayout contenedor){
        opciones.setVisible(false);

        VerticalLayout ver = new VerticalLayout();

        Label bene = new Label("Datos de los beneficiarios actuales");
        bene.addStyleName(ValoTheme.LABEL_H2);

//        Grid<String> beneficiarios = new Grid<>();
//        beneficiarios.addColumn("NOMBRE");
//        beneficiarios.addColumn("CÉDULA");
//        beneficiarios.addColumn("PORCENTAJE");

        ver.addComponents(bene);
        contenedor.addComponent(ver);
    }

    //Métodos de los botones

    private void SeleccionarCuenta(Button.ClickEvent event) {
        numCuenta = cuentas.getSelectedItem().get();
        //Desbloquear las otras tabs si es diferente de no seleccionado
    }


    public void obtenerDatos(Button.ClickEvent event) {
        nombreUsuario = usuario.getValue();
        contra = contrasenna.getValue();
        if (controller.verificarUsuario(contra, nombreUsuario)){
            contenedorLogIn.setVisible(false);
            contenedorTabsBanco.setVisible(true);
            ArrayList<String> cuentasL = controller.devolverCuentas(nombreUsuario);
            cuentasL.add(0, "No seleccionado");
            cuentas.setItems(cuentasL);
            cuentas.setSelectedItem("No seleccionado");


        }
        else{
            Notification.show("Alguno de los datos ingresados es incorrecto\nVerfique e intente de nuevo");
        }

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {}
}
