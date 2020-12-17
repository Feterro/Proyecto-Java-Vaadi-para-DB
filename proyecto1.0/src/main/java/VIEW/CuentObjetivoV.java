package VIEW;

import CONTROLLER.ControllerUI;
import MODEL.CuentaObjetivo;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class CuentObjetivoV{

    private static CuentObjetivoV cuentObjetivoV;

    private ControllerUI controller = ControllerUI.getInstance();

    private Label cuentaOB;
    private ComboBox<String> numCuentasObjetivo = new ComboBox<>();

    public CuentObjetivoV(){}

    public static CuentObjetivoV getInstance(){
        if(cuentObjetivoV == null){
            cuentObjetivoV = new CuentObjetivoV();
        }
        return cuentObjetivoV;
    }

    public Label getCuentaOB() {
        return cuentaOB;
    }

    public void setCuentaOB(Label cuentaOB) {
        this.cuentaOB = cuentaOB;
    }

    //Principal

    public void CuentasObjetivo() {
        AbsoluteLayout cuentasObjetivoOpciones = new AbsoluteLayout();
        cuentasObjetivoOpciones.setWidth("1500px");
        cuentasObjetivoOpciones.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/CuentaObjetivo.png"));
        Image fondoCuentaObj = new Image("", resource);
        fondoCuentaObj.setWidth("1500px");
        fondoCuentaObj.setHeight("700px");

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

        cuentasObjetivoOpciones.addComponent(fondoCuentaObj);
        cuentasObjetivoOpciones.addComponent(opciones, "top: 50px; left: 100px");
        cuentasObjetivoOpciones.addComponent(ver, "top: 170; left: 100px");
        cuentasObjetivoOpciones.addComponent(crear, "top: 270; left: 100px");
        cuentasObjetivoOpciones.addComponent(modificar, "top: 370; left: 100px");
        cuentasObjetivoOpciones.addComponent(desactivar, "top: 470; left: 100px");
        cuentasObjetivoOpciones.addComponent(cuentaOB, "top: 50; right: 50px");
        cuentasObjetivo.addComponent(cuentasObjetivoOpciones);

        controller.getMenu().addTab(cuentasObjetivo, "CUENTAS OBJETIVO");
    }

    //Ver cuentas objetivo

    public void verCuentaObj(AbsoluteLayout cuentasObjetivoCon, AbsoluteLayout cuentasObjOpciones){
        cuentasObjOpciones.setVisible(false);

        AbsoluteLayout ver = new AbsoluteLayout();
        ver.setWidth("1500px");
        ver.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/CuentaObjetivoVer.png"));
        Image fondoVerCO = new Image("", resource);
        fondoVerCO.setWidth("1500px");
        fondoVerCO.setHeight("700px");

        Label datos = new Label("DETALLES CUENTAS");
        datos.addStyleName(ValoTheme.LABEL_H2);

        Grid<CuentaObjetivo> cuentasObjetivo = new Grid<>(CuentaObjetivo.class);
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

        ver.addComponent(fondoVerCO);
        ver.addComponent(datos, "top: 50px; left: 50px");
        ver.addComponent(cuentasObjetivo, "top: 200px; left: 250px");
        ver.addComponent(atras, "top: 575px; left: 525px");
        ver.addComponent(adelante, "top: 575px; left: 775px");
        ver.addComponent(volver, "top: 610px; right: 50px");

        cuentasObjetivoCon.addComponent(ver);
    }

    //Crear cuenta objetivo

    public void crearCuenta(AbsoluteLayout cuentasObjetivoCon, AbsoluteLayout cuentasObjOpciones){
        cuentasObjOpciones.setVisible(false);

        AbsoluteLayout crear = new AbsoluteLayout();
        crear.setHeight("700px");
        crear.setWidth("1500px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/CuentaObjetivoAgrega.png"));
        Image fondoCrearCO = new Image("", resource);
        fondoCrearCO.setWidth("1500px");
        fondoCrearCO.setHeight("700px");

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
                        if(controller.crearCuentaObj(objetivo.getValue(), fechaIn.getValue().toString(), fechaFin.getValue().toString(), Float.parseFloat(cuota.getValue()))){
                            Notification.show("CREACIÓN","Exitosa", Notification.Type.TRAY_NOTIFICATION);
                            controller.setNumerosCuenta();
                        }
                        else{
                            Notification.show("CREACIÓN","No se pudo crear", Notification.Type.ERROR_MESSAGE);
                        }
                    }
                    else{
                        Notification.show("FECHA INCORRECTA","La fecha de finalización está antes que la fecha de inicio", Notification.Type.WARNING_MESSAGE);
                    }
                }
                else{
                    Notification.show("FECHA INCORRECTA","La fecha de inicio no puede ser una fecha anterior a la fecha de hoy", Notification.Type.WARNING_MESSAGE);
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

        crear.addComponent(fondoCrearCO);
        crear.addComponent(datosObj, "top: 50px; left: 80px");
        crear.addComponent(objetivo, "top: 200px; left: 550px");
        crear.addComponent(fechaIn, "top: 350px; left: 400px");
        crear.addComponent(fechaFin, "top: 350px; left: 750px");
        crear.addComponent(cuota, "top: 500px; left: 550px");
        crear.addComponent(crearB, "top: 600px; left: 550px");
        crear.addComponent(volver, "top: 610px; right: 50px");

        cuentasObjetivoCon.addComponent(crear);
    }

    //Modificar cuenta Objetivo

    public void modificarCuenOb(AbsoluteLayout cuentasObjetivoCon, AbsoluteLayout cuentasObjOpciones){
        cuentasObjOpciones.setVisible(false);

        AbsoluteLayout modificar = new AbsoluteLayout();
        modificar.setWidth("1500px");
        modificar.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/CuentaObjetivoModificar.png"));
        Image fondoModificarCuenta = new Image("", resource);
        fondoModificarCuenta.setWidth("1500px");
        fondoModificarCuenta.setHeight("700px");

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
                        Notification.show("ACTUALIZACIÓN","Exitosa", Notification.Type.TRAY_NOTIFICATION);
                        controller.setNumerosCuenta();
                    }
                    else{
                        Notification.show("ACTUALIZACIÓN","No se pudo", Notification.Type.ERROR_MESSAGE);
                    }
                }
                else
                    Notification.show("FECHA INCORRECTA","La fecha de finalización no puede ser antes que la fecha de inicio", Notification.Type.WARNING_MESSAGE);
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

        modificar.addComponent(fondoModificarCuenta);
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

    //Desactivar cuenta

    public void desactivarCuentaObjetivo(AbsoluteLayout cuentasObjetivoCon, AbsoluteLayout cuentasObjOpciones) {
        cuentasObjOpciones.setVisible(false);

        AbsoluteLayout desactivar = new AbsoluteLayout();
        desactivar.setWidth("1500px");
        desactivar.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/CuentaObjetivoDesactivar.png"));
        Image fondoDesactivarCO = new Image("", resource);
        fondoDesactivarCO.setSizeFull();

        Label desact = new Label("ESCOJA LA CUENTA");
        desact.addStyleName(ValoTheme.LABEL_H2);

        ComboBox<String> cuenDes = new ComboBox<>("Cuentas");
        cuenDes.setWidth("300px");
        cuenDes.setIcon(VaadinIcons.SELECT);
        cuenDes.setPlaceholder("Cuentas");
        cuenDes.setItems(controller.getNumerosCuentaObjetivo());

        Button desactivarB = new Button("DESACTIVAR");
        desactivarB.setIcon(VaadinIcons.FROWN_O);
        desactivarB.addStyleName(ValoTheme.BUTTON_PRIMARY);
        desactivarB.setWidth("300px");
        desactivarB.addClickListener(e-> {
            if(controller.desactivarCuentaObj(cuenDes.getSelectedItem().get())){
                Notification.show("DESACTIVACIÓN","La cuenta fue desactivada exitosamente", Notification.Type.TRAY_NOTIFICATION);
                controller.setNumerosCuenta();
            }
            else{
                Notification.show("DESACTIVACIÓN","No se pudo desactivar la cuenta\nIntente de nuevo", Notification.Type.ERROR_MESSAGE);
            }
            controller.setNumerosCuenta();
            cuenDes.setItems(controller.getNumerosCuentaObjetivo());
        });

        Button volver = new Button("ATRÁS");
        volver.setIcon(VaadinIcons.BACKSPACE_A);
        volver.setWidth("200px");
        volver.setHeight("50px");
        volver.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        volver.addClickListener(e -> atras(desactivar, cuentasObjOpciones));

        desactivar.addComponent(fondoDesactivarCO);
        desactivar.addComponent(desact, "top: 50px; left: 50px");
        desactivar.addComponent(cuenDes, "top: 300px; left: 100px");
        desactivar.addComponent(desactivarB, "top: 400px; left: 100px");
        desactivar.addComponent(volver, "top: 610px; right: 50px");

        cuentasObjetivoCon.addComponent(desactivar);
    }

    //Métodos interfaz

    public void atras(AbsoluteLayout actual, AbsoluteLayout opciones) {
        actual.setVisible(false);
        opciones.setVisible(true);
    }

}
