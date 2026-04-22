package gm.SistemaBancario.controlador;

import gm.SistemaBancario.dto.TransferenciaDTO;
import gm.SistemaBancario.modelo.Transferencia;
import gm.SistemaBancario.servicio.TransferenciaServicio;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    // Realizar transferencia
    @PostMapping
    public Transferencia realizarTransferencia(@RequestBody TransferenciaDTO dto) {

        return transferenciaServicio.realizarTransferencia(
                dto.getCuentaOrigen(),
                dto.getCuentaDestino(),
                dto.getMonto(),
                dto.getMotivo()
        );

    }




    // Historial de una cuenta
    @GetMapping("/cuenta/{idCuenta}")
    public List<Transferencia> historial(@PathVariable Long idCuenta) {
        return transferenciaServicio.historialCuenta(idCuenta);
    }
}