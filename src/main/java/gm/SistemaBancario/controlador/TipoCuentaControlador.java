package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.TipoCuenta;
import gm.SistemaBancario.servicio.TipoCuentaServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo_cuenta")
@CrossOrigin(origins = "*")
public class TipoCuentaControlador {

    private final TipoCuentaServicio tipoCuentaServicio;

    public TipoCuentaControlador(TipoCuentaServicio tipoCuentaServicio) {
        this.tipoCuentaServicio = tipoCuentaServicio;
    }

    @GetMapping
    public List<TipoCuenta> listar() {
        return tipoCuentaServicio.listar();
    }

    @GetMapping("/{id}")
    public TipoCuenta buscar(@PathVariable Long id) {
        return tipoCuentaServicio.buscarPorId(id).orElseThrow();
    }

    @PostMapping
    public TipoCuenta guardar(@RequestBody TipoCuenta tipoCuenta) {
        return tipoCuentaServicio.guardar(tipoCuenta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tipoCuentaServicio.eliminar(id);
    }
}

