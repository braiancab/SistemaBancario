package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.TipoTarjeta;
import gm.SistemaBancario.servicio.TipoTarjetaServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos_tarjeta")
@CrossOrigin(origins = "*")
public class TipoTarjetaControlador {

    private final TipoTarjetaServicio tipoTarjetaServicio;

    public TipoTarjetaControlador(TipoTarjetaServicio tipoTarjetaServicio) {
        this.tipoTarjetaServicio = tipoTarjetaServicio;
    }

    @GetMapping
    public List<TipoTarjeta> listar() {
        return tipoTarjetaServicio.listar();
    }

    @GetMapping("/{id}")
    public TipoTarjeta buscar(@PathVariable Long id) {
        return tipoTarjetaServicio.buscarPorId(id).orElseThrow();
    }

    @PostMapping
    public TipoTarjeta guardar(@RequestBody TipoTarjeta tipoTarjeta) {
        return tipoTarjetaServicio.guardar(tipoTarjeta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tipoTarjetaServicio.eliminar(id);
    }
}

