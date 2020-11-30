package MODEL;


import CONTROLLER.ControllerBeneficiario;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.util.ArrayList;

public class BeneficiariosTabla {

    private Beneficiario beneficiario = new Beneficiario();

    private String nombre;
    private String documentoIdentidad;
    private float porcentaje;

    public BeneficiariosTabla() {
    }

    public BeneficiariosTabla(String nombre, String documentoIdentidad, float porcentaje) {
        this.nombre = nombre;
        this.documentoIdentidad = documentoIdentidad;
        this.porcentaje = porcentaje;
    }

    public String getNombre() {
        return nombre;
    }

    public String getdocumentoIdentidad() {
        return documentoIdentidad;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setdocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }



}
