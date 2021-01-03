package VIEW;

import MODEL.tablaMultas;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;

public class ConsultaMultasCuenta extends AbsoluteLayout implements View {

    public ConsultaMultasCuenta() {
        ventana();
    }

    private void ventana() {
        HorizontalLayout fondo = new HorizontalLayout();
        fondo.setSizeFull();

        AbsoluteLayout consulta = new AbsoluteLayout();
        consulta.setHeight("700px");
        consulta.setWidth("1500px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondo.png"));
        Image fondoTotal = new Image("", resource);
        fondoTotal.setSizeFull();

        FileResource resource1 = new FileResource(new File("src/main/java/VIEW/Imagenes/fondoConsMulta.png"));
        Image fondoConsu = new Image("", resource1);
        fondoConsu.setWidth("1500px");
        fondoConsu.setHeight("700px");

        Label label = new Label("CUENTAS DE AHORRO");
        label.addStyleName(ValoTheme.LABEL_H2);

        Label detalles = new Label("Cuentas de ahorro que");
        detalles.addStyleName(ValoTheme.LABEL_H4);

        Label detalles2 = new Label("tuvieron multas por exceso de retiros");
        detalles2.addStyleName(ValoTheme.LABEL_H4);

        Label detalles3 = new Label("en cajero automático");
        detalles3.addStyleName(ValoTheme.LABEL_H4);

        Grid<tablaMultas> multas = new Grid<>(tablaMultas.class);
        multas.getColumn("numeroCuenta").setCaption("# CUENTA");
        multas.getColumn("cantPromedioMes").setCaption("# RETIROS PROMEDIO POR MES");
        multas.getColumn("mayorCantRetiros").setCaption("MES CON MÁS RETIROS");
        multas.setHeight("300px");
        multas.setWidth("1000px");
        multas.setColumnOrder("numeroCuenta", "cantPromedioMes", "mayorCantRetiros");

        TextField cantidadDias = new TextField("ÚLTIMOS DÍAS");
        cantidadDias.setPlaceholder("CANTIDAD DE DÍAS");
        cantidadDias.setWidth("200px");
//        cantidadDias.setHeight("50px");
        cantidadDias.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
        cantidadDias.setIcon(VaadinIcons.SEARCH);

        Button buscar = new Button();
        buscar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        buscar.setIcon(VaadinIcons.FOLDER_SEARCH);
        buscar.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
//        buscar.setHeight("50px");

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

        consulta.addComponent(multas, "top: 300px; left: 250px");
        consulta.addComponent(fondoConsu);
        consulta.addComponent(cantidadDias, "top: 240px; left: 250px");
        consulta.addComponent(buscar, "top: 240px; left: 460px");
        consulta.addComponent(label, "top: 25px; left: 625px");
        consulta.addComponent(detalles, "top: 75px; left: 660px");
        consulta.addComponent(detalles2, "top: 90px; left: 610px");
        consulta.addComponent(detalles3, "top: 105px; left: 670px");
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
