package VIEW;

import CONTROLLER.ControllerUI;
import MODEL.Beneficiario;
import MODEL.BeneficiariosTabla;
import MODEL.CuentaObjetivo;
import MODEL.EstadoCuenta;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class GUIBanco extends VerticalLayout implements View {

    //Controlador
    private ControllerUI controller = new ControllerUI();
    private BeneficiariosTabla beneficiariosTabla = new BeneficiariosTabla();

    //Listas
    private ArrayList<BeneficiariosTabla> beneficiariosTablaL;
    private ArrayList<EstadoCuenta> estadoCuentas;

    //Contenedores diferentes páginas
    private AbsoluteLayout contenedorLogIn;
    private AbsoluteLayout contenedorTabsBanco;
    private AbsoluteLayout contenedorOpciones;
    private AbsoluteLayout ver;
    private AbsoluteLayout contenedorAgregar;
    private AbsoluteLayout actualizarBene;
    private AbsoluteLayout preguntaC;

    //Components
    private TextField usuario;
    private PasswordField contrasenna;
    private RadioButtonGroup<String> cuentas;
    private TabSheet menu;

    private TextField cedulaA;
    private ComboBox<String> parentezcoA;
    private TextField porc;
    private Button agregar;

    private Label cuentaL;
    private Label porcentajeL;
    private Label cuenta;
    private Label cuentaOB;

    private Grid<EstadoCuenta> estados;
    private Grid<CuentaObjetivo> cuentasObjetivo;

    private ComboBox<String> numCuentasObjetivo = new ComboBox<>();


    //Atributos para guardar
    private String nombreUsuario;
    private String contra;
    private String numCuenta = "";
    private int cantEstados = 0;
    private int estadoActual = 0;

    public GUIBanco() {
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

    public void Banco() {
        contenedorTabsBanco = new AbsoluteLayout();
        contenedorTabsBanco.setWidth("1500px");
        contenedorTabsBanco.setHeight("1000px");
        contenedorTabsBanco.setStyleName(ValoTheme.PANEL_BORDERLESS);
        TabSheet menu = Menu();
        contenedorTabsBanco.addComponent(menu);
        addComponent(contenedorTabsBanco);

    }

    public TabSheet Menu() {
        menu = new TabSheet();
        menu.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
        menu.addStyleName(ValoTheme.TABSHEET_FRAMED);
        Cuentas();
        Beneficiarios();
        estadosCuenta();
        cuentasObjetivo();
        return menu;

    }

    public void Cuentas() {
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
        cuentas.addStyleName(ValoTheme.LABEL_BOLD);


        Label lCuentas = new Label("SELECCIONE UNA CUENTA");
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

    public void Beneficiarios() {


        AbsoluteLayout contenedorBeneficiarios = new AbsoluteLayout();
        contenedorBeneficiarios.setWidth("1500px");
        contenedorBeneficiarios.setHeight("700px");

        cuentaL = new Label();
        cuentaL.addStyleName(ValoTheme.LABEL_BOLD);
        cuentaL.addStyleName(ValoTheme.LABEL_H4);

        porcentajeL = new Label();
        porcentajeL.addStyleName(ValoTheme.LABEL_BOLD);
        porcentajeL.addStyleName(ValoTheme.LABEL_H4);
        porcentajeL.addStyleName(ValoTheme.LABEL_FAILURE);

        contenedorOpciones = new AbsoluteLayout();
        contenedorOpciones.setWidth("1500px");
        contenedorOpciones.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoCuentas.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        Label opciones = new Label("OPCIONES SOBRE LOS BENEFICIARIOS");
        opciones.addStyleName(ValoTheme.LABEL_H2);

        Button verBeneficiarios = new Button("VER BENEFICIARIOS");
        verBeneficiarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
        verBeneficiarios.setIcon(VaadinIcons.EYE);
        verBeneficiarios.setWidth("300px");
        verBeneficiarios.setHeight("50px");
        verBeneficiarios.addClickListener(e -> VerBeneficiarios(contenedorOpciones, contenedorBeneficiarios));

        Button agregarBeneficiarios = new Button("AGREGAR BENEFICIARIO");
        agregarBeneficiarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
        agregarBeneficiarios.setIcon(VaadinIcons.ADD_DOCK);
        agregarBeneficiarios.setWidth("300px");
        agregarBeneficiarios.setHeight("50px");
        agregarBeneficiarios.addClickListener(e -> AgregarBeneficiario(contenedorOpciones, contenedorBeneficiarios));

        Button actualizarBeneficiarios = new Button("ACTUALIZAR BENEFICIARIO");
        actualizarBeneficiarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
        actualizarBeneficiarios.setIcon(VaadinIcons.ANGLE_DOUBLE_UP);
        actualizarBeneficiarios.setWidth("300px");
        actualizarBeneficiarios.setHeight("50px");
        actualizarBeneficiarios.addClickListener(e -> actualizarBeneficiario(contenedorOpciones, contenedorBeneficiarios));

        Button eliminarBeneficiarios = new Button("ELIMINAR BENEFICIARIO");
        eliminarBeneficiarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
        eliminarBeneficiarios.setIcon(VaadinIcons.CLOSE_CIRCLE_O);
        eliminarBeneficiarios.setWidth("300px");
        eliminarBeneficiarios.setHeight("50px");
        eliminarBeneficiarios.addClickListener(e -> eliminarBeneficiario(contenedorOpciones, contenedorBeneficiarios));

        contenedorOpciones.addComponent(image, "top: 0px; left: 0px");
        contenedorOpciones.addComponent(cuentaL, "top: 50px; right: 50px");
        contenedorOpciones.addComponent(porcentajeL, "top: 100px; right: 50px");
        contenedorOpciones.addComponent(opciones, "top: 50px; left: 100px");
        contenedorOpciones.addComponent(verBeneficiarios, "top: 170px; left: 100px");
        contenedorOpciones.addComponent(agregarBeneficiarios, "top: 270px; left: 100px");
        contenedorOpciones.addComponent(actualizarBeneficiarios, "top: 370px; left: 100px");
        contenedorOpciones.addComponent(eliminarBeneficiarios, "top: 470px; left: 100px");

        contenedorBeneficiarios.addComponent(contenedorOpciones, "top: 0px; left: 0px");


        menu.addTab(contenedorBeneficiarios, "BENEFICIARIOS").setEnabled(false);
    }

    public void VerBeneficiarios(AbsoluteLayout opciones, AbsoluteLayout contenedor) {
        opciones.setVisible(false);

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoVerBene.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        ver = new AbsoluteLayout();
        ver.setHeight("700px");
        ver.setWidth("1500px");

        Label bene = new Label("DATOS DE LOS BENEFICIARIOS ACTUALES");
        bene.addStyleName(ValoTheme.LABEL_H2);
        bene.addStyleName(ValoTheme.LABEL_BOLD);


        Grid<BeneficiariosTabla> beneficiarios = new Grid<>(BeneficiariosTabla.class);
        beneficiarios.setHeight("152px");
        beneficiarios.setWidth("700px");
        beneficiarios.setColumns("nombre", "documentoIdentidad", "porcentaje");
        beneficiariosTablaL = controller.llenarTabla(Integer.parseInt(numCuenta));
        beneficiarios.setItems(beneficiariosTablaL);

        int porcentaje = controller.getPorcentajeUsado(beneficiariosTablaL);
        int sobrante = 100 - porcentaje;


        Label resumen = new Label("RESUMEN DATOS");
        resumen.addStyleName(ValoTheme.LABEL_H2);
        resumen.addStyleName(ValoTheme.LABEL_BOLD);
        Label cantBeneficiarios = new Label("Total de beneficiarios: " + controller.getCantActBene(beneficiariosTablaL));
        cantBeneficiarios.addStyleName(ValoTheme.LABEL_H3);
        cantBeneficiarios.addStyleName(ValoTheme.LABEL_BOLD);
        Label porcentajeActual = new Label("Total de porcentaje usado: " + porcentaje);
        porcentajeActual.addStyleName(ValoTheme.LABEL_H3);
        porcentajeActual.addStyleName(ValoTheme.LABEL_BOLD);
        Label sobranteL = new Label("Total dispoible de porcentaje: " + sobrante);
        sobranteL.addStyleName(ValoTheme.LABEL_H3);
        sobranteL.addStyleName(ValoTheme.LABEL_BOLD);


        Button volver = new Button("ATRÁS");
        volver.setIcon(VaadinIcons.BACKSPACE_A);
        volver.setWidth("200px");
        volver.setHeight("50px");
        volver.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        volver.addClickListener(e -> atras(ver, contenedorOpciones));

        ver.addComponent(image, "top: 0px; left: 0px");
        ver.addComponent(bene, "top: 50px; left: 50px");
        ver.addComponent(beneficiarios, "top: 200px; left: 350px");
        ver.addComponent(volver, "top: 610px; right: 50px");
        ver.addComponent(resumen, "top: 400px; left: 50px");
        ver.addComponent(cantBeneficiarios, "top: 450px; left: 350px");
        ver.addComponent(porcentajeActual, "top: 475px; left: 350px");
        ver.addComponent(sobranteL, "top: 500px; left: 350px");
        contenedor.addComponent(ver);
    }

    public void AgregarBeneficiario(AbsoluteLayout opciones, AbsoluteLayout tabBene) {
        opciones.setVisible(false);

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoAgregarBene.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        contenedorAgregar = new AbsoluteLayout();
        contenedorAgregar.setWidth("1500px");
        contenedorAgregar.setHeight("700px");

        Label agregarL = new Label("COMPLETE LOS ESPACIOS");
        agregarL.addStyleName(ValoTheme.LABEL_BOLD);
        agregarL.addStyleName(ValoTheme.LABEL_H2);
        agregarL.setSizeFull();

        cedulaA = new TextField("Documento de identidad");
        cedulaA.setIcon(VaadinIcons.USER_CHECK);
        cedulaA.setPlaceholder("Sin cedula");
        cedulaA.setWidth("300px");

        parentezcoA = new ComboBox<>("Parentezco");
        parentezcoA.setIcon(VaadinIcons.FAMILY);
        parentezcoA.setPlaceholder("No seleccionado");
        parentezcoA.setItems(controller.getParentezcos());
        parentezcoA.setWidth("300px");


        porc = new TextField("Porcentaje");
        porc.setIcon(VaadinIcons.BOOK_PERCENT);
        porc.setPlaceholder("0%");
        porc.setWidth("300px");

        agregar = new Button("AGREGAR");
        agregar.setIcon(VaadinIcons.ADD_DOCK);
        agregar.setStyleName("primary");
        agregar.setWidth("300px");
        agregar.addClickListener(e -> {
            {
                agregarBeneficiario(Integer.parseInt(cedulaA.getValue()), parentezcoA.getSelectedItem().get(), Integer.parseInt(porc.getValue()), controller.getBeneficiarios(), contenedorAgregar, tabBene);
                controller.setBeneficiarios(Integer.parseInt(numCuenta));
                if (controller.getPorcentajeUsado(controller.getBeneficiarios()) == 100)
                    porcentajeL.setVisible(false);
            }
        });

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(j -> atras(contenedorAgregar, contenedorOpciones));

        contenedorAgregar.addComponent(image);
        contenedorAgregar.addComponent(agregarL, "top: 50px; left: 100px");
        contenedorAgregar.addComponent(cedulaA, "top: 200px; left: 100px");
        contenedorAgregar.addComponent(parentezcoA, "top: 300px; left: 100px");
        contenedorAgregar.addComponent(porc, "top: 400px; left: 100px");
        contenedorAgregar.addComponent(agregar, "top: 500px; left: 100px");
        contenedorAgregar.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(contenedorAgregar);
    }

    public void agregarBeneNuevo(AbsoluteLayout tabBene, int cedulaPuesta, String parentezcoPuesto, int porcenataje) {
        contenedorAgregar.setVisible(false);

        AbsoluteLayout contenedorAgregarComplejo = new AbsoluteLayout();
        contenedorAgregarComplejo.setWidth("1500px");
        contenedorAgregarComplejo.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoAgregarBene2.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        Label datos = new Label("AGREGAR BENEFICIARIOS");
        datos.addStyleName(ValoTheme.LABEL_H2);

        Label datosPersonales = new Label("Datos Personales");
        datosPersonales.addStyleName(ValoTheme.LABEL_H3);
        datosPersonales.addStyleName(ValoTheme.LABEL_BOLD);

        TextField nombre = new TextField("Nombre");
        nombre.setIcon(VaadinIcons.USER_CHECK);
        nombre.setWidth("300px");
        nombre.setPlaceholder("Sin nombre");

        ComboBox<String> tipoDoc = new ComboBox<>("Tipo Documento Identidad");
        tipoDoc.setWidth("300px");
        tipoDoc.setIcon(VaadinIcons.QUESTION);
        tipoDoc.setPlaceholder("Sin selección");
        tipoDoc.setItems(controller.getTiposDoc());

        TextField cedula = new TextField("Documento identidad");
        cedula.setIcon(VaadinIcons.TEXT_LABEL);
        cedula.setWidth("300px");
        cedula.setPlaceholder("Sin cedula");
        cedula.setValue(String.valueOf(cedulaPuesta));

        DateField fechaNac = new DateField("Fecha Nacimiento");
        fechaNac.setIcon(VaadinIcons.CALENDAR_USER);
        fechaNac.setWidth("300px");
        fechaNac.setPlaceholder("0/0/0");

        Label relPorc = new Label("Relación y porcentaje");
        relPorc.addStyleName(ValoTheme.LABEL_H3);
        relPorc.addStyleName(ValoTheme.LABEL_BOLD);

        ComboBox<String> parentezco = new ComboBox<>("Parentezo");
        parentezco.setPlaceholder("Sin selección");
        parentezco.setIcon(VaadinIcons.FAMILY);
        parentezco.setWidth("300px");
        parentezco.setItems(controller.getParentezcos());
        parentezco.setValue(parentezcoPuesto);

        TextField porcentaje = new TextField("Porcentaje");
        porcentaje.setIcon(VaadinIcons.BOOK_PERCENT);
        porcentaje.setWidth("300px");
        porcentaje.setPlaceholder("0%");
        porcentaje.setValue(String.valueOf(porcenataje));

        Label contacto = new Label("Contacto");
        contacto.addStyleName(ValoTheme.LABEL_BOLD);
        contacto.addStyleName(ValoTheme.LABEL_H3);

        TextField email = new TextField("Email");
        email.setIcon(VaadinIcons.ENVELOPE);
        email.setWidth("300px");
        email.setPlaceholder("persona@correo.com");

        TextField tel1 = new TextField("Telefono 1");
        tel1.setIcon(VaadinIcons.PHONE);
        tel1.setWidth("300px");
        tel1.setPlaceholder("888888");

        TextField tel2 = new TextField("Telefono 2");
        tel2.setIcon(VaadinIcons.PHONE);
        tel2.setWidth("300px");
        tel2.setPlaceholder("888888");

        Button agregarNuevo = new Button("AGREGAR");
        agregarNuevo.setIcon(VaadinIcons.ADD_DOCK);
        agregarNuevo.setStyleName(ValoTheme.BUTTON_PRIMARY);
        agregarNuevo.setWidth("300px");
        agregarNuevo.setHeight("50px");
        agregarNuevo.addClickListener(e -> {
            if (controller.getPorcentajeUsado(controller.getBeneficiarios()) + Integer.parseInt(porcentaje.getValue()) <= 100) {
                if (controller.getCantActBene(controller.getBeneficiarios()) < 3) {
                    boolean devolvio = controller.agregarBeneficiarioComplejo(Integer.parseInt(numCuenta), Integer.parseInt(cedula.getValue()), parentezco.getSelectedItem().get(), nombre.getValue(), tipoDoc.getSelectedItem().get(), fechaNac.getValue().toString(), Integer.parseInt(porcentaje.getValue()), email.getValue(), Integer.parseInt(tel1.getValue()), Integer.parseInt(tel2.getValue()));
                    if (devolvio) {
                        Notification.show("Beneficiario agregado correctamente");
                        contenedorAgregarComplejo.setVisible(false);
                        contenedorOpciones.setVisible(true);
                    } else {
                        Notification.show("Hubo un problema agregando el beneficiario");
                    }
                } else {
                    Notification.show("Ya existe la cantidad máxima de beneficiarios");
                }
            } else {
                Notification.show("El porcentaje supera el 100%");
            }
            controller.setBeneficiarios(Integer.parseInt(numCuenta));
            if (controller.getPorcentajeUsado(controller.getBeneficiarios()) == 100)
                porcentajeL.setVisible(false);
        });

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(j -> {
            atras(contenedorAgregarComplejo, contenedorAgregar);
            preguntaC.setVisible(false);
            cedulaA.setEnabled(true);
            porc.setEnabled(true);
            parentezcoA.setEnabled(true);
            agregar.setEnabled(true);
            cedulaA.clear();
            porc.clear();
            parentezcoA.clear();
        });

        contenedorAgregarComplejo.addComponent(image);

        contenedorAgregarComplejo.addComponent(datos, "top: 50px; left: 50px");
        contenedorAgregarComplejo.addComponent(datosPersonales, "top: 100px; left: 650px");
        contenedorAgregarComplejo.addComponent(nombre, "top: 200px; left: 75px");
        contenedorAgregarComplejo.addComponent(tipoDoc, "top: 200px; left: 425px");
        contenedorAgregarComplejo.addComponent(cedula, "top: 200px; right: 425px");
        contenedorAgregarComplejo.addComponent(fechaNac, "top: 200px; right: 75px");

        contenedorAgregarComplejo.addComponent(relPorc, "top: 270px; left: 630px");
        contenedorAgregarComplejo.addComponent(parentezco, "top: 350px; left: 400px");
        contenedorAgregarComplejo.addComponent(porcentaje, "top: 350px; right: 400px");

        contenedorAgregarComplejo.addComponent(contacto, "top: 420px; left: 700px");
        contenedorAgregarComplejo.addComponent(email, "top: 500px; left: 250px");
        contenedorAgregarComplejo.addComponent(tel1, "top: 500px; left: 600px");
        contenedorAgregarComplejo.addComponent(tel2, "top: 500px; right: 250px");

        contenedorAgregarComplejo.addComponent(agregarNuevo, "top: 600px; left: 600px");
        contenedorAgregarComplejo.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(contenedorAgregarComplejo, "top: 0px, left: 0px");

    }

    public void preguntar(AbsoluteLayout agregarCont, AbsoluteLayout tabBene, int cedula, String parentezco, int porcentaje) {
        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondoPregunta.png"));
        Image image = new Image("", resource);
        image.setWidth("700px");
        image.setHeight("500px");

        preguntaC = new AbsoluteLayout();
        preguntaC.setWidth("700px");
        preguntaC.setHeight("500px");

        cedulaA.setEnabled(false);
        porc.setEnabled(false);
        parentezcoA.setEnabled(false);
        agregar.setEnabled(false);

        Label notificacion = new Label("La persona no está en la base de datos. No se pudo agregar");
        notificacion.addStyleName(ValoTheme.LABEL_FAILURE);

        Label pregunta = new Label("¿Desea agregarla?");
        pregunta.addStyleName(ValoTheme.LABEL_BOLD);

        Button si = new Button("SI");
        si.setIcon(VaadinIcons.CHECK);
        si.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        si.setWidth("250px");
        si.addClickListener(e -> agregarBeneNuevo(tabBene, cedula, parentezco, porcentaje));

        Button no = new Button("NO");
        no.setIcon(VaadinIcons.CLOSE_SMALL);
        no.setWidth("250px");
        no.addStyleName(ValoTheme.BUTTON_DANGER);

        preguntaC.addComponent(image, "top: 0px; left: 0px");
        preguntaC.addComponent(notificacion, "top: 100px; left: 100px");
        preguntaC.addComponent(pregunta, "top: 150px; left: 270px");
        preguntaC.addComponent(si, "top: 200px; left: 80px");
        preguntaC.addComponent(no, "top: 200px; left: 380px");

        no.addClickListener(e -> preguntaC.setVisible(false));

        agregarCont.addComponent(preguntaC, "top: 150px; left: 450px");
    }

    public void actualizarBeneficiario(AbsoluteLayout opciones, AbsoluteLayout tabBene) {
        opciones.setVisible(false);

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoAgregarBene.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        actualizarBene = new AbsoluteLayout();
        actualizarBene.setHeight("700px");
        actualizarBene.setWidth("1500px");

        Label act = new Label("ACTUALIZAR BENEFICIARIO");
        act.addStyleName(ValoTheme.LABEL_H2);

        ComboBox<String> beneficiarios = new ComboBox<>("Beneficiarios");
        beneficiarios.setIcon(VaadinIcons.GROUP);
        beneficiarios.setItems(controller.getCedulasBen(Integer.parseInt(numCuenta)));
        beneficiarios.setWidth("300px");
        beneficiarios.setPlaceholder("No seleccionado");

        Button confirmar = new Button("CONFIRMAR");
        confirmar.setIcon(VaadinIcons.CHECK_CIRCLE);
        confirmar.setWidth("300px");
        confirmar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        confirmar.addClickListener(e -> actualizarBeneficiarios(actualizarBene, tabBene, Integer.parseInt(beneficiarios.getSelectedItem().get()), beneficiarios));

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(e -> atras(actualizarBene, contenedorOpciones));

        actualizarBene.addComponent(image);
        actualizarBene.addComponent(act, "top: 50; left: 50px");
        actualizarBene.addComponent(beneficiarios, "top: 200px; left: 100px");
        actualizarBene.addComponent(confirmar, "top: 400px; left: 100px");
        actualizarBene.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(actualizarBene);
    }

    public void actualizarBeneficiarios(AbsoluteLayout act1, AbsoluteLayout tabBene, int cedulaG, ComboBox<String> beneficiarios) {
        act1.setVisible(false);

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoAgregarBene2.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        Beneficiario beneficiario = controller.getBeneficiario(cedulaG);
        beneficiario.imprimir();
        AbsoluteLayout actualizarBen = new AbsoluteLayout();
        actualizarBen.setWidth("1500px");
        actualizarBen.setHeight("700px");

        Label datos = new Label("DATOS NUEVO BENEFICIARIO");
        datos.addStyleName(ValoTheme.LABEL_H2);

        Label datosPersonales = new Label("Datos Personales");
        datosPersonales.addStyleName(ValoTheme.LABEL_H3);
        datosPersonales.addStyleName(ValoTheme.LABEL_BOLD);

        TextField nombre = new TextField("Nombre");
        nombre.setIcon(VaadinIcons.USER_CHECK);
        nombre.setWidth("300px");
        nombre.setPlaceholder("Sin nombre");
        nombre.setValue(beneficiario.getNombre());

        ComboBox<String> tipoDoc = new ComboBox<>("Tipo Documento Identidad");
        tipoDoc.setWidth("300px");
        tipoDoc.setIcon(VaadinIcons.QUESTION);
        tipoDoc.setPlaceholder("Sin selección");
        tipoDoc.setItems(controller.getTiposDoc());
        tipoDoc.setValue(beneficiario.getTipoDocIdent());

        TextField cedula = new TextField("Documento identidad");
        cedula.setIcon(VaadinIcons.TEXT_LABEL);
        cedula.setWidth("300px");
        cedula.setPlaceholder("Sin cedula");
        cedula.setValue(String.valueOf(beneficiario.getValorDocIdent()));

        DateField fechaNac = new DateField("Fecha Nacimiento");
        fechaNac.setIcon(VaadinIcons.CALENDAR_USER);
        fechaNac.setWidth("300px");
        fechaNac.setPlaceholder("0/0/0");
        fechaNac.setValue(LocalDate.parse(beneficiario.getFechaNac()));

        Label relPorc = new Label("Relación y porcentaje");
        relPorc.addStyleName(ValoTheme.LABEL_H3);
        relPorc.addStyleName(ValoTheme.LABEL_BOLD);

        ComboBox<String> parentezco = new ComboBox<>("Parentezo");
        parentezco.setPlaceholder("Sin selección");
        parentezco.setIcon(VaadinIcons.FAMILY);
        parentezco.setWidth("300px");
        parentezco.setItems(controller.getParentezcos());
        parentezco.setValue(beneficiario.getParentesco());

        TextField porcentaje = new TextField("Porcentaje");
        porcentaje.setIcon(VaadinIcons.BOOK_PERCENT);
        porcentaje.setWidth("300px");
        porcentaje.setPlaceholder("0%");
        porcentaje.setValue(String.valueOf(beneficiario.getPorcentaje()));

        Label contacto = new Label("Contacto");
        contacto.addStyleName(ValoTheme.LABEL_BOLD);
        contacto.addStyleName(ValoTheme.LABEL_H3);

        TextField email = new TextField("Email");
        email.setIcon(VaadinIcons.ENVELOPE);
        email.setWidth("300px");
        email.setPlaceholder("persona@correo.com");
        email.setValue(beneficiario.getEmail());

        TextField tel1 = new TextField("Telefono 1");
        tel1.setIcon(VaadinIcons.PHONE);
        tel1.setWidth("300px");
        tel1.setPlaceholder("888888");
        tel1.setValue(String.valueOf(beneficiario.getTel1()));

        TextField tel2 = new TextField("Telefono 2");
        tel2.setIcon(VaadinIcons.PHONE);
        tel2.setWidth("300px");
        tel2.setPlaceholder("888888");
        tel2.setValue(String.valueOf(beneficiario.getTel2()));

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(e -> {
            atras(actualizarBen, actualizarBene);
            beneficiarios.clear();
        });

        Button actualizar = new Button("ACTUALIZAR");
        actualizar.setIcon(VaadinIcons.UPLOAD);
        actualizar.setStyleName(ValoTheme.BUTTON_PRIMARY);
        actualizar.setWidth("300px");
        actualizar.setHeight("50px");
        actualizar.addClickListener(e -> {
            if (porcentajeCorrecto(Integer.parseInt(porcentaje.getValue()), cedulaG)) {
                if (controller.actualizarBen(cedulaG, Integer.parseInt(cedula.getValue()), nombre.getValue(), parentezco.getSelectedItem().get(), fechaNac.getValue().toString(), tipoDoc.getSelectedItem().get(), Integer.parseInt(porcentaje.getValue()), email.getValue(), Integer.parseInt(tel1.getValue()), Integer.parseInt(tel2.getValue()))) {
                    Notification.show("Se actualizó correctamente el beneficiario");
                    actualizarBen.setVisible(false);
                    contenedorOpciones.setVisible(true);
                } else {
                    Notification.show("Hubo un problema al agregar el beneficiario\nIntentelo de nuevo más tarde");
                }
            } else {
                Notification.show("El porcentaje seleccionado sobrepasa el 100%");
            }
            controller.setBeneficiarios(Integer.parseInt(numCuenta));
            if (controller.getPorcentajeUsado(controller.getBeneficiarios()) == 100) {
                porcentajeL.setVisible(false);
            } else if (controller.getPorcentajeUsado(controller.getBeneficiarios()) < 100)
                porcentajeL.setVisible(true);
        });

        actualizarBen.addComponent(image);
        actualizarBen.addComponent(datos, "top: 50px; left: 50px");
        actualizarBen.addComponent(datosPersonales, "top: 100px; left: 650px");
        actualizarBen.addComponent(nombre, "top: 200px; left: 75px");
        actualizarBen.addComponent(tipoDoc, "top: 200px; left: 425px");
        actualizarBen.addComponent(cedula, "top: 200px; right: 425px");
        actualizarBen.addComponent(fechaNac, "top: 200px; right: 75px");

        actualizarBen.addComponent(relPorc, "top: 270px; left: 630px");
        actualizarBen.addComponent(parentezco, "top: 350px; left: 400px");
        actualizarBen.addComponent(porcentaje, "top: 350px; right: 400px");

        actualizarBen.addComponent(contacto, "top: 420px; left: 700px");
        actualizarBen.addComponent(email, "top: 500px; left: 250px");
        actualizarBen.addComponent(tel1, "top: 500px; left: 600px");
        actualizarBen.addComponent(tel2, "top: 500px; right: 250px");

        actualizarBen.addComponent(actualizar, "top: 600px; left: 600px");
        actualizarBen.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(actualizarBen, "top: 0px, left: 0px");
    }

    public void eliminarBeneficiario(AbsoluteLayout opciones, AbsoluteLayout tabBene) {
        opciones.setVisible(false);

        AbsoluteLayout contenedorEliminar = new AbsoluteLayout();
        contenedorEliminar.setWidth("1500px");
        contenedorEliminar.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoAgregarBene.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        Label label = new Label("ELIMINAR BENEFICIARIO");
        label.addStyleName(ValoTheme.LABEL_H2);

        ComboBox<String> beneficiariosE = new ComboBox<>("Beneficiarios");
        beneficiariosE.setIcon(VaadinIcons.GROUP);
        beneficiariosE.setPlaceholder("Sin selección");
        beneficiariosE.setWidth("300px");
        beneficiariosE.setItems(controller.getCedulasBen(Integer.parseInt(numCuenta)));

        Button eliminar = new Button("ELIMINAR");
        eliminar.setIcon(VaadinIcons.CLOSE_CIRCLE);
        eliminar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        eliminar.setWidth("300px");
        eliminar.addClickListener(e -> {
            boolean devolver = controller.eliminarBene(Integer.parseInt(beneficiariosE.getSelectedItem().get()));
            if (devolver) {
                Notification.show("Se eliminó el beneficiario correctamente");
                beneficiariosE.clear();
                beneficiariosE.setItems(controller.getCedulasBen(Integer.parseInt(numCuenta)));
            } else {
                Notification.show("Hubo un error con la eliminación\nIntentelo más tarde");
            }
            controller.setBeneficiarios(Integer.parseInt(numCuenta));
            if (controller.getPorcentajeUsado(controller.getBeneficiarios()) < 100)
                porcentajeL.setVisible(true);
        });

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(j -> atras(contenedorEliminar, contenedorOpciones));

        contenedorEliminar.addComponent(image);
        contenedorEliminar.addComponent(label, "top: 50px; left: 50px");
        contenedorEliminar.addComponent(beneficiariosE, "top: 200px; left: 100px");
        contenedorEliminar.addComponent(eliminar, "top: 400px; left: 100px");
        contenedorEliminar.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(contenedorEliminar);
    }

    public void estadosCuenta() {
        AbsoluteLayout estadosCuentaContenedor = new AbsoluteLayout();
        estadosCuentaContenedor.setWidth("1500px");
        estadosCuentaContenedor.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/EstadosCuenta.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

        Label datos = new Label("ESTADOS MÁS RECIENTES");
        datos.addStyleName(ValoTheme.LABEL_H2);

        estados = new Grid<>(EstadoCuenta.class);
        estados.setWidth("700px");
        estados.setHeight("343px");
        estados.getColumn("numero").setCaption("#");
        estados.getColumn("fechaInicio").setCaption("FECHA INICIO");
        estados.getColumn("fechaFinal").setCaption("FECHA FINAL");


        ArrayList<Integer> idsR = new ArrayList<>();
        idsR.add(1);
        idsR.add(2);
        idsR.add(3);
        idsR.add(4);
        idsR.add(5);
        idsR.add(6);
        idsR.add(7);
        idsR.add(8);
        RadioButtonGroup<Integer> ids = new RadioButtonGroup<>();
        ids.setItems(idsR);
        ids.addStyleName(ValoTheme.CHECKBOX_LARGE);


        Button selecionar = new Button("VER DETALLES");
        selecionar.setIcon(VaadinIcons.FILE_TEXT);
        selecionar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        selecionar.setWidth("300px");
        selecionar.setHeight("50px");

        cuenta = new Label();
        cuenta.addStyleName(ValoTheme.LABEL_H4);
        cuenta.addStyleName(ValoTheme.LABEL_BOLD);


        Button mas = new Button();
        mas.setIcon(VaadinIcons.ANGLE_DOUBLE_RIGHT);
        mas.addStyleName(ValoTheme.BUTTON_PRIMARY);
        mas.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        mas.setWidth("300px");
        mas.setHeight("50px");
        mas.addClickListener(e -> {
            if (cantEstados >= 8) {
                ids.setItems(idsRB(estadoActual, estadoActual + 8));
                estados.setItems(estadoCuentas.subList(estadoActual, estadoActual + 8));
                cantEstados = cantEstados - 8;
                estadoActual = estadoActual + 8;
            } else if (cantEstados <= 8) {
                estados.setItems(estadoCuentas.subList(estadoActual, -1));
            } else {
                Notification.show("No hay más estado que mostrar");
            }

        });

        Button atras = new Button();
        atras.setIcon(VaadinIcons.ANGLE_DOUBLE_LEFT);
        atras.addStyleName(ValoTheme.BUTTON_PRIMARY);
        atras.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        atras.setWidth("300px");
        atras.setHeight("50px");
        atras.addClickListener(e -> {
            if (estadoActual >= 8) {
                ids.setItems(idsRB(estadoActual - 8, estadoActual));
                estados.setItems(estadoCuentas.subList(estadoActual - 8, estadoActual));
                estadoActual = estadoActual - 8;
                cantEstados = cantEstados + 8;
            } else {
                Notification.show("No hay estados anteriores");
            }
        });

        estadosCuentaContenedor.addComponent(image);
        estadosCuentaContenedor.addComponent(datos, "top: 25px; left: 50px");
        estadosCuentaContenedor.addComponent(estados, "top: 100px; left: 400px");
        estadosCuentaContenedor.addComponent(ids, "top:130px; left: 1120px");
        estadosCuentaContenedor.addComponent(atras, "top: 463; left: 425px");
        estadosCuentaContenedor.addComponent(mas, "top: 463px; left: 775px");
        estadosCuentaContenedor.addComponent(selecionar, "top: 540; left: 600px");
        estadosCuentaContenedor.addComponent(cuenta, "top: 50px; right: 50px");


        menu.addTab(estadosCuentaContenedor, "ESTADOS CUENTA").setEnabled(false);
    }

    public ArrayList<Integer> idsRB(int inicio, int fin) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = inicio; i < fin; i++) {
            ids.add(i + 1);
        }
        return ids;
    }

    public void cuentasObjetivo() {
        AbsoluteLayout cuentasObjetivoOpciones = new AbsoluteLayout();
        cuentasObjetivoOpciones.setWidth("1500px");
        cuentasObjetivoOpciones.setHeight("700px");

        AbsoluteLayout cuentasObjetivo = new AbsoluteLayout();
        cuentasObjetivo.setWidth("1500px");
        cuentasObjetivo.setHeight("700px");

        Label opciones = new Label("OPCIONES DE CUENTA OBJETIVO");
        opciones.addStyleName(ValoTheme.LABEL_H2);

        Button ver = new Button("VER DETALLES");
        ver.setIcon(VaadinIcons.GLASSES);
        ver.setWidth("300px");
        ver.setHeight("50px");
        ver.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ver.addClickListener(e -> verCuentaObj(cuentasObjetivo, cuentasObjetivoOpciones));

        Button crear = new Button("CREAR");
        crear.setWidth("300px");
        crear.setHeight("50px");
        crear.addStyleName(ValoTheme.BUTTON_PRIMARY);
        crear.setIcon(VaadinIcons.PENCIL);
        crear.addClickListener(e -> crearCuenta(cuentasObjetivo, cuentasObjetivoOpciones));

        cuentaOB = new Label();
        cuentaOB.addStyleName(ValoTheme.LABEL_H4);
        cuentaOB.addStyleName(ValoTheme.LABEL_BOLD);

        Button modificar = new Button("MODIFICAR");
        modificar.setWidth("300px");
        modificar.setHeight("50px");
        modificar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        modificar.setIcon(VaadinIcons.EDIT);
        modificar.addClickListener(e -> {
            numCuentasObjetivo.setItems(controller.getNumerosCuentaObjetivo());
            modificarCuenOb(cuentasObjetivo, cuentasObjetivoOpciones);

        });

        Button desactivar = new Button("DESACTIVAR");
        desactivar.setWidth("300px");
        desactivar.setHeight("50px");
        desactivar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        desactivar.setIcon(VaadinIcons.ERASER);
        desactivar.addClickListener(e -> desactivarCuentaObjetivo(cuentasObjetivo, cuentasObjetivoOpciones));

        cuentasObjetivoOpciones.addComponent(opciones, "top: 50px; left: 100px");
        cuentasObjetivoOpciones.addComponent(ver, "top: 170; left: 100px");
        cuentasObjetivoOpciones.addComponent(crear, "top: 270; left: 100px");
        cuentasObjetivoOpciones.addComponent(modificar, "top: 370; left: 100px");
        cuentasObjetivoOpciones.addComponent(desactivar, "top: 470; left: 100px");
        cuentasObjetivoOpciones.addComponent(cuentaOB, "top: 50; right: 50px");
        cuentasObjetivo.addComponent(cuentasObjetivoOpciones);


        menu.addTab(cuentasObjetivo, "CUENTAS OBJETIVO");
        menu.getTab(3).setEnabled(false);
    }

    public void verCuentaObj(AbsoluteLayout cuentasObjetivoCon, AbsoluteLayout cuentasObjOpciones){
        cuentasObjOpciones.setVisible(false);

        AbsoluteLayout ver = new AbsoluteLayout();
        ver.setWidth("1500px");
        ver.setHeight("700px");

        Label datos = new Label("DETALLES CUENTAS");
        datos.addStyleName(ValoTheme.LABEL_H2);

        cuentasObjetivo = new Grid<>(CuentaObjetivo.class);
        cuentasObjetivo.removeColumn("intereses");
        cuentasObjetivo.removeColumn("numCuentaAsociada");
        cuentasObjetivo.setColumnOrder("numCuenta", "objetivo", "fechaInicio", "fechaFinal", "cuota", "saldo");
        cuentasObjetivo.getColumn("numCuenta").setCaption("CUENTA #");
        cuentasObjetivo.getColumn("objetivo").setCaption("OBJETIVO");
        cuentasObjetivo.getColumn("fechaInicio").setCaption("FECHA INICIO");
        cuentasObjetivo.getColumn("fechaFinal").setCaption("FECHA FINALIZACIÓN");
        cuentasObjetivo.getColumn("cuota").setCaption("CUOTA MENSUAL");
        cuentasObjetivo.getColumn("saldo").setCaption("SALDO ACTUAL");
        cuentasObjetivo.setWidth("1000px");
        cuentasObjetivo.setHeight("350px");
        cuentasObjetivo.setItems(controller.verDetalles());


        Button atras = new Button();
        atras.setWidth("200px");
        atras.setHeight("50px");
        atras.addStyleName(ValoTheme.BUTTON_PRIMARY);
        atras.setIcon(VaadinIcons.ANGLE_DOUBLE_LEFT);

        Button adelante = new Button();
        adelante.setWidth("200px");
        adelante.setHeight("50px");
        adelante.addStyleName(ValoTheme.BUTTON_PRIMARY);
        adelante.setIcon(VaadinIcons.ANGLE_DOUBLE_RIGHT);

        Button volver = new Button("ATRÁS");
        volver.setIcon(VaadinIcons.BACKSPACE_A);
        volver.setWidth("200px");
        volver.setHeight("50px");
        volver.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        volver.addClickListener(e -> atras(ver, cuentasObjOpciones));

        ver.addComponent(datos, "top: 50px; left: 50px");
        ver.addComponent(cuentasObjetivo, "top: 150px; left: 250px");
        ver.addComponent(atras, "top: 525px; left: 525px");
        ver.addComponent(adelante, "top: 525px; left: 775px");
        ver.addComponent(volver, "top: 610px; right: 50px");

        cuentasObjetivoCon.addComponent(ver);
    }

    public void crearCuenta(AbsoluteLayout cuentasObjetivoCon, AbsoluteLayout cuentasObjOpciones){
        cuentasObjOpciones.setVisible(false);

        AbsoluteLayout crear = new AbsoluteLayout();
        crear.setHeight("700px");
        crear.setWidth("1500px");

        Label datosObj = new Label("PERSONALICE SU CUENTA OBJETIVO");
        datosObj.addStyleName(ValoTheme.LABEL_H2);

        TextField objetivo = new TextField("Objetivo");
        objetivo.setWidth("300px");
        objetivo.setIcon(VaadinIcons.GIFT);
        objetivo.setPlaceholder("SU OBJETIVO AQUI");

        DateField fechaIn = new DateField("Fecha Inicio");
        fechaIn.setIcon(VaadinIcons.HOURGLASS_START);
        fechaIn.setWidth("300px");
        fechaIn.setPlaceholder("10/10/10");

        DateField fechaFin = new DateField("Fecha Finalización");
        fechaFin.setIcon(VaadinIcons.HOURGLASS_END);
        fechaFin.setWidth("300px");
        fechaFin.setPlaceholder("10/10/10");

        TextField cuota = new TextField("Cuota");
        cuota.setIcon(VaadinIcons.BOOK_DOLLAR);
        cuota.setWidth("300px");
        cuota.setPlaceholder("1000$");

        Button crearB = new Button("CREAR");
        crearB.setWidth("300px");
        crearB.setIcon(VaadinIcons.HAMMER);
        crearB.addStyleName(ValoTheme.BUTTON_PRIMARY);
        crearB.setHeight("50px");
        crearB.addClickListener(e -> {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaFinal = formatter.parse(fechaFin.getValue().toString());
                Date fechaInicio = formatter.parse(fechaIn.getValue().toString());
                String hoy = formatter.format(new Date());
                Date hoyD = formatter.parse(hoy);

                if(fechaInicio.after(hoyD) || fechaInicio.equals(hoyD)){
                    if (fechaFinal.after(fechaInicio)){
                       if(controller.crearCuentaObj(Integer.parseInt(numCuenta), objetivo.getValue(), fechaIn.getValue().toString(), fechaFin.getValue().toString(), Float.parseFloat(cuota.getValue()))){
                           Notification.show("La cuenta fue creada con éxito!");
                           controller.setNumerosCuenta(Integer.parseInt(numCuenta));
                       }
                       else{
                           Notification.show("Hubo un problema creando la cuenta, intente de nuevo");
                       }
                    }
                    else{
                        Notification.show("La fecha de finalización está antes que la fecha de inicio");
                    }
                }
                else{
                    Notification.show("La fecha de inicio no puede ser una fecha anterior a la fecha de hoy");
                }
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });

        Button volver = new Button("ATRÁS");
        volver.setIcon(VaadinIcons.BACKSPACE_A);
        volver.setWidth("200px");
        volver.setHeight("50px");
        volver.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        volver.addClickListener(e -> atras(crear, cuentasObjOpciones));

        crear.addComponent(datosObj, "top: 50px; left: 100px");
        crear.addComponent(objetivo, "top: 200px; left: 600px");
        crear.addComponent(fechaIn, "top: 350px; left: 450px");
        crear.addComponent(fechaFin, "top: 350px; left: 800px");
        crear.addComponent(cuota, "top: 500px; left: 600px");
        crear.addComponent(crearB, "top: 600px; left: 600px");
        crear.addComponent(volver, "top: 610px; right: 50px");

        cuentasObjetivoCon.addComponent(crear);
    }

    public void modificarCuenOb(AbsoluteLayout cuentasObjetivoCon, AbsoluteLayout cuentasObjOpciones){
        cuentasObjOpciones.setVisible(false);

        AbsoluteLayout modificar = new AbsoluteLayout();
        modificar.setWidth("1500px");
        modificar.setHeight("700px");

        Label datosObj = new Label("EDITAR DETALLES");
        datosObj.addStyleName(ValoTheme.LABEL_H2);

        TextField objetivo = new TextField("Objetivo");
        objetivo.setWidth("300px");
        objetivo.setIcon(VaadinIcons.GIFT);
        objetivo.setPlaceholder("SU OBJETIVO AQUI");
        objetivo.setEnabled(false);

        DateField fechaFin = new DateField("Fecha Final");
        fechaFin.setIcon(VaadinIcons.HOURGLASS_END);
        fechaFin.setWidth("300px");
        fechaFin.setPlaceholder("10/10/10");
        fechaFin.setEnabled(false);

        TextField cuota = new TextField("Cuota");
        cuota.setIcon(VaadinIcons.BOOK_DOLLAR);
        cuota.setWidth("300px");
        cuota.setPlaceholder("1000$");
        cuota.setEnabled(false);

        numCuentasObjetivo.setCaption("Cuentas");
        numCuentasObjetivo.setWidth("300px");
        numCuentasObjetivo.setIcon(VaadinIcons.CLIPBOARD_TEXT);
        numCuentasObjetivo.setPlaceholder("CUENTAS OBJETIVO");

        Button editar = new Button("EDITAR CUENTA");
        editar.setWidth("300px");
        editar.setHeight("50px");
        editar.setIcon(VaadinIcons.FLIGHT_TAKEOFF);
        editar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        editar.setEnabled(false);
        editar.addClickListener(e-> {
            try {

                Date fechaFinD = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin.getValue().toString());
                CuentaObjetivo cuenta = controller.llenarCamposAct(numCuentasObjetivo.getSelectedItem().get());
                Date fechaIn = new SimpleDateFormat("yyyy-MM-dd").parse(cuenta.getFechaInicio().toString());

                System.out.println("Fin: " + fechaFinD);
                System.out.println("Inicio: " +fechaIn);
                if(fechaFinD.after(fechaIn)){
                    if(controller.actualizarCuentaObj(numCuentasObjetivo.getSelectedItem().get(), objetivo.getValue(), fechaFin.getValue().toString(), Float.parseFloat(cuota.getValue()))){
                        Notification.show("La cuenta se actualizó exitosamente!");
                        controller.setNumerosCuenta(Integer.parseInt(numCuenta));
                    }
                    else{
                        Notification.show("Hubo un problema con la actualización\nIntente de nuevo");
                    }
                }
                else
                    Notification.show("La fecha de finalización no puede ser antes que la fecha de inicio");
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

        });

        Button seleccionar = new Button("SELECCIONAR");
        seleccionar.setIcon(VaadinIcons.SELECT);
        seleccionar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        seleccionar.setWidth("300px");
        seleccionar.addClickListener(e -> {
            objetivo.setEnabled(true);
            cuota.setEnabled(true);
            fechaFin.setEnabled(true);
            editar.setEnabled(true);
            CuentaObjetivo cuentaObjetivo = controller.llenarCamposAct(numCuentasObjetivo.getSelectedItem().get());
            objetivo.setValue(cuentaObjetivo.getObjetivo());
            fechaFin.setValue(LocalDate.parse(cuentaObjetivo.getFechaFinal().toString()));
            cuota.setValue(String.valueOf(cuentaObjetivo.getCuota()));
            cuentaObjetivo.imprimir();
        });


        Button volver = new Button("ATRÁS");
        volver.setIcon(VaadinIcons.BACKSPACE_A);
        volver.setWidth("200px");
        volver.setHeight("50px");
        volver.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        volver.addClickListener(e -> atras(modificar, cuentasObjOpciones));

        modificar.addComponent(numCuentasObjetivo, "top: 200px; left: 400px");
        modificar.addComponent(seleccionar, "top: 200px; left: 750px");
        modificar.addComponent(datosObj, "top: 50px; left: 100px");
        modificar.addComponent(objetivo, "top: 350px; left: 150px");
        modificar.addComponent(fechaFin, "top: 350px; left: 600px");
        modificar.addComponent(cuota, "top: 350px; right: 150px");
        modificar.addComponent(volver, "top: 610px; right: 50px");
        modificar.addComponent(editar, "top: 550px; left: 600px");

        cuentasObjetivoCon.addComponent(modificar);
    }

    public void desactivarCuentaObjetivo(AbsoluteLayout cuentasObjetivoCon, AbsoluteLayout cuentasObjOpciones){
        cuentasObjOpciones.setVisible(false);

        AbsoluteLayout desactivar = new AbsoluteLayout();
        desactivar.setWidth("1500px");
        desactivar.setHeight("700px");

        Label desact = new Label("ESCOJA LA CUENTA");
        desact.addStyleName(ValoTheme.LABEL_H2);

        ComboBox<String> cuenDes = new ComboBox<>("Cuentas");
        cuenDes.setWidth("300px");
        cuenDes.setIcon(VaadinIcons.SELECT);
        cuenDes.setItems(controller.getNumerosCuentaObjetivo());

        Button desactivarB = new Button("DESACTIVAR");
        desactivarB.setIcon(VaadinIcons.FROWN_O);
        desactivarB.addStyleName(ValoTheme.BUTTON_PRIMARY);
        desactivarB.setWidth("300px");
        desactivarB.addClickListener(e-> {
            if(controller.desactivarCuentaObj(cuenDes.getSelectedItem().get())){
                Notification.show("La cuenta fue desactivada exitosamente");
                controller.setNumerosCuenta(Integer.parseInt(numCuenta));
            }
            else{
                Notification.show("No se pudo desactivar la cuenta\nIntente de nuevo");
            }
        });

        Button volver = new Button("ATRÁS");
        volver.setIcon(VaadinIcons.BACKSPACE_A);
        volver.setWidth("200px");
        volver.setHeight("50px");
        volver.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        volver.addClickListener(e -> atras(desactivar, cuentasObjOpciones));

        desactivar.addComponent(desact, "top: 50px; left: 50px");
        desactivar.addComponent(cuenDes, "top: 200px; left: 100px");
        desactivar.addComponent(desactivarB, "top: 500px; left: 100px");
        desactivar.addComponent(volver, "top: 610px; right: 50px");

        cuentasObjetivoCon.addComponent(desactivar);
    }

    //Métodos de los botones

    public void SeleccionarCuenta(Button.ClickEvent event) {
        numCuenta = cuentas.getSelectedItem().get();
        if (!numCuenta.equals("No seleccionado")){
            menu.getTab(1).setEnabled(true);
            menu.getTab(2).setEnabled(true);
            menu.getTab(3).setEnabled(true);
            Notification.show("Actualmente está usando la cuenta:\n       "+numCuenta);
            cuentaL.setValue("Está en la cuenta numero " + numCuenta);
            cuenta.setValue("Está en la cuenta numero " + numCuenta);
            cuentaOB.setValue("Está en la cuenta numero " + numCuenta);
            controller.setBeneficiarios(Integer.parseInt(numCuenta));
            if (controller.getPorcentajeUsado(controller.getBeneficiarios()) < 100){
                porcentajeL.setValue("No está usando el porcentaje completo para los beneficiarios");
            }
            else if (controller.getPorcentajeUsado(controller.getBeneficiarios()) == 100) {
                porcentajeL.setVisible(false);
                porcentajeL.setValue("No está usando el porcentaje completo para los beneficiarios");
            }
            estadoCuentas = controller.getEstadosCuenta(Integer.parseInt(numCuenta));
            cantEstados = estadoCuentas.size();
            estados.setItems(estadoCuentas.subList(0,8));
            cantEstados = cantEstados - 8;
            estadoActual = estadoActual + 8;
            controller.setNumerosCuenta(Integer.parseInt(numCuenta));


        }
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

    public void agregarBene(){
        if(controller.getBeneficiarios().size() == 3){
            Notification.show("No se pueden agregar más beneficiarios porque ya hay 3");
        }
    }

    public void atras(AbsoluteLayout actual, AbsoluteLayout opciones){
        actual.setVisible(false);
        opciones.setVisible(true);

    }

    public void agregarBeneficiario(int cedula, String parentezo, int porcetanje, ArrayList<BeneficiariosTabla> beneficiarios, AbsoluteLayout agregar, AbsoluteLayout tabBene){
        if (controller.getPorcentajeUsado(beneficiarios)+porcetanje<=100) {
            if (controller.getCantActBene(beneficiarios) < 3) {
                if (controller.AgregarBeneficiario(cedula, parentezo, porcetanje, Integer.parseInt(numCuenta))) {
                    Notification.show("Se agregó el beneficiario correctamente");
                }
                else{
                    preguntar(agregar, tabBene, cedula, parentezo, porcetanje);
                }
            }
            else{
                Notification.show("Ya existe la cantidad máxima de beneficiarios\nElimine alguno para agregar uno nuevo");
            }
        }
        else{
            Notification.show("El porcentaje asignado supera más del 100%");
        }
    }

    //Otros
    public boolean porcentajeCorrecto(int porcentajeEscrito, int cedula){
        int porcentaje = 0;
        for(BeneficiariosTabla ben: controller.getBeneficiarios()){
            if(Integer.parseInt(ben.getdocumentoIdentidad()) == cedula){
                porcentaje = (int) (controller.getPorcentajeUsado(controller.getBeneficiarios()) - ben.getPorcentaje());
            }
        }
        if ((porcentaje + porcentajeEscrito) <= 100)
            return true;
        return false;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {}
}
