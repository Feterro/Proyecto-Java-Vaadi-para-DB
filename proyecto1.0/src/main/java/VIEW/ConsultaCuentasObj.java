package VIEW;

import CONTROLLER.ControllerUI;
import MODEL.CuentaObjetivo;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class ConsultaCuentasObj extends AbsoluteLayout implements View {

    private ControllerUI controller = ControllerUI.getInstance();

    public ConsultaCuentasObj() throws ParseException {
        ventana();
    }

    public void ventana() throws ParseException {

        HorizontalLayout fondo = new HorizontalLayout();
        fondo.setSizeFull();

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondo.png"));
        Image fondoTotal = new Image("", resource);
        fondoTotal.setSizeFull();

        FileResource resource1 = new FileResource(new File("src/main/java/VIEW/Imagenes/fondoConsCO.png"));
        Image fondoConsu = new Image("", resource1);
        fondoConsu.setWidth("1500px");
        fondoConsu.setHeight("700px");

        AbsoluteLayout consulta = new AbsoluteLayout();
        consulta.setWidth("1500px");
        consulta.setHeight("700px");

        Label label = new Label("CUENTAS OBJETIVO");
        label.addStyleName(ValoTheme.LABEL_H2);

        Label detalles = new Label("Cuentas objetivo que en algún mes");
        detalles.addStyleName(ValoTheme.LABEL_H4);
        Label detalles2 = new Label("no pudieron tener depósitos");
        detalles2.addStyleName(ValoTheme.LABEL_H4);


        Grid<CuentaObjetivo> cuentasMalDeposito = new Grid<>(CuentaObjetivo.class);
        cuentasMalDeposito.removeColumn("intereses");
        cuentasMalDeposito.removeColumn("numCuentaAsociada");
        cuentasMalDeposito.setHeight("342px");
        cuentasMalDeposito.setWidth("1000px");
        cuentasMalDeposito.setColumnOrder("numCuenta", "objetivo", "cuota", "saldo", "fechaInicio", "fechaFinal");
        cuentasMalDeposito.getColumn("numCuenta").setCaption("# CUENTA");
        cuentasMalDeposito.getColumn("objetivo").setCaption("OBJETIVO");
        cuentasMalDeposito.getColumn("saldo").setCaption("SALDO");
        cuentasMalDeposito.getColumn("cuota").setCaption("CUOTA");
        cuentasMalDeposito.getColumn("fechaInicio").setCaption("FECHA INICIO");
        cuentasMalDeposito.getColumn("fechaFinal").setCaption("FECHA FINAL");

        ArrayList<CuentaObjetivo> cuentas = prueba();
        cuentasMalDeposito.setItems(cuentas);

        ComboBox<String> nums = new ComboBox<>("NUMEROS CUENTA");
        ArrayList<String> cuentasNum = new ArrayList<>();
        for(CuentaObjetivo cuen: cuentas){
            cuentasNum.add(cuen.getNumCuenta());
        }
        nums.setItems(cuentasNum);
        nums.setWidth("250px");
        nums.setIcon(VaadinIcons.DOLLAR);
        nums.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);

        Button ir = new Button("DETALLES");
        ir.setWidth("250px");
        ir.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ir.setIcon(VaadinIcons.CLIPBOARD_TEXT);
        ir.addClickListener(e-> {
            if(nums.getSelectedItem().isPresent()) {
                controller.setNumCuentaOb(nums.getSelectedItem().get());
                Navigator navigator = new Navigator(UI.getCurrent(), this);
                navigator.addView("Detalles", detallesCuentaObjetivo.class);
                navigator.navigateTo("Detalles");
            }
            else
                Notification.show("NO SELECCIONADO", "Seleccione una cuenta para ver los detalles", Notification.Type.WARNING_MESSAGE);
        });

        TextField filtro = new TextField();
        filtro.setPlaceholder("BUSCAR POR NUMERO CUENTA");
        filtro.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
        filtro.setWidth("300px");
        filtro.setValueChangeMode(ValueChangeMode.LAZY);
        filtro.addValueChangeListener(e-> {
            if(!filtro.isEmpty()) {
                //agregar las cuentas que coicidan con el numero
                Notification.show("No está vacío");
            }
            if (filtro.isEmpty()){
                //agregar todas
                Notification.show("Está vacío");
            }

        });

        Button atras = new Button("");
        atras.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        atras.setIcon(VaadinIcons.ARROW_BACKWARD);
        atras.setWidth("50px");
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(e->{
            Navigator navigator = new Navigator(UI.getCurrent(), this);
            navigator.addView("Consultas", Admistrador.class);
            navigator.navigateTo("Consultas");
        });

        consulta.addComponent(cuentasMalDeposito, "top: 250px; left: 350px");
        consulta.addComponent(fondoConsu);
        consulta.addComponent(label, "top: 25px; left: 625px");
        consulta.addComponent(detalles, "top: 75px; left: 600px");
        consulta.addComponent(detalles2, "top: 100px; left: 625px");
        consulta.addComponent(filtro, "top: 200px; left: 350px");
        consulta.addComponent(nums, "top:250px; left: 50px");
        consulta.addComponent(ir, "top: 300px; left: 50px");
        consulta.addComponent(atras, "top: 600px; right: 50px");

        fondo.addComponent(consulta);
        fondo.setComponentAlignment(consulta, Alignment.MIDDLE_CENTER);

        addComponent(fondoTotal);
        addComponent(fondo);

    }

    private ArrayList<CuentaObjetivo> prueba() throws ParseException {
        ArrayList<CuentaObjetivo> cuentas = new ArrayList<>();
        ArrayList<String> objetivos = new ArrayList<>(Arrays.asList("Comprar casa", "Carro nuevo", "Estudios", "Paseos"));
        Random random = new Random();

        String fec = "20/10/2020";
        String fec1 = "04/01/2010";
        String fec2 = "05/11/2005";
        String fec3 = "20/3/2001";
        String fec4 = "15/12/1995";
        String fec5 = "17/02/2015";
        String fec6 = "06/05/2008";
        String fec7 = "20/08/1985";
        String fec8 = "31/12/2018";
        String fec9 = "07/03/2021";
        String fec10 = "25/09/2019";
        String fec11 = "23/06/1985";
        String fec12 = "31/10/2004";
        String fec13 = "07/01/2020";
        String fec14 = "28/05/2017";
        String fec15 = "14/01/2003";
        String fec16 = "20/05/2014";
        String fec17 = "14/06/2006";
        String fec18 = "20/02/1965";
        String fec19 = "19/07/2020";

        Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fec);
        Date fecha1 = new SimpleDateFormat("dd/MM/yyyy").parse(fec1);
        Date fecha2 = new SimpleDateFormat("dd/MM/yyyy").parse(fec2);
        Date fecha3 = new SimpleDateFormat("dd/MM/yyyy").parse(fec3);
        Date fecha4 = new SimpleDateFormat("dd/MM/yyyy").parse(fec4);
        Date fecha5 = new SimpleDateFormat("dd/MM/yyyy").parse(fec5);
        Date fecha6 = new SimpleDateFormat("dd/MM/yyyy").parse(fec6);
        Date fecha7 = new SimpleDateFormat("dd/MM/yyyy").parse(fec7);
        Date fecha8 = new SimpleDateFormat("dd/MM/yyyy").parse(fec8);
        Date fecha9 = new SimpleDateFormat("dd/MM/yyyy").parse(fec9);
        Date fecha10 = new SimpleDateFormat("dd/MM/yyyy").parse(fec10);
        Date fecha11 = new SimpleDateFormat("dd/MM/yyyy").parse(fec11);
        Date fecha12 = new SimpleDateFormat("dd/MM/yyyy").parse(fec12);
        Date fecha13 = new SimpleDateFormat("dd/MM/yyyy").parse(fec13);
        Date fecha14 = new SimpleDateFormat("dd/MM/yyyy").parse(fec14);
        Date fecha15 = new SimpleDateFormat("dd/MM/yyyy").parse(fec15);
        Date fecha16 = new SimpleDateFormat("dd/MM/yyyy").parse(fec16);
        Date fecha17 = new SimpleDateFormat("dd/MM/yyyy").parse(fec17);
        Date fecha18 = new SimpleDateFormat("dd/MM/yyyy").parse(fec18);
        Date fecha19 = new SimpleDateFormat("dd/MM/yyyy").parse(fec19);

        ArrayList<Date> fechasInicio = new ArrayList<>(Arrays.asList(fecha, fecha1, fecha2, fecha3, fecha5, fecha6, fecha7, fecha8, fecha9));
        ArrayList<Date> fechasFinal = new ArrayList<>(Arrays.asList(fecha10, fecha11, fecha12, fecha13, fecha14, fecha15, fecha16, fecha17, fecha18, fecha19));



        for(int i = 0; i<10; i++){
            CuentaObjetivo cuentaObjetivo = new CuentaObjetivo();
            cuentaObjetivo.setObjetivo(objetivos.get(random.nextInt(objetivos.size()-1)));
            cuentaObjetivo.setFechaInicio(fechasInicio.get(random.nextInt(fechasInicio.size()-1)));
            cuentaObjetivo.setFechaFinal(fechasFinal.get(random.nextInt(fechasFinal.size()-1)));
            cuentaObjetivo.setSaldo(random.nextInt(1000000000));
            cuentaObjetivo.setCuota(random.nextInt(100000));
            cuentaObjetivo.setNumCuenta(String.valueOf(i));
            cuentaObjetivo.setNumCuentaAsociada(i+(i+1));
            cuentas.add(cuentaObjetivo);
        }
        return cuentas;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
