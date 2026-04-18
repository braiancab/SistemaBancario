package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.MotivoTransferencia;
import gm.SistemaBancario.servicio.MotivoTransferenciaServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motivo_transferencia")
@CrossOrigin(origins = "*")
public class MotivoTransferenciaControlador {

    private final MotivoTransferenciaServicio motivoServicio;

    public MotivoTransferenciaControlador(MotivoTransferenciaServicio motivoServicio) {
        this.motivoServicio = motivoServicio;
    }

    @GetMapping
    public List<MotivoTransferencia> listar() {
        return motivoServicio.listar();
    }

    @GetMapping("/{id}")
    public MotivoTransferencia buscar(@PathVariable Long id) {
        return motivoServicio.buscarPorId(id).orElseThrow();
    }

    @PostMapping
    public MotivoTransferencia guardar(@RequestBody MotivoTransferencia motivo) {
        return motivoServicio.guardar(motivo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        motivoServicio.eliminar(id);
    }
}
