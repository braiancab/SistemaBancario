package gm.SistemaBancario.controlador;
import java.util.Map;
import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.servicio.CuentaServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")

public class CuentaControlador {

    private final CuentaServicio cuentaServicio;

    public CuentaControlador(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    //Crear cuenta
    @PostMapping
    public Cuenta crearCuenta(@RequestBody Map<String, Long> datos) {
        // Extraemos los datos directamente del JSON que nos manda React
        Long idCliente = datos.get("idCliente");
        Long idTipo = datos.get("idTipo");
        Long idEstado = datos.get("idEstado");

        // Se los pasamos a tu servicio tal como los espera
        return cuentaServicio.crearCuenta(idCliente, idTipo, idEstado);
    }

    //Obtener cuentas de un cliente
    @GetMapping("/cliente/{idCliente}")
    public List<Cuenta> obtenerCuentasPorCliente(@PathVariable Long idCliente) {
        return cuentaServicio.obtenerCuentasPorCliente(idCliente);
    }

    // Buscar por número de cuenta

    @GetMapping("/numero/{numeroCuenta}")
    public Cuenta buscarCuenta(@PathVariable String numeroCuenta) {
        return cuentaServicio.buscarPorNumeroCuenta(numeroCuenta);
    }

    @GetMapping("/destino/{idCliente}")
    public List<Cuenta> obtenerCuentasDestino(@PathVariable Long idCliente) {
        return cuentaServicio.obtenerCuentasDestino(idCliente);
    }

}