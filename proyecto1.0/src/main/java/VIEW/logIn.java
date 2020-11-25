package VIEW;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import conexion.Beneficiario;
import CONTROLLER.ControllerConexion;

import java.util.ArrayList;
import java.util.stream.IntStream;

//@StyleSheet("vaadin://login.css")
public class logIn extends VerticalLayout implements View {
    TextField usuario;
    PasswordField contrasenna;
    Panel loginP;

    Button entrar;
    String nombreUsuario;
    String contr;
    Beneficiario beneficiario = new Beneficiario();

    TextField cedulaA;
    TextField porc;
    ComboBox <String> parentezcoA;
    ComboBox <String> cuentas;
    ComboBox <String> beneficiariosE;
    int cuentaCombo;
    ArrayList<String> Cuentas;
    ComboBox<String> cuentaE;
    TextField BeneDoc;
    TextField nombre;
    TextField cedula;
    ComboBox <String> tipoDoc;
    TextField fechaNac;
    ComboBox <String> parentezco;
    TextField porcentaje;
    TextField email;
    TextField tel1;
    TextField tel2;



    public logIn() {

        usuario = new TextField("Usuario");
        usuario.setIcon(VaadinIcons.USER);
        usuario.setSizeFull();

        contrasenna = new PasswordField("Contraseña");
        contrasenna.setIcon(VaadinIcons.KEY);
        contrasenna.setSizeFull();

        entrar = new Button("Ingresar");
        entrar.setStyleName("primary");
        entrar.setIcon(VaadinIcons.CHECK);
        entrar.setSizeFull();

        FormLayout cont = new FormLayout(usuario, contrasenna, entrar);
        cont.setMargin(true);

        loginP = new Panel("Ingreso de sesión", cont);
        loginP.setStyleName("login");
        loginP.setWidth("500");
        loginP.setHeight("250");

        addComponent(loginP);
        setComponentAlignment(loginP, Alignment.MIDDLE_CENTER);
        setStyleName("login");
        entrar.addClickListener(this::obtenerDatos);

    }

    public void obtenerDatos(Button.ClickEvent event) {
        nombreUsuario = usuario.getValue();
        contr = contrasenna.getValue();
        String contra = beneficiario.getContrasenna(ControllerConexion.getInstance().connection, nombreUsuario);
        if (contr.equals(contra)){
            loginP.setVisible(false);
            banco();
            System.out.println("esta bien");
        }
        else{
            System.out.println("Esta mal");
        }
    }

