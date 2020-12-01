package VIEW;

import CONTROLLER.ControllerUI;
import MODEL.Beneficiario;
import MODEL.BeneficiariosTabla;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class GUIBanco extends VerticalLayout implements View {

    //Controlador
    private ControllerUI controller = new ControllerUI();
    private BeneficiariosTabla beneficiariosTabla = new BeneficiariosTabla();

    //Listas
    private ArrayList<BeneficiariosTabla> beneficiariosTablaL;

    //Contenedores diferentes páginas
    private AbsoluteLayout contenedorLogIn;
    private AbsoluteLayout contenedorTabsBanco;
    private AbsoluteLayout contenedorOpciones;
    private AbsoluteLayout ver;
    private AbsoluteLayout contenedorAgregar;

    //Components
    private TextField usuario;
    private PasswordField contrasenna;
    private RadioButtonGroup<String> cuentas;
    private TabSheet menu;

    private TextField cedulaA;
    private ComboBox<String> parentezcoA;
    private TextField porc;
    private Button agregar;


    //Atributos para guardar
    private String nombreUsuario;
    private String contra;
    private String numCuenta;


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
        menu = new TabSheet();
        menu.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
        menu.addStyleName(ValoTheme.TABSHEET_FRAMED);
        Cuentas();
        Beneficiarios();

        return menu;

    }

    public void Cuentas(){
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

    public void Beneficiarios(){


        AbsoluteLayout contenedorBeneficiarios = new AbsoluteLayout();
        contenedorBeneficiarios.setWidth("1500px");
        contenedorBeneficiarios.setHeight("700px");

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
        verBeneficiarios.addClickListener(e->VerBeneficiarios(contenedorOpciones, contenedorBeneficiarios));

        Button agregarBeneficiarios = new Button("AGREGAR BENEFICIARIO");
        agregarBeneficiarios.addStyleName(ValoTheme.BUTTON_PRIMARY);
        agregarBeneficiarios.setIcon(VaadinIcons.ADD_DOCK);
        agregarBeneficiarios.setWidth("300px");
        agregarBeneficiarios.setHeight("50px");
        agregarBeneficiarios.addClickListener(e->AgregarBeneficiario(contenedorOpciones, contenedorBeneficiarios));

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
        contenedorOpciones.addComponent(opciones, "top: 50px; left: 100px");
        contenedorOpciones.addComponent(verBeneficiarios, "top: 170px; left: 100px");
        contenedorOpciones.addComponent(agregarBeneficiarios, "top: 270px; left: 100px");
        contenedorOpciones.addComponent(actualizarBeneficiarios, "top: 370px; left: 100px");
        contenedorOpciones.addComponent(eliminarBeneficiarios, "top: 470px; left: 100px");

        contenedorBeneficiarios.addComponent(contenedorOpciones, "top: 0px; left: 0px");


        menu.addTab(contenedorBeneficiarios, "BENEFICIARIOS").setEnabled(false);
    }

    public void VerBeneficiarios (AbsoluteLayout opciones, AbsoluteLayout contenedor) {
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

        float porcentaje = controller.getPorcentajeUsado(beneficiariosTablaL);

        float sobrante = 100 - porcentaje;


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
        volver.addClickListener(e-> atras(ver, contenedorOpciones));

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

    public void AgregarBeneficiario(AbsoluteLayout opciones, AbsoluteLayout tabBene){
        opciones.setVisible(false);

        contenedorAgregar = new AbsoluteLayout();
        contenedorAgregar.setWidth("1500px");
        contenedorAgregar.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoVerBene.png"));
        Image image = new Image("", resource);
        image.setWidth("1500px");
        image.setHeight("700px");

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
        porc.setIcon(VaadinIcons.DOLLAR);
        porc.setPlaceholder("0%");
        porc.setWidth("300px");

        agregar = new Button("AGREGAR");
        agregar.setIcon(VaadinIcons.ADD_DOCK);
        agregar.setStyleName("primary");
        agregar.setWidth("300px");
        agregar.addClickListener(e -> agregarBeneficiario(Integer.parseInt(cedulaA.getValue()), parentezcoA.getSelectedItem().get(), Float.parseFloat(porc.getValue()), controller.getBeneficiarios(), contenedorAgregar, tabBene));

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(j -> atras(contenedorAgregar, contenedorOpciones));

        contenedorAgregar.addComponent(agregarL, "top: 50px; left: 100px");
        contenedorAgregar.addComponent(cedulaA, "top: 200px; left: 100px");
        contenedorAgregar.addComponent(parentezcoA, "top: 300px; left: 100px");
        contenedorAgregar.addComponent(porc, "top: 400px; left: 100px");
        contenedorAgregar.addComponent(agregar, "top: 500px; left: 100px");
        contenedorAgregar.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(contenedorAgregar);
    }

    public void agregarBeneNuevo(AbsoluteLayout tabBene){
        contenedorAgregar.setVisible(false);

        AbsoluteLayout contenedorAgregarComplejo = new AbsoluteLayout();
        contenedorAgregarComplejo.setWidth("1500px");
        contenedorAgregarComplejo.setHeight("700px");

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

        TextField porcentaje = new TextField("Porcentaje");
        porcentaje.setIcon(VaadinIcons.DOLLAR);
        porcentaje.setWidth("300px");
        porcentaje.setPlaceholder("0%");

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

        Button agregar = new Button("AGREGAR");
        agregar.setIcon(VaadinIcons.ADD_DOCK);
        agregar.setStyleName(ValoTheme.BUTTON_PRIMARY);
        agregar.setWidth("300px");
        agregar.setHeight("50px");
        agregar.addClickListener(e ->{
            if(controller.getPorcentajeUsado(controller.getBeneficiarios())+Integer.parseInt(porcentaje.getValue()) <=100){
                if (controller.getCantActBene(controller.getBeneficiarios())<3){
                    boolean devolvio = controller.agregarBeneficiarioComplejo(Integer.parseInt(numCuenta), Integer.parseInt(cedula.getValue()),parentezco.getSelectedItem().get(), nombre.getValue(), tipoDoc.getSelectedItem().get(), fechaNac.getValue().toString(), Integer.parseInt(porcentaje.getValue()), email.getValue(), Integer.parseInt(tel1.getValue()),Integer.parseInt(tel2.getValue()));
                    if(devolvio){
                        Notification.show("Beneficiario agregado correctamente");
                        contenedorAgregarComplejo.setVisible(false);
                        contenedorOpciones.setVisible(true);
                    }
                    else{
                        Notification.show("Hubo un problema agregando el beneficiario");
                    }
                }
                else{
                    Notification.show("Ya existe la cantidad máxima de beneficiarios");
                }
            }
            else{
                Notification.show("El porcentaje supera el 100%");
            }
        });

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(j -> atras(contenedorAgregar, contenedorOpciones));

        contenedorAgregarComplejo.addComponent(datos, "top: 50px; left: 50px");
        contenedorAgregarComplejo.addComponent(datosPersonales, "top: 100px; left: 650px");
        contenedorAgregarComplejo.addComponent(nombre, "top: 200px; left: 75px");
        contenedorAgregarComplejo.addComponent(tipoDoc,"top: 200px; left: 425px");
        contenedorAgregarComplejo.addComponent(cedula, "top: 200px; right: 425px");
        contenedorAgregarComplejo.addComponent(fechaNac, "top: 200px; right: 75px");

        contenedorAgregarComplejo.addComponent(relPorc, "top: 270px; left: 630px");
        contenedorAgregarComplejo.addComponent(parentezco, "top: 350px; left: 400px");
        contenedorAgregarComplejo.addComponent(porcentaje, "top: 350px; right: 400px");

        contenedorAgregarComplejo.addComponent(contacto, "top: 420px; left: 700px");
        contenedorAgregarComplejo.addComponent(email, "top: 500px; left: 250px");
        contenedorAgregarComplejo.addComponent(tel1, "top: 500px; left: 600px");
        contenedorAgregarComplejo.addComponent(tel2, "top: 500px; right: 250px");

        contenedorAgregarComplejo.addComponent(agregar, "top: 600px; left: 600px");
        contenedorAgregarComplejo.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(contenedorAgregarComplejo, "top: 0px, left: 0px");

    }

    public void preguntar(AbsoluteLayout agregarCont, AbsoluteLayout tabBene){
        AbsoluteLayout preguntaC = new AbsoluteLayout();
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
        si.addClickListener(e -> agregarBeneNuevo(tabBene));

        Button no = new Button("NO");
        no.setIcon(VaadinIcons.CLOSE_SMALL);
        no.setWidth("250px");
        no.addStyleName(ValoTheme.BUTTON_DANGER);

        preguntaC.addComponent(notificacion, "top: 0px; left: 30px");
        preguntaC.addComponent(pregunta, "top: 50px; left: 200px");
        preguntaC.addComponent(si, "top: 100px; left: 0px");
        preguntaC.addComponent(no, "top: 100px; left: 300px");

        no.addClickListener(e -> {
            notificacion.setVisible(false);
            pregunta.setVisible(false);
            si.setVisible(false);
            no.setVisible(false);
            cedulaA.setEnabled(true);
            porc.setEnabled(true);
            parentezcoA.setEnabled(true);
            agregar.setEnabled(true);
        });

        agregarCont.addComponent(preguntaC, "top: 200px; left: 650px");
    }

    public void actualizarBeneficiario(AbsoluteLayout opciones, AbsoluteLayout tabBene){
        opciones.setVisible(false);

        AbsoluteLayout actualizarBene = new AbsoluteLayout();
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
        confirmar.addClickListener(e -> actualizarBeneficiarios(actualizarBene,tabBene, Integer.parseInt(beneficiarios.getSelectedItem().get())));

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(e -> atras(actualizarBene, contenedorOpciones));

        actualizarBene.addComponent(act, "top: 50; left: 50px");
        actualizarBene.addComponent(beneficiarios, "top: 200px; left: 50px");
        actualizarBene.addComponent(confirmar, "top: 400px; left: 50px");
        actualizarBene.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(actualizarBene);
    }

    public void actualizarBeneficiarios(AbsoluteLayout act1, AbsoluteLayout tabBene, int cedulaG){
        act1.setVisible(false);

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
        porcentaje.setIcon(VaadinIcons.DOLLAR);
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
        atras.addClickListener(e -> atras(actualizarBen, contenedorAgregar));

        Button actualizar = new Button("ACTUALIZAR");
        actualizar.setIcon(VaadinIcons.UPLOAD);
        actualizar.setStyleName(ValoTheme.BUTTON_PRIMARY);
        actualizar.setWidth("300px");
        actualizar.setHeight("50px");
        actualizar.addClickListener(e -> {
            if(controller.actualizarBen(cedulaG, Integer.parseInt(cedula.getValue()), nombre.getValue(),parentezco.getSelectedItem().get(),fechaNac.getValue().toString(),tipoDoc.getSelectedItem().get(), Integer.parseInt(porcentaje.getValue()), email.getValue(), Integer.parseInt(tel1.getValue()), Integer.parseInt(tel2.getValue()))){
                Notification.show("Se actualizó correctamente el beneficiario");
                actualizarBen.setVisible(false);
                contenedorOpciones.setVisible(true);
            }
            else{
                Notification.show("Hubo un problema al agregar el beneficiario\nIntentelo de nuevo más tarde");
            }
        });


        actualizarBen.addComponent(datos, "top: 50px; left: 50px");
        actualizarBen.addComponent(datosPersonales, "top: 100px; left: 650px");
        actualizarBen.addComponent(nombre, "top: 200px; left: 75px");
        actualizarBen.addComponent(tipoDoc,"top: 200px; left: 425px");
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

    public void eliminarBeneficiario(AbsoluteLayout opciones, AbsoluteLayout tabBene){
        opciones.setVisible(false);

        AbsoluteLayout contenedorEliminar = new AbsoluteLayout();
        contenedorEliminar.setWidth("1500px");
        contenedorEliminar.setHeight("700px");

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
            if(devolver){
                Notification.show("Se eliminó el beneficiario correctamente");
            }
            else{
                Notification.show("Hubo un error con la eliminación\nIntentelo más tarde");
            }
        });

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(j -> atras(contenedorEliminar, contenedorOpciones));

        contenedorEliminar.addComponent(label, "top: 50px; left: 50px");
        contenedorEliminar.addComponent(beneficiariosE, "top: 200px; left: 50px");
        contenedorEliminar.addComponent(eliminar, "top: 400px; left: 50px");
        contenedorEliminar.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(contenedorEliminar);
    }

    //Métodos de los botones

    public void SeleccionarCuenta(Button.ClickEvent event) {
        numCuenta = cuentas.getSelectedItem().get();
        if (!numCuenta.equals("No seleccionado")){
            menu.getTab(1).setEnabled(true);
            Notification.show("Actualmente está usando la cuenta:\n       "+numCuenta);
            controller.setBeneficiarios(Integer.parseInt(numCuenta));

            //Agregar las otras tabs
        }
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

    public void agregarBene(){
        if(controller.getBeneficiarios().size() == 3){
            Notification.show("No se pueden agregar más beneficiarios porque ya hay 3");
        }
    }

    public void atras(AbsoluteLayout actual, AbsoluteLayout opciones){
        actual.setVisible(false);
        opciones.setVisible(true);

    }

    public void agregarBeneficiario(int cedula, String parentezo, float porcetanje, ArrayList<BeneficiariosTabla> beneficiarios, AbsoluteLayout agregar, AbsoluteLayout tabBene){
        if (controller.getPorcentajeUsado(beneficiarios)+porcetanje<=100) {
            if (controller.getCantActBene(beneficiarios) < 3) {
                if (controller.AgregarBeneficiario(cedula, parentezo, porcetanje, Integer.parseInt(numCuenta))) {
                    Notification.show("Se agregó el beneficiario correctamente");
                }
                else{
                    preguntar(agregar, tabBene);
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {}
}
