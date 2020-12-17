package VIEW;

import CONTROLLER.ControllerUI;
import MODEL.*;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class BancoV extends AbsoluteLayout implements View {
    private ControllerUI controller = ControllerUI.getInstance();

    private CuentasV cuentasV = CuentasV.getInstance();
    private BeneficiariosV beneficiariosV = BeneficiariosV.getInstance();
    private EstadCuentaV estadCuentaV = EstadCuentaV.getInstance();
    private CuentObjetivoV cuentObjetivoV = CuentObjetivoV.getInstance();

    public BancoV(){
        Banco();
    }

    public void Banco() {
        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondoTotalMenu.png"));
        Image fondoTotalBanco = new Image("", resource);
        fondoTotalBanco.setSizeFull();

        HorizontalLayout contenedor = new HorizontalLayout();
        contenedor.setSizeFull();

        AbsoluteLayout contenedorTabsBanco = new AbsoluteLayout();
        contenedorTabsBanco.setWidth("1500px");
        contenedorTabsBanco.setHeight("800px");
        contenedorTabsBanco.setStyleName(ValoTheme.PANEL_BORDERLESS);

        TabSheet menu = Menu();
        contenedorTabsBanco.addComponent(menu);

        contenedor.addComponent(contenedorTabsBanco);
        contenedor.setComponentAlignment(contenedorTabsBanco, Alignment.BOTTOM_CENTER);

        addComponent(fondoTotalBanco);
        addComponent(contenedor);

    }

    public TabSheet Menu() {
        TabSheet menu = new TabSheet();
        menu.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
        controller.setMenu(menu);
        cuentasV.Cuentas();
        beneficiariosV.Beneficiarios();
        estadCuentaV.EstadoCuenta();
        cuentObjetivoV.CuentasObjetivo();
        menu.getTab(1).setEnabled(false);
        menu.getTab(2).setEnabled(false);
        menu.getTab(3).setEnabled(false);

        return menu;

    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