    public void banco() {
        HorizontalLayout lay = new HorizontalLayout();
        TabSheet principal = new TabSheet();
        Button boton2 = new Button("Boton2");

        ArrayList<String> Parentezcos = beneficiario.getListaParentescos(ControllerConexion.getInstance().connection);
//        Cuentas = beneficiario.getVisibles(Conector.getInstance().connection, nombreUsuario);
        ArrayList<String> tipos = beneficiario.getListaTipoDocIden(ControllerConexion.getInstance().connection);
        ArrayList<String> bene = beneficiario.getCedulasBeneficiarios(ControllerConexion.getInstance().connection, cuentaCombo);

        //Beneficiarios
        Accordion beneficiarios = new Accordion();

        //Actualizar beneficiario

        AbsoluteLayout actualizarBeneficiario = new AbsoluteLayout();

        actualizarBeneficiario.setWidth("1000px");
        actualizarBeneficiario.setHeight("600px");

        Label datosPersonales = new Label("Datos Personales");
        nombre = new TextField("Nombre");
        nombre.setIcon(VaadinIcons.USER_CHECK);
        cedula = new TextField("Documento identidad");
        cedula.setIcon(VaadinIcons.TEXT_LABEL);
        fechaNac = new TextField("Fecha Nacimiento");
        fechaNac.setIcon(VaadinIcons.CALENDAR_USER);

        Label relPorc = new Label("Relación y porcentaje");
        parentezco = new ComboBox<>("Parentezo");
        parentezco.setItems(Parentezcos);
        parentezco.setValue("Sin selección");
        parentezco.setIcon(VaadinIcons.FAMILY);
        parentezco.setWidth("300px");

        porcentaje = new TextField("Porcentaje");
        porcentaje.setIcon(VaadinIcons.DOLLAR);
        porcentaje.setWidth("300px");

        Label contacto = new Label("Contacto");
        email = new TextField("Email");
        email.setIcon(VaadinIcons.ENVELOPE);
        tel1 = new TextField("Telefono 1");
        tel1.setIcon(VaadinIcons.PHONE);
        tel2 = new TextField("Telefono 2");
        tel2.setIcon(VaadinIcons.PHONE);

        tipoDoc = new ComboBox<>("Tipo Documento Identidad");
        tipoDoc.setItems(tipos);

        Button actualizar = new Button("ACTUALIZAR");
        actualizar.setIcon(VaadinIcons.UPLOAD);
        actualizar.setStyleName("primary");
        actualizar.addClickListener(this::Actualiza);

        BeneDoc = new TextField("Documento de Identidad");

        Button agarrar = new Button("SELECCIONAR");
        agarrar.setIcon(VaadinIcons.UPLOAD);
        agarrar.setStyleName("primary");
        agarrar.addClickListener(this::SeleccionarInfo);


        actualizarBeneficiario.addComponent(agarrar, "top: 20px; left: 410px");
        actualizarBeneficiario.addComponent(BeneDoc, "top: 20px; left: 100px");
        actualizarBeneficiario.addComponent(datosPersonales, "top: 70px; left: 410px");
        actualizarBeneficiario.addComponent(nombre, "top: 170px; left: 50px");
        actualizarBeneficiario.addComponent(cedula, "top: 170px; left: 250px");
        actualizarBeneficiario.addComponent(fechaNac, "top: 170px; left: 700px");
        actualizarBeneficiario.addComponent(tipoDoc,"top: 170px; left: 480px");

        actualizarBeneficiario.addComponent(relPorc, "top: 250px; left: 430px");
        actualizarBeneficiario.addComponent(parentezco, "top: 330px; left: 175px");
        actualizarBeneficiario.addComponent(porcentaje, "top: 330px; right: 175px");

        actualizarBeneficiario.addComponent(contacto, "top: 400px; left: 470px");
        actualizarBeneficiario.addComponent(email, "top: 460px; left: 100px");
        actualizarBeneficiario.addComponent(tel1, "top: 460px; left: 410px");
        actualizarBeneficiario.addComponent(tel2, "top: 460px; right: 100");

        actualizarBeneficiario.addComponent(actualizar, "top: 550px; left: 430px");

        //Agregar beneficiario
        AbsoluteLayout agregarBeneficiario = new AbsoluteLayout();

        agregarBeneficiario.setWidth("1000px");
        agregarBeneficiario.setHeight("500px");

        Label agregarL = new Label("Complete los siguientes espacios para agregar un nuevo beneficiario");
        agregarL.setSizeFull();

        cedulaA = new TextField("Documento de identidad");
        cedulaA.setIcon(VaadinIcons.USER_CHECK);

        parentezcoA = new ComboBox<>("Parentezco");
        parentezcoA.setIcon(VaadinIcons.FAMILY);
        parentezcoA.setItems(Parentezcos);
        parentezcoA.setValue("Sin selección");

        cuentas = new ComboBox<>("Cuentas");
        cuentas.setIcon(VaadinIcons.DOLLAR);
        cuentas.setItems(Cuentas);
        cuentas.setValue("Sin selección");

        porc = new TextField("Porcentaje");
        porc.setIcon(VaadinIcons.DOLLAR);

        Button agregar = new Button("AGREGAR");
        agregar.setIcon(VaadinIcons.ADD_DOCK);
        agregar.setStyleName("primary");
        agregar.addClickListener(this::ActualizarBenef);

        agregarBeneficiario.addComponent(agregarL, "top: 25px; left: 100px");
        agregarBeneficiario.addComponent(cedulaA, "top: 100px; left: 100px");
        agregarBeneficiario.addComponent(parentezcoA, "top: 200px; left: 100px");
        agregarBeneficiario.addComponent(porc, "top: 300px; left: 100px");
        agregarBeneficiario.addComponent(agregar, "top: 400px; left: 300px");
        agregarBeneficiario.addComponent(cuentas, "top: 400px; left: 100px");


        //Eliminar Beneficiario
        AbsoluteLayout eliminarBeneficiario = new AbsoluteLayout();
        eliminarBeneficiario.setHeight("500px");
        eliminarBeneficiario.setWidth("1000px");

        cuentaE = new ComboBox<>("Cuentas");
        cuentaE.setItems(Cuentas);

        beneficiariosE = new ComboBox<>("Beneficiarios");
        beneficiariosE.setIcon(VaadinIcons.GROUP);
        beneficiariosE.setItems(bene);
        beneficiariosE.setValue("Sin selección");

        Button eliminar = new Button("ELIMINAR");
        eliminar.setIcon(VaadinIcons.CLOSE_CIRCLE);
        eliminar.addStyleName("primary");
        eliminar.addClickListener(this::EliminarBeneficiario);

        Button seleccionarE = new Button("Seleccionar");
        seleccionarE.setIcon(VaadinIcons.CLOSE_CIRCLE);
        seleccionarE.addStyleName("primary");
        seleccionarE.addClickListener(this::SeleccionarCuenta);

        Label elim = new Label("Seleccione el beneficiario que desea eliminar");
        elim.setSizeFull();

        eliminarBeneficiario.addComponent(elim, "top: 200px; left: 120px");
        eliminarBeneficiario.addComponent(beneficiariosE, "top: 100px; left: 100px");
        eliminarBeneficiario.addComponent(eliminar, "top: 300px; left: 120px");
        eliminarBeneficiario.addComponent(cuentaE, "top: 25px; left: 120px");
        eliminarBeneficiario.addComponent(seleccionarE, "top: 25px; left: 400px");



        //Principal

        principal.addTab(beneficiarios, "BENEFICIARIOS");

        beneficiarios.addTab(agregarBeneficiario, "AGREGAR BENEFICIARIO");
        beneficiarios.addTab(actualizarBeneficiario, "ACTUALIZAR BENEFICIARIO");
        beneficiarios.addTab(eliminarBeneficiario, "ELIMINAR BENEFICIARIO");

        Button devolverse = new Button("ATRÁS");
        devolverse.setIcon(VaadinIcons.BACKSPACE_A);
        devolverse.setStyleName("primary");
//        devolverse.addClickListener(this::atras);

        AbsoluteLayout estadosCuenta = new AbsoluteLayout();
        estadosCuenta.setWidth("1000px");
        estadosCuenta.setHeight("500px");
        Grid<Integer> grid = new Grid<>();

        grid.addColumn(i -> i).setCaption("#");
        grid.addColumn(i -> "1");
        grid.setItems(IntStream.range(1, 21).boxed());

//        Grid<EstadoCuenta> estados = new Grid<>(EstadoCuenta.class);
//        estados.setWidth("700px");


//        estados.addColumn("#");
//        estados.addColumn("Estado Cuenta");
//        estados.addColumn("Fecha");
        estadosCuenta.addComponent(grid, "top: 100px; left: 100");


        principal.addTab(estadosCuenta, "ESTADOS DE CUENTA");

        lay.addComponents(principal, devolverse);
        addComponents(lay);
        setComponentAlignment(lay, Alignment.MIDDLE_CENTER);


    }


//
//    public void atras(Button.ClickEvent event){
//        loginP.setVisible(true);
//        banco().setVisible(false);
//
//    }
//    public AbsoluteLayout estadosCuenta() {
//
//        return estadosCuenta;
//
//    }

