package VIEW;

import CONTROLLER.ControllerUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;

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
        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/fondo.png"));
        Image fondoTotalBanco = new Image("", resource);
        fondoTotalBanco.setSizeFull();

        FileResource resource1 = new FileResource(new File("src/main/java/VIEW/Imagenes/menu.png"));
        Image menuI = new Image("", resource1);
        menuI.setWidth("1500px");
        menuI.setHeight("50px");

        HorizontalLayout contenedor = new HorizontalLayout();
        contenedor.setSizeFull();

        AbsoluteLayout contenedorTabsBanco = new AbsoluteLayout();
        contenedorTabsBanco.setWidth("1500px");
        contenedorTabsBanco.setHeight("800px");
        contenedorTabsBanco.setStyleName(ValoTheme.PANEL_BORDERLESS);

        TabSheet menu = Menu();

        Button atras = new Button("");
        atras.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        atras.setIcon(VaadinIcons.ARROW_BACKWARD);
        atras.setWidth("50px");
        atras.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        atras.addClickListener(e->{
            Navigator navigator = new Navigator(UI.getCurrent(), this);
            navigator.addView("Inicio", Inicio.class);
            navigator.navigateTo("Inicio");
        });

        contenedorTabsBanco.addComponent(menuI);
        contenedorTabsBanco.addComponent(menu);
        contenedorTabsBanco.addComponent(atras, "top: 650px; right: 50px");
        contenedor.addComponent(contenedorTabsBanco);
        contenedor.setComponentAlignment(contenedorTabsBanco, Alignment.MIDDLE_CENTER);

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
