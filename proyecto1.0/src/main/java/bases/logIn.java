package bases;

import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class logIn {
    private String cont;
    private String nombreUsu;
    TextField usuario;
    PasswordField contrasenna;

    public logIn(){

    }
    public void inicioSesion(VerticalLayout contenedor){
        usuario = new TextField("Usuario");
        contrasenna = new PasswordField("Contrasenna");
        
        Button entrar = new Button("Ingresar");
        contenedor.addComponents(usuario, contrasenna, entrar);

    }




}
