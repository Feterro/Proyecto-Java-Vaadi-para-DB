package VIEW;

import CONTROLLER.ControllerUI;
import MODEL.EstadoCuenta;
import MODEL.Movimiento;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class EstadCuentaV  {

    private static EstadCuentaV estadCuentaV;

    private ControllerUI controller = ControllerUI.getInstance();

    private Grid<EstadoCuenta> estados;
    private RadioButtonGroup<Integer> ids;
    private Label cuenta;

    private int cantEstados;
    private int estadoActual;
    private ArrayList<EstadoCuenta> estadoCuentas;

    public EstadCuentaV() {}

    public static EstadCuentaV getInstance(){
        if(estadCuentaV == null){
            estadCuentaV = new EstadCuentaV();
        }
        return estadCuentaV;
    }

    public Label getCuenta() {
        return cuenta;
    }

    public RadioButtonGroup<Integer> getIds() {
        return ids;
    }

    public void setIds(RadioButtonGroup<Integer> ids) {
        this.ids = ids;
    }

    public Grid<EstadoCuenta> getEstados() {
        return estados;
    }

    public void setEstados(Grid<EstadoCuenta> estados) {
        this.estados = estados;
    }

    public ArrayList<EstadoCuenta> getEstadoCuentas() {
        return estadoCuentas;
    }

    public void setCantEstados(int cantEstados) {
        this.cantEstados = cantEstados;
    }

    public int getCantEstados() {
        return cantEstados;
    }

    public int getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(int estadoActual) {
        this.estadoActual = estadoActual;
    }

    public void setEstadoCuentas(ArrayList<EstadoCuenta> estadoCuentas) {
        this.estadoCuentas = estadoCuentas;
    }

    public void EstadoCuenta() {

        AbsoluteLayout estadosCuentaContenedor = new AbsoluteLayout();
        estadosCuentaContenedor.setWidth("1500px");
        estadosCuentaContenedor.setHeight("700px");

        AbsoluteLayout estadoCuenta = new AbsoluteLayout();
        estadosCuentaContenedor.setWidth("1500px");
        estadosCuentaContenedor.setHeight("700px");

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/EstadosCuenta.png"));
        Image fondoEstadoCuenta = new Image("", resource);
        fondoEstadoCuenta.setWidth("1500px");
        fondoEstadoCuenta.setHeight("700px");


        Label datos = new Label("ESTADOS MÁS RECIENTES");
        datos.addStyleName(ValoTheme.LABEL_H2);

        estados = new Grid<>(EstadoCuenta.class);
        estados.setWidth("700px");
        estados.setHeight("343px");
        estados.setColumnOrder("numero", "fechaInicio", "fechaFinal", "saldoInicial", "saldoFinal");
        estados.getColumn("numero").setCaption("#");
        estados.getColumn("fechaInicio").setCaption("FECHA INICIO");
        estados.getColumn("fechaFinal").setCaption("FECHA FINAL");
        estados.getColumn("saldoInicial").setCaption("SALDO INCIAL");
        estados.getColumn("saldoFinal").setCaption("SALDO FINAL");

        ArrayList<Integer> idsR = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));

        ids = new RadioButtonGroup<>();
        ids.setItems(idsR);
        ids.addStyleName(ValoTheme.CHECKBOX_LARGE);

        Button selecionar = new Button("VER DETALLES");
        selecionar.setIcon(VaadinIcons.FILE_TEXT);
        selecionar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        selecionar.setWidth("300px");
        selecionar.setHeight("50px");
        selecionar.addClickListener(e->{
            movimientos(estadosCuentaContenedor, estadoCuenta, ids.getSelectedItem().get());
        });

        cuenta = new Label();
        cuenta.addStyleName(ValoTheme.LABEL_H4);
        cuenta.addStyleName(ValoTheme.LABEL_BOLD);


        Button mas = new Button();
        mas.setIcon(VaadinIcons.ANGLE_DOUBLE_RIGHT);
        mas.addStyleName(ValoTheme.BUTTON_PRIMARY);
        mas.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        mas.setWidth("300px");
        mas.setHeight("50px");
        mas.addClickListener(e -> {
            if (cantEstados >= 8) {
                ids.setItems(idsRB(estadoActual, estadoActual + 8));
                estados.setItems(estadoCuentas.subList(estadoActual, estadoActual + 8));
                cantEstados = cantEstados - 8;
                estadoActual = estadoActual + 8;
            } else if (cantEstados != 0) {
                estados.setItems(estadoCuentas.subList(estadoActual, -1));
            } else {
                Notification.show("TOTAL","No hay más estados que mostrar", Notification.Type.TRAY_NOTIFICATION);
            }

        });

        Button atras = new Button();
        atras.setIcon(VaadinIcons.ANGLE_DOUBLE_LEFT);
        atras.addStyleName(ValoTheme.BUTTON_PRIMARY);
        atras.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        atras.setWidth("300px");
        atras.setHeight("50px");
        atras.addClickListener(e -> {
            if (estadoActual >= 8) {
                ids.setItems(idsRB(estadoActual - 8, estadoActual));
                estados.setItems(estadoCuentas.subList(estadoActual - 8, estadoActual));
                estadoActual = estadoActual - 8;
                cantEstados = cantEstados + 8;
            } else {
                Notification.show("TOTAL","No hay estados anteriores", Notification.Type.TRAY_NOTIFICATION);
            }
        });

        estadoCuenta.addComponent(fondoEstadoCuenta);
        estadoCuenta.addComponent(datos, "top: 50px; left: 50px");
        estadoCuenta.addComponent(estados, "top: 150px; left: 400px");
        estadoCuenta.addComponent(ids, "top:180px; left: 1120px");
        estadoCuenta.addComponent(atras, "top: 510; left: 425px");
        estadoCuenta.addComponent(mas, "top: 510px; left: 775px");
        estadoCuenta.addComponent(selecionar, "top: 590; left: 600px");
        estadoCuenta.addComponent(cuenta, "top: 50px; right: 50px");
        estadosCuentaContenedor.addComponent(estadoCuenta);
        controller.getMenu().addTab(estadosCuentaContenedor, "ESTADOS CUENTA");
    }

    public ArrayList<Integer> idsRB(int inicio, int fin) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = inicio; i < fin; i++) {
            ids.add(i + 1);
        }
        return ids;
    }

    public void movimientos(AbsoluteLayout contenedorEstados, AbsoluteLayout estadoCuenta, int id){
        estadoCuenta.setVisible(false);

        Label datos = new Label("DATOS ESTADO CUENTA");
        datos.addStyleName(ValoTheme.LABEL_H2);
        EstadoCuenta estadoActual = estadoCuentas.get(id-1);
        String fechaIn = String.valueOf(estadoActual.getFechaInicio());
        String fechaFin = String.valueOf(estadoActual.getFechaFinal());

        FileResource resource = new FileResource(new File("src/main/java/VIEW/Imagenes/detallesEstadoCuenta.png"));
        Image fondoMovimientos = new Image("", resource);
        fondoMovimientos.setWidth("1500px");
        fondoMovimientos.setHeight("700px");

        Label fechas = new Label("Estado del " + fechaIn + " al " + fechaFin);
        fechas.addStyleName(ValoTheme.LABEL_H3);

        AbsoluteLayout movimientos = new AbsoluteLayout();
        movimientos.setWidth("1500px");
        movimientos.setHeight("700px");

        Grid<Movimiento> movimiento = new Grid<>(Movimiento.class);
        movimiento.setWidth("800px");
        movimiento.setHeight("400px");
        movimiento.setColumnOrder("fechaMov", "tipo", "descripcion", "monto");
        movimiento.getColumn("fechaMov").setCaption("FECHA MOVIMIENTO");
        movimiento.getColumn("tipo").setCaption("TIPO");
        movimiento.getColumn("descripcion").setCaption("DESCRIPCIÓN");
        movimiento.getColumn("monto").setCaption("MONTO $");
        ArrayList<Movimiento> movimientosDetalles = controller.getMovimientos(fechaIn, fechaFin);
        movimiento.setItems(movimientosDetalles);


        Label buscar = new Label();
        buscar.setIcon(VaadinIcons.SEARCH);

        TextField filter = new TextField();
        filter.setPlaceholder("FILTRO POR DESCRIPCIÓN");
        filter.setWidth("250px");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e-> {
            if(!filter.isEmpty()) {
                movimiento.setItems(controller.getMovimientos(filter.getValue()));
            }
            if (filter.isEmpty())
                movimiento.setItems(movimientosDetalles);
        });

        Label resumen = new Label("RESUMEN DATOS");
        resumen.addStyleName(ValoTheme.LABEL_BOLD);
        resumen.addStyleName(ValoTheme.LABEL_H2);

        Label saldoInicial = new Label("Saldo inicial: " + estadoActual.getSaldoInicial());
        saldoInicial.addStyleName(ValoTheme.LABEL_H4);
        saldoInicial.addStyleName(ValoTheme.LABEL_BOLD);

        Label saldoFinal = new Label("Saldo Final: " + estadoActual.getFechaFinal());
        saldoFinal.addStyleName(ValoTheme.LABEL_H4);
        saldoFinal.addStyleName(ValoTheme.LABEL_BOLD);

        Label cantOpCajHumano = new Label("Cantidad de operaciones por cajero humano: " + getCantCajeroH(movimientosDetalles));
        cantOpCajHumano.addStyleName(ValoTheme.LABEL_H4);
        cantOpCajHumano.addStyleName(ValoTheme.LABEL_BOLD);

        Label cantOpCajAut = new Label("Cantidad de operaciones por cajero automático: " + getCantCajeroAu(movimientosDetalles));
        cantOpCajAut.addStyleName(ValoTheme.LABEL_H4);
        cantOpCajAut.addStyleName(ValoTheme.LABEL_BOLD);

        Button volver = new Button("ATRÁS");
        volver.setIcon(VaadinIcons.BACKSPACE_A);
        volver.setWidth("200px");
        volver.setHeight("50px");
        volver.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        volver.addClickListener(e -> atras(movimientos, estadoCuenta));


        movimientos.addComponent(fondoMovimientos);
        movimientos.addComponent(saldoInicial, "top: 250px; left: 900px");
        movimientos.addComponent(saldoFinal, "top: 300px; left: 900px");
        movimientos.addComponent(cantOpCajHumano, "top: 350px; left: 900px");
        movimientos.addComponent(cantOpCajAut, "top: 400px; left: 900px");
        movimientos.addComponent(movimiento, "top: 200px; left: 80px");
        movimientos.addComponent(filter, "top: 150px; left: 100px");
        movimientos.addComponent(buscar, "top: 180px; left: 80");
        movimientos.addComponent(datos, "top: 25px; left: 650px");
        movimientos.addComponent(fechas, "top: 75px; left: 625px");
        movimientos.addComponent(volver, "top: 610px; right: 50px");
        movimientos.addComponent(resumen, "top: 200px; left: 900px");
        contenedorEstados.addComponent(movimientos);
    }

    //Métodos interfaz

    private String getCantCajeroAu(ArrayList<Movimiento> movimientosDetalles) {
        int cant = 0;
        for (Movimiento mov: movimientosDetalles){
            if(mov.getTipo().equals("Deposito en ATM") || mov.getTipo().equals("Retiro ATM")){
                cant ++;
            }
        }

        return String.valueOf(cant);

    }

    private String getCantCajeroH(ArrayList<Movimiento> movimientosDetalles) {
        int cant = 0;
         for (Movimiento mov: movimientosDetalles){
            if(mov.getTipo().equals("Deposito Ventana") || mov.getTipo().equals("Retiro Ventana")){
                cant++;
            }
        }

        return String.valueOf(cant);
    }

    public void atras(AbsoluteLayout actual, AbsoluteLayout opciones) {
        actual.setVisible(false);
        opciones.setVisible(true);
    }


}
