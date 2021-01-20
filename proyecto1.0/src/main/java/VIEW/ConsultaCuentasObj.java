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
        cuentasMalDeposito.setItems(controller.llenarTablaCoAdm());

        ComboBox<String> nums = new ComboBox<>("NUMEROS CUENTA");
        nums.setItems(controller.getNumsCuenta());
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
               cuentasMalDeposito.setItems(controller.filtro(filtro.getValue()));
            }
            if (filtro.isEmpty()){
                cuentasMalDeposito.setItems(controller.getCuentas());
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


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