    public void ActualizarBenef(Button.ClickEvent event){

        int ced = Integer.parseInt(cedulaA.getValue());
        int porce = Integer.parseInt(porc.getValue());
        System.out.println(parentezcoA.getSelectedItem().get()+"mcago");
        String parentezco = parentezcoA.getSelectedItem().get();
        System.out.println(cuentas.getSelectedItem().get()+"mecago");
        int cuenta = Integer.parseInt(cuentas.getSelectedItem().get());
        System.out.println(ced);
        System.out.println(porce);
        System.out.println(parentezco);
        System.out.println(cuenta);
        beneficiario.insertaBeneficiarios(ControllerConexion.getInstance().connection, ced, cuenta,parentezco,porce);
    }



    public void SeleccionarCuenta(Button.ClickEvent event){
        Cuentas = beneficiario.getCedulasBeneficiarios(ControllerConexion.getInstance().connection, Integer.parseInt(cuentaE.getSelectedItem().get()));
        beneficiariosE.setItems(Cuentas);
    }

    public void EliminarBeneficiario(Button.ClickEvent event){

        int ced = Integer.parseInt(beneficiariosE.getSelectedItem().get());
        beneficiario.eliminarBeneficiario(ControllerConexion.getInstance().connection, ced);
    }

    public void SeleccionarInfo(Button.ClickEvent event){

        Beneficiario ben = new Beneficiario();
        ben = ben.getBeneficiarios(ControllerConexion.getInstance().connection,Integer.parseInt(BeneDoc.getValue()));
        nombre.setValue(ben.nombre);
        cedula.setValue(String.valueOf(ben.valorDocIdent));
        //tipoDoc.setValue(ben.tipoDocIdent);
        //parentezco.setValue(ben.getParentesco());
        porcentaje.setValue(String.valueOf(ben.getPorcentaje()));
        email.setValue(ben.email);
        tel1.setValue(String.valueOf(ben.tel1));
        tel2.setValue(String.valueOf(ben.tel2));

    }

    public void Actualiza(Button.ClickEvent event){
        System.out.println(Integer.parseInt(BeneDoc.getValue()));
        System.out.println(Integer.parseInt(cedula.getValue()));
        System.out.println(parentezco.getSelectedItem().get());
        System.out.println(Integer.parseInt(porcentaje.getValue()));
        System.out.println( nombre.getValue());
        System.out.println(fechaNac.getValue());
        System.out.println(Integer.parseInt(tel1.getValue()));
        System.out.println(Integer.parseInt(tel2.getValue()));
        System.out.println(tipoDoc.getSelectedItem().get());
        System.out.println(email.getValue());
        beneficiario.modificaPersonas(ControllerConexion.getInstance().connection, Integer.parseInt(BeneDoc.getValue()), Integer.parseInt(cedula.getValue()),parentezco.getSelectedItem().get(), Integer.parseInt(porcentaje.getValue()), nombre.getValue(),fechaNac.getValue(),Integer.parseInt(tel1.getValue()),Integer.parseInt(tel2.getValue()),tipoDoc.getSelectedItem().get(),email.getValue() );

    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

}
