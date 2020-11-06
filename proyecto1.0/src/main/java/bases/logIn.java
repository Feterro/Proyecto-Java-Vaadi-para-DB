package bases;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.tabsheet.TabsheetState;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

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
        //if usuario correcto:
            //hacer visible nuevo papel y hacer invisoble panel anterior
        //else:
            //avisar que está mamando
    }

    public void banco(){
        Panel banco = new Panel("PÁGINA BANCO");
        TabsheetState menu = new TabsheetState();
        
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
