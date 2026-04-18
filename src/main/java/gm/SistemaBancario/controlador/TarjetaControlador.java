package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.Tarjeta;
import gm.SistemaBancario.servicio.TarjetaServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarjetas")
@CrossOrigin(origins = "*")
public class TarjetaControlador {

    private final TarjetaServicio tarjetaServicio;

    public TarjetaControlador(TarjetaServicio tarjetaServicio) {
        this.tarjetaServicio = tarjetaServicio;
    }

    @GetMapping
    public List<Tarjeta> listar() {
        return tarjetaServicio.listarTarjetas();
    }

    @GetMapping("/{id}")
    public Tarjeta buscar(@PathVariable Long id) {
        return tarjetaServicio.buscarPorId(id).orElseThrow();
    }

    @GetMapping("/cuenta/{idCuenta}")
    public List<Tarjeta> listarPorCuenta(@PathVariable Long idCuenta) {
        return tarjetaServicio.listarPorCuenta(idCuenta);
    }

    @PostMapping
    public Tarjeta guardar(@RequestBody Tarjeta tarjeta) {
        return tarjetaServicio.guardarTarjeta(tarjeta);
    }

    @PutMapping("/{id}")
    public Tarjeta actualizar(@PathVariable Long id, @RequestBody Tarjeta tarjeta) {
        tarjeta.setIdTarjeta(id);
        return tarjetaServicio.actualizarTarjeta(tarjeta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tarjetaServicio.eliminarTarjeta(id);
    }
}
