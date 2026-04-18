package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.servicio.CuentaServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@CrossOrigin(origins = "*")
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;

    public CuentaControlador(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    // ✅ Crear cuenta
    @PostMapping
    public Cuenta crearCuenta(@RequestParam Long idCliente,
                              @RequestParam Long idTipo,
                              @RequestParam Long idEstado) {
        return cuentaServicio.crearCuenta(idCliente, idTipo, idEstado);
    }

    // ✅ Obtener cuentas de un cliente
    @GetMapping("/cliente/{idCliente}")
    public List<Cuenta> obtenerCuentasPorCliente(@PathVariable Long idCliente) {
        return cuentaServicio.obtenerCuentasPorCliente(idCliente);
    }

    // ✅ Buscar por número de cuenta
    @GetMapping("/{numeroCuenta}")
    public Cuenta buscarCuenta(@PathVariable String numeroCuenta) {
        return cuentaServicio.buscarPorNumeroCuenta(numeroCuenta);
    }
}