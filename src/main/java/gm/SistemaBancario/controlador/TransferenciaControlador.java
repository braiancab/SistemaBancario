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
    @PostMapping("/transferir")
    public ResponseEntity<TransferenciaDTO> transferir(@RequestBody TransferenciaRequest request) {

        TransferenciaDTO dto = servicio.realizarTransferencia(
                request.getCuentaOrigen(),
                request.getCuentaDestino(),
                request.getMonto(),
                request.getMotivo()
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/comprobante")
    public ResponseEntity<byte[]> descargarPDF() {

        TransferenciaDTO dto = servicio.obtenerUltimaTransferencia(); // o por ID

        byte[] pdf = pdfService.generarPDF(dto);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comprobante.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // Historial de una cuenta
    @GetMapping("/cuenta/{idCuenta}")
    public List<Transferencia> historial(@PathVariable Long idCuenta) {
        return transferenciaServicio.historialCuenta(idCuenta);
    }
}