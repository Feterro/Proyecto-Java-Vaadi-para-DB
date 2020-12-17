package VIEW;

import CONTROLLER.ControllerUI;
import MODEL.Beneficiario;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class BeneficiariosV {


    private Label cuentaL;
    private Label porcentajeL;
    private ComboBox<String> parentezcoA;
    private TextField porc;
    private TextField cedulaA;
    private Button agregar;

    private ControllerUI controller = ControllerUI.getInstance();

    private static BeneficiariosV beneficiariosV;

    public BeneficiariosV() {
    }

    public static BeneficiariosV getInstance() {
        if (beneficiariosV == null) {
            beneficiariosV = new BeneficiariosV();
        }
        return beneficiariosV;
    }

    public Label getCuentaL() {
        return cuentaL;
    }

    public Label getPorcentajeL() {
        return porcentajeL;
    }

    //principal

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

        AbsoluteLayout contenedorOpciones = new AbsoluteLayout();
        contenedorOpciones.setWidth("1500px");
        contenedorOpciones.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoBene.png"));
        Image fondoBeneficiarios = new Image("", resource);
        fondoBeneficiarios.setWidth("1500px");
        fondoBeneficiarios.setHeight("700px");

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

        contenedorOpciones.addComponent(fondoBeneficiarios, "top: 0px; left: 0px");
        contenedorOpciones.addComponent(cuentaL, "top: 50px; right: 50px");
        contenedorOpciones.addComponent(porcentajeL, "top: 100px; right: 50px");
        contenedorOpciones.addComponent(opciones, "top: 50px; left: 100px");
        contenedorOpciones.addComponent(verBeneficiarios, "top: 170px; left: 100px");
        contenedorOpciones.addComponent(agregarBeneficiarios, "top: 270px; left: 100px");
        contenedorOpciones.addComponent(actualizarBeneficiarios, "top: 370px; left: 100px");
        contenedorOpciones.addComponent(eliminarBeneficiarios, "top: 470px; left: 100px");

        contenedorBeneficiarios.addComponent(contenedorOpciones, "top: 0px; left: 0px");


        TabSheet menu = controller.getMenu();
        menu.addTab(contenedorBeneficiarios, "BENEFICIARIOS");
    }

    //Ver beneficiarios

    public void VerBeneficiarios(AbsoluteLayout opciones, AbsoluteLayout contenedor) {
        opciones.setVisible(false);

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoVerBene.png"));
        Image fondoVerBeneficiarios = new Image("", resource);
        fondoVerBeneficiarios.setWidth("1500px");
        fondoVerBeneficiarios.setHeight("700px");

        AbsoluteLayout ver = new AbsoluteLayout();

        ver.setHeight("700px");
        ver.setWidth("1500px");

        Label bene = new Label("DATOS DE LOS BENEFICIARIOS ACTUALES");
        bene.addStyleName(ValoTheme.LABEL_H2);
        bene.addStyleName(ValoTheme.LABEL_BOLD);


        Grid<Beneficiario> beneficiarios = new Grid<>(Beneficiario.class);
        beneficiarios.setHeight("152px");
        beneficiarios.setWidth("700px");
        beneficiarios.setColumnOrder("nombre", "valorDocIdent", "porcentaje");
        beneficiarios.removeColumn("email");
        beneficiarios.removeColumn("fechaNac");
        beneficiarios.removeColumn("tel1");
        beneficiarios.removeColumn("tel2");
        beneficiarios.removeColumn("tipoDocIdent");
        beneficiarios.removeColumn("activo");
        beneficiarios.removeColumn("parentesco");
        beneficiarios.removeColumn("cuenta");
        ArrayList<Beneficiario> beneficiariosTablaL = controller.llenarTabla();
        beneficiarios.setItems(beneficiariosTablaL);

        int porcentaje = controller.getPorcentajeUsado();
        int sobrante = 100 - porcentaje;


        Label resumen = new Label("RESUMEN DATOS");
        resumen.addStyleName(ValoTheme.LABEL_H2);
        resumen.addStyleName(ValoTheme.LABEL_BOLD);
        Label cantBeneficiarios = new Label("Total de beneficiarios: " + controller.getCantActBene());
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
        volver.addClickListener(e -> atras(ver, opciones));

        ver.addComponent(fondoVerBeneficiarios, "top: 0px; left: 0px");
        ver.addComponent(bene, "top: 50px; left: 50px");
        ver.addComponent(beneficiarios, "top: 200px; left: 350px");
        ver.addComponent(volver, "top: 610px; right: 50px");
        ver.addComponent(resumen, "top: 400px; left: 50px");
        ver.addComponent(cantBeneficiarios, "top: 450px; left: 350px");
        ver.addComponent(porcentajeActual, "top: 475px; left: 350px");
        ver.addComponent(sobranteL, "top: 500px; left: 350px");
        contenedor.addComponent(ver);
    }

    //Agregar beneficiarios

    public void AgregarBeneficiario(AbsoluteLayout opciones, AbsoluteLayout tabBene) {
        opciones.setVisible(false);

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoAgregarBene.png"));
        Image fondoAgregarBen = new Image("", resource);
        fondoAgregarBen.setWidth("1500px");
        fondoAgregarBen.setHeight("700px");

        AbsoluteLayout contenedorAgregar = new AbsoluteLayout();
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
            if (cedulaA.isEmpty() || porc.isEmpty() || parentezcoA.isEmpty())
                Notification.show("CAMPOS VACÍOS", "Complete todos los espacios para continuar", Notification.Type.WARNING_MESSAGE);
            else
                agregarBeneficiario(Integer.parseInt(cedulaA.getValue()), parentezcoA.getSelectedItem().get(), Integer.parseInt(porc.getValue()), contenedorAgregar, tabBene, opciones);
        });

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(j -> atras(contenedorAgregar, opciones));

        contenedorAgregar.addComponent(fondoAgregarBen);
        contenedorAgregar.addComponent(agregarL, "top: 50px; left: 100px");
        contenedorAgregar.addComponent(cedulaA, "top: 200px; left: 100px");
        contenedorAgregar.addComponent(parentezcoA, "top: 300px; left: 100px");
        contenedorAgregar.addComponent(porc, "top: 400px; left: 100px");
        contenedorAgregar.addComponent(agregar, "top: 500px; left: 100px");
        contenedorAgregar.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(contenedorAgregar);
    }

    public void agregarBeneNuevo(AbsoluteLayout tabBene, AbsoluteLayout contenedorAgregar, AbsoluteLayout opciones, AbsoluteLayout pregunta, int cedulaPuesta, String parentezcoPuesto, int porcenataje) {
        contenedorAgregar.setVisible(false);

        AbsoluteLayout contenedorAgregarComplejo = new AbsoluteLayout();
        contenedorAgregarComplejo.setWidth("1500px");
        contenedorAgregarComplejo.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoAgregarBene2.png"));
        Image fondoAgregarBenNuev = new Image("", resource);
        fondoAgregarBenNuev.setWidth("1500px");
        fondoAgregarBenNuev.setHeight("700px");

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
            if (!nombre.isEmpty() || !tipoDoc.getSelectedItem().isPresent() || !cedula.isEmpty() || !fechaNac.isEmpty() || !parentezco.getSelectedItem().isPresent() || !porc.isEmpty() || !email.isEmpty() || !tel1.isEmpty() || !tel2.isEmpty()) {
                if (controller.getPorcentajeUsado() + Integer.parseInt(porcentaje.getValue()) <= 100) {
                    if (controller.getCantActBene() < 3) {
                        boolean devolvio = controller.agregarBeneficiarioComplejo(Integer.parseInt(cedula.getValue()), parentezco.getSelectedItem().get(), nombre.getValue(), tipoDoc.getSelectedItem().get(), fechaNac.getValue().toString(), Integer.parseInt(porcentaje.getValue()), email.getValue(), Integer.parseInt(tel1.getValue()), Integer.parseInt(tel2.getValue()));
                        if (devolvio) {
                            Notification.show("BENEFICIARIO AGREGADO", "Correctamente", Notification.Type.TRAY_NOTIFICATION);
                            contenedorAgregarComplejo.setVisible(false);
                            opciones.setVisible(true);
                        } else {
                            Notification.show("NO SE AGREGÓ", "Hubo un problema agregando el beneficiario", Notification.Type.ERROR_MESSAGE);
                        }
                    } else {
                        Notification.show("CANTIDAD MÁXIMA", "Elimine uno para continuar", Notification.Type.WARNING_MESSAGE);
                    }
                } else {
                    Notification.show("PORCENTAJE MAYOR A 100%", "Solo tiene disponible, " + (100 - controller.getPorcentajeUsado()), Notification.Type.WARNING_MESSAGE);
                }
            } else {
                Notification.show("CAMPOS VACÍOS", "Alguno de los espacios está sin llenar", Notification.Type.WARNING_MESSAGE);
            }
            controller.setBeneficiarios();
            if (controller.getPorcentajeUsado() == 100)
                porcentajeL.setVisible(false);
        });

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(j -> {
            atras(contenedorAgregarComplejo, contenedorAgregar);
            pregunta.setVisible(false);
            cedulaA.setEnabled(true);
            porc.setEnabled(true);
            parentezcoA.setEnabled(true);
            agregar.setEnabled(true);
            cedulaA.clear();
            porc.clear();
            parentezcoA.clear();
        });

        contenedorAgregarComplejo.addComponent(fondoAgregarBenNuev);

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

    public void preguntar(AbsoluteLayout agregarCont, AbsoluteLayout tabBene, AbsoluteLayout opciones, int cedula, String parentezco, int porcentaje) {
        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondoPregunta.png"));
        Image fondoPregunta = new Image("", resource);
        fondoPregunta.setWidth("700px");
        fondoPregunta.setHeight("500px");

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
        si.addClickListener(e -> agregarBeneNuevo(tabBene, agregarCont, opciones, preguntaC, cedula, parentezco, porcentaje));

        Button no = new Button("NO");
        no.setIcon(VaadinIcons.CLOSE_SMALL);
        no.setWidth("250px");
        no.addStyleName(ValoTheme.BUTTON_DANGER);

        preguntaC.addComponent(fondoPregunta, "top: 0px; left: 0px");
        preguntaC.addComponent(notificacion, "top: 100px; left: 100px");
        preguntaC.addComponent(pregunta, "top: 150px; left: 270px");
        preguntaC.addComponent(si, "top: 200px; left: 80px");
        preguntaC.addComponent(no, "top: 200px; left: 380px");

        no.addClickListener(e -> repuestaNo(preguntaC));

        agregarCont.addComponent(preguntaC, "top: 150px; left: 450px");
    }

    //Actualizar beneficiarios

    public void actualizarBeneficiario(AbsoluteLayout opciones, AbsoluteLayout tabBene) {
        opciones.setVisible(false);

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoAgregarBene.png"));
        Image fondoActualizar = new Image("", resource);
        fondoActualizar.setWidth("1500px");
        fondoActualizar.setHeight("700px");

        AbsoluteLayout actualizarBene = new AbsoluteLayout();
        actualizarBene.setHeight("700px");
        actualizarBene.setWidth("1500px");

        Label act = new Label("ACTUALIZAR BENEFICIARIO");
        act.addStyleName(ValoTheme.LABEL_H2);

        ComboBox<String> beneficiarios = new ComboBox<>("Beneficiarios");
        beneficiarios.setIcon(VaadinIcons.GROUP);
        beneficiarios.setItems(controller.getCedulasBen());
        beneficiarios.setWidth("300px");
        beneficiarios.setPlaceholder("No seleccionado");

        Button confirmar = new Button("CONFIRMAR");
        confirmar.setIcon(VaadinIcons.CHECK_CIRCLE);
        confirmar.setWidth("300px");
        confirmar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        confirmar.addClickListener(e -> actualizarBeneficiarios(actualizarBene, tabBene, opciones, Integer.parseInt(beneficiarios.getSelectedItem().get()), beneficiarios));

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(e -> atras(actualizarBene, opciones));

        actualizarBene.addComponent(fondoActualizar);
        actualizarBene.addComponent(act, "top: 50; left: 50px");
        actualizarBene.addComponent(beneficiarios, "top: 200px; left: 100px");
        actualizarBene.addComponent(confirmar, "top: 400px; left: 100px");
        actualizarBene.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(actualizarBene);
    }

    public void actualizarBeneficiarios(AbsoluteLayout act1, AbsoluteLayout tabBene, AbsoluteLayout opciones, int cedulaG, ComboBox<String> beneficiarios) {
        act1.setVisible(false);

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/FondoAgregarBene2.png"));
        Image fondoActualizarCom = new Image("", resource);
        fondoActualizarCom.setWidth("1500px");
        fondoActualizarCom.setHeight("700px");

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
            atras(actualizarBen, act1);
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
                    Notification.show("ACTUALIZACIÓN", "Exitosa", Notification.Type.TRAY_NOTIFICATION);
                    actualizarBen.setVisible(false);
                    opciones.setVisible(true);
                } else {
                    Notification.show("ACTUALIZACIÓN", "No se pudo actualizar el beneficiario", Notification.Type.ERROR_MESSAGE);
                }
            } else {
                Notification.show("PORCENTAJE MAYOR A 100%", "Tiene disponible solo " + (100 - controller.getPorcentajeUsado()), Notification.Type.WARNING_MESSAGE);
            }
            controller.setBeneficiarios();
            if (controller.getPorcentajeUsado() == 100) {
                porcentajeL.setVisible(false);
            } else if (controller.getPorcentajeUsado() < 100)
                porcentajeL.setVisible(true);
        });

        actualizarBen.addComponent(fondoActualizarCom);
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
        Image fondoEliminar = new Image("", resource);
        fondoEliminar.setWidth("1500px");
        fondoEliminar.setHeight("700px");

        Label label = new Label("ELIMINAR BENEFICIARIO");
        label.addStyleName(ValoTheme.LABEL_H2);

        ComboBox<String> beneficiariosE = new ComboBox<>("Beneficiarios");
        beneficiariosE.setIcon(VaadinIcons.GROUP);
        beneficiariosE.setPlaceholder("Sin selección");
        beneficiariosE.setWidth("300px");
        beneficiariosE.setItems(controller.getCedulasBen());

        Button eliminar = new Button("ELIMINAR");
        eliminar.setIcon(VaadinIcons.CLOSE_CIRCLE);
        eliminar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        eliminar.setWidth("300px");
        eliminar.addClickListener(e -> {
            boolean devolver = controller.eliminarBene(Integer.parseInt(beneficiariosE.getSelectedItem().get()));
            if (devolver) {
                Notification.show("ELIMINACIÓN", "Exitosa", Notification.Type.TRAY_NOTIFICATION);
                beneficiariosE.clear();
                beneficiariosE.setItems(controller.getCedulasBen());
            } else {
                Notification.show("ELIMINACIÓN", "No se pudo realizar", Notification.Type.ERROR_MESSAGE);
            }
            controller.setBeneficiarios();
            if (controller.getPorcentajeUsado() < 100)
                porcentajeL.setVisible(true);
        });

        Button atras = new Button("ATRÁS");
        atras.setIcon(VaadinIcons.BACKSPACE_A);
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(j -> atras(contenedorEliminar, opciones));

        contenedorEliminar.addComponent(fondoEliminar);
        contenedorEliminar.addComponent(label, "top: 50px; left: 50px");
        contenedorEliminar.addComponent(beneficiariosE, "top: 200px; left: 100px");
        contenedorEliminar.addComponent(eliminar, "top: 400px; left: 100px");
        contenedorEliminar.addComponent(atras, "top: 610px; right: 50px");

        tabBene.addComponent(contenedorEliminar);
    }

    //Métodos de interfaz

    //Agregar

    public void agregarBeneficiario(int cedula, String parentezo, int porcetanje, AbsoluteLayout agregar, AbsoluteLayout tabBene, AbsoluteLayout opciones) {
        if (controller.getPorcentajeUsado() + porcetanje <= 100) {
            if (controller.getCantActBene() < 3) {
                if (controller.AgregarBeneficiario(cedula, parentezo, porcetanje)) {
                    Notification.show("AGREGAR BENEFICIARIO", "Correcto", Notification.Type.TRAY_NOTIFICATION);
                } else {
                    preguntar(agregar, tabBene, opciones, cedula, parentezo, porcetanje);
                }
            } else {
                Notification.show("CANTIDAD MÁSXIMA SUPERADA", "Elimine alguno para agregar uno nuevo", Notification.Type.WARNING_MESSAGE);
            }
        } else {
            Notification.show("MÁS DEL 100%", "Solo hay dispoible " + (100 - controller.getPorcentajeUsado()), Notification.Type.WARNING_MESSAGE);
        }
        controller.setBeneficiarios();
        if (controller.getPorcentajeUsado() == 100)
            porcentajeL.setVisible(false);
    }

    public void repuestaNo(AbsoluteLayout pregunta) {
        pregunta.setVisible(false);
        cedulaA.setEnabled(true);
        porc.setEnabled(true);
        parentezcoA.setEnabled(true);
        agregar.setEnabled(true);
    }

    //Modificar

    public boolean porcentajeCorrecto(int porcentajeEscrito, int cedula) {
        int porcentaje = 0;
        for (Beneficiario ben : controller.getBeneficiarios()) {
            if (ben.getValorDocIdent() == cedula) {
                porcentaje = (controller.getPorcentajeUsado() - ben.getPorcentaje());
            }
        }
        if ((porcentaje + porcentajeEscrito) <= 100)
            return true;
        return false;
    }

    //Otros

    public void atras(AbsoluteLayout actual, AbsoluteLayout opciones) {
        actual.setVisible(false);
        opciones.setVisible(true);
    }
}