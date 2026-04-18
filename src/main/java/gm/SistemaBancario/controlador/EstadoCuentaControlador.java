
package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.EstadoCuenta;
import gm.SistemaBancario.servicio.EstadoCuentaServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados_cuenta")
@CrossOrigin(origins = "*")
public class EstadoCuentaControlador {

    private final EstadoCuentaServicio estadoCuentaServicio;

    public EstadoCuentaControlador(EstadoCuentaServicio estadoCuentaServicio) {
        this.estadoCuentaServicio = estadoCuentaServicio;
    }

    @GetMapping
    public List<EstadoCuenta> listar() {
        return estadoCuentaServicio.listar();
    }

    @GetMapping("/{id}")
    public EstadoCuenta buscar(@PathVariable Long id) {
        return estadoCuentaServicio.buscarPorId(id).orElseThrow();
    }

    @PostMapping
    public EstadoCuenta guardar(@RequestBody EstadoCuenta estadoCuenta) {
        return estadoCuentaServicio.guardar(estadoCuenta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        estadoCuentaServicio.eliminar(id);
    }
}
