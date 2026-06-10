package gm.SistemaBancario.controlador;

import gm.SistemaBancario.dto.TransferenciaDTO;
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


    //Historial de transferencias enviadas
    //No utilizar
    @GetMapping("/enviadas/{idCuenta}")
    public List<Transferencia> obtenerEnviadas(@PathVariable Long idCuenta) {
        return transferenciaServicio.obtenerEnviadas(idCuenta);
    }

    //Historial de transferencias recibidas
    //No utilizar
    @GetMapping("/recibidas/{idCuenta}")
    public List<Transferencia> obtenerRecibidas(@PathVariable Long idCuenta) {
        return transferenciaServicio.obtenerRecibidas(idCuenta);
    }


}