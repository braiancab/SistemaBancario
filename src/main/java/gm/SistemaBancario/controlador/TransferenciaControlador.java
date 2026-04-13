package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.Transferencia;
import gm.SistemaBancario.servicio.TransferenciaServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transferencias")
@CrossOrigin(origins = "*")
public class TransferenciaControlador {

    private final TransferenciaServicio transferenciaServicio;

    public TransferenciaControlador(TransferenciaServicio transferenciaServicio) {
        this.transferenciaServicio = transferenciaServicio;
    }

    // 💥 Realizar transferencia
    @PostMapping
    public Transferencia realizarTransferencia(
            @RequestParam String cuentaOrigen,
            @RequestParam String cuentaDestino,
            @RequestParam Float monto) {

        return transferenciaServicio.realizarTransferencia(
                cuentaOrigen,
                cuentaDestino,
                monto
        );
    }

    // 📊 Historial de una cuenta
    @GetMapping("/cuenta/{idCuenta}")
    public List<Transferencia> historial(@PathVariable Long idCuenta) {
        return transferenciaServicio.historialCuenta(idCuenta);
    }
}