package bases;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.tabsheet.TabsheetState;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class logIn extends VerticalLayout implements View {
    TextField usuario;
    PasswordField contrasenna;
    Panel loginP;
    Button entrar;
    String nombreUsuario;
    String contr;
    public logIn(){

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
    public void obtenerDatos(Button.ClickEvent event){
        nombreUsuario = usuario.getValue();
        contr = contrasenna.getValue();
        loginP.setVisible(false);
        banco();
        //if usuario correcto:
            //hacer visible nuevo papel y hacer invisoble panel anterior
        //else:
            //avisar que está mamando
    }

    public void banco(){
        TabSheet principal = new TabSheet();
        Button boton2 = new Button("Boton2");
        ArrayList<String> prueba = new ArrayList<>();
        prueba.add("Parentezco 1");
        prueba.add("Parentezco 2");
        prueba.add("Parentezco 3");


        //Beneficiarios
        Accordion beneficiarios = new Accordion();

        //Actualizar beneficiario

        AbsoluteLayout actualizarBeneficiario = new AbsoluteLayout();
        actualizarBeneficiario.setWidth("1000px");
        actualizarBeneficiario.setHeight("600px");

        Label datosPersonales = new Label("Datos Personales");
        TextField nombre = new TextField("Nombre");
        nombre.setIcon(VaadinIcons.USER_CHECK);
        TextField cedula = new TextField("Documento identidad");
        cedula.setIcon(VaadinIcons.TEXT_LABEL);
        DateField fechaNac = new DateField("Fecha Naacimiento");
        fechaNac.setIcon(VaadinIcons.CALENDAR_USER);

        Label relPorc = new Label("Relación y porcentaje");
        ComboBox parentezco = new ComboBox("Parentezo");
        parentezco.setItems(prueba);

        parentezco.setIcon(VaadinIcons.FAMILY);
        parentezco.setWidth("300px");
        TextField porcentaje = new TextField("Porcentaje");
        porcentaje.setIcon(VaadinIcons.DOLLAR);
        porcentaje.setWidth("300px");

        Label contacto = new Label("Contacto");
        TextField email = new TextField("Email");
        email.setIcon(VaadinIcons.ENVELOPE);
        TextField tel1 = new TextField("Telefono 1");
        tel1.setIcon(VaadinIcons.PHONE);
        TextField tel2 = new TextField("Telefono 2");
        tel2.setIcon(VaadinIcons.PHONE);

        Button actualizar = new Button("ACTUALIZAR");
        actualizar.setIcon(VaadinIcons.UPLOAD);
        actualizar.setStyleName("primary");

        actualizarBeneficiario.addComponent(datosPersonales, "top: 20px; left: 440px");
        actualizarBeneficiario.addComponent(nombre, "top: 100px; left: 100px");
        actualizarBeneficiario.addComponent(cedula, "top: 100px; left: 410px");
        actualizarBeneficiario.addComponent(fechaNac, "top: 100px; right: 100px");

        actualizarBeneficiario.addComponent(relPorc, "top: 200px; left: 430px");
        actualizarBeneficiario.addComponent(parentezco, "top: 280px; left: 175px");
        actualizarBeneficiario.addComponent(porcentaje, "top: 280px; right: 175px");

        actualizarBeneficiario.addComponent(contacto, "top: 380px; left: 470px");
        actualizarBeneficiario.addComponent(email, "top: 460px; left: 100px");
        actualizarBeneficiario.addComponent(tel1, "top: 460px; left: 410px");
        actualizarBeneficiario.addComponent(tel2, "top: 460px; right: 100");

        actualizarBeneficiario.addComponent(actualizar, "top: 550px; left: 430px");

        //Agregar beneficiario
        AbsoluteLayout agregarBeneficiario = new AbsoluteLayout();
        agregarBeneficiario.setWidth("1000px");
        agregarBeneficiario.setHeight("500px");

        TextField cedulaA = new TextField("Documento de identidad");
        cedulaA.setIcon(VaadinIcons.USER_CHECK);
        ComboBox parentezcoA = new ComboBox("Parentezco");
        parentezcoA.setIcon(VaadinIcons.FAMILY);
        parentezco.setItems(prueba);
        TextField porcentajeA = new TextField("Porcentaje");
        porcentajeA.setIcon(VaadinIcons.DOLLAR);

        agregarBeneficiario.addComponent(cedulaA, "top: 100px; left: 100px");
        agregarBeneficiario.addComponent(parentezcoA, "top: 200px; left: 100px");
        agregarBeneficiario.addComponent(porcentajeA, "top: 300px; left: 100px");

        //Eliminar Beneficiario
        AbsoluteLayout eliminarBeneficiario = new AbsoluteLayout();
        eliminarBeneficiario.setHeight("500px");
        eliminarBeneficiario.setWidth("1000px");

        ComboBox beneficiariosE = new ComboBox("Beneficiarios");
        beneficiariosE.setIcon(VaadinIcons.GROUP);
        beneficiariosE.setDescription("HOLA");
        beneficiariosE.setItems(prueba);

        eliminarBeneficiario.addComponent(beneficiariosE, "top: 100px; left: 100px");

        //Principal

        principal.addTab(beneficiarios,"BENEFICIARIOS");
        principal.addTab(boton2, "ESTADOS DE CUENTA");
        beneficiarios.addTab(agregarBeneficiario, "AGREGAR BENEFICIARIO");
        beneficiarios.addTab(actualizarBeneficiario, "ACTUALIZAR BENEFICIARIO");
        beneficiarios.addTab(eliminarBeneficiario, "ELIMINAR BENEFICIARIO");


        HorizontalLayout lay = new HorizontalLayout(principal);
        addComponents(lay);
        setComponentAlignment(lay, Alignment.MIDDLE_CENTER);

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
