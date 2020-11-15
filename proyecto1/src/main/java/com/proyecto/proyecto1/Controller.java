package com.proyecto.proyecto1;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/")
    public String prueba(){
        return login();
    }

    public String login(){
       return "<!DOCTYPE HTML>\n" +
               "<html lang=\"es\">\n" +
               "<head>\n" +
               "    <meta charset=\"UTF-8\">\n" +
               "    <title>Iniciar Sesión</title>\n" +
               "    <link rel=\"stylesheet\" href=\"InicioSesion.css\"/>\n" +
               "</head>\n" +
               "<body>\n" +
               "    <h1> Página principal Banco </h1>\n" +
               "    <div id=\"is\">\n" +
               "        <form>\n" +
               "            <label for=\"usuario\">Usuario</label>\n" +
               "            <input type=\"text\" id=\"usuario\"><br><br>\n" +
               "            <label for=\"contrasenna\">Contraseña</label>\n" +
               "            <input type=\"text\" id=\"contrasenna\"><br><br>\n" +
               "            <input type=\"submit\" id = \"iniSe\" value=\"Inciar Sesión\">\n" +
               "        </form>\n" +
               "    </div>" ;
    }
}
