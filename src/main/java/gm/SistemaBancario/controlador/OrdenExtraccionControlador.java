package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.OrdenExtraccion;
import gm.SistemaBancario.servicio.OrdenExtraccionServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gm.SistemaBancario.repositorio.OrdenExtraccionRepositorio;
import java.util.List;

@RestController
@RequestMapping("/api/ordenes_extraccion")
@CrossOrigin(origins = "*")
public class OrdenExtraccionControlador {

    private final OrdenExtraccionServicio ordenServicio;
    private final OrdenExtraccionRepositorio ordenExtraccionRepositorio;

    public OrdenExtraccionControlador(OrdenExtraccionServicio ordenServicio,
    OrdenExtraccionRepositorio ordenExtraccionRepositorio) {
        this.ordenServicio = ordenServicio;
        this.ordenExtraccionRepositorio = ordenExtraccionRepositorio;
    }


    @PostMapping
    public OrdenExtraccion crearOrdenExtraccion(@RequestBody OrdenExtraccion orden) {
        return ordenServicio.crearOrdenExtraccion(orden);
    }


    // mapea el idCliente
    @GetMapping("/historial/cliente/{idCliente}")
    public ResponseEntity<List<OrdenExtraccion>> obtenerHistorialPorCliente(@PathVariable Long idCliente) {
        List<OrdenExtraccion> historial = ordenServicio.historialOrdenExtraccion(idCliente);

        if (historial.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content si no tiene extracciones
        }
        return new ResponseEntity<>(historial, HttpStatus.OK); // 200 OK con la lista
    }

    @GetMapping("/historial/cuenta/{idCuenta}")
    public List<OrdenExtraccion> historialPorCuenta(
            @PathVariable Long idCuenta) {

        return ordenExtraccionRepositorio
                .findByCuentaOrigen_IdCuenta(idCuenta);
    }
}