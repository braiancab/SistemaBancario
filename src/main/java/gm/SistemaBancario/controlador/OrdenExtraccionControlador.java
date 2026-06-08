package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.OrdenExtraccion;
import gm.SistemaBancario.servicio.OrdenExtraccionServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes_extraccion")
@CrossOrigin(origins = "*")
public class OrdenExtraccionControlador {

    private final OrdenExtraccionServicio ordenServicio;

    public OrdenExtraccionControlador(OrdenExtraccionServicio ordenServicio) {
        this.ordenServicio = ordenServicio;
    }

    @GetMapping
    public List<OrdenExtraccion> listar() {
        return ordenServicio.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenExtraccion> obtenerPorId(@PathVariable Long id) {
        return ordenServicio.buscarPorId(id)
                .map(orden -> new ResponseEntity<>(orden, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public OrdenExtraccion crearOrdenExtraccion(@RequestBody OrdenExtraccion orden) {
        return ordenServicio.crearOrdenExtraccion(orden);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        try {
            ordenServicio.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<OrdenExtraccion>> buscarPorCodigo(@RequestParam String codigo) {
        return new ResponseEntity<>(ordenServicio.buscarPorCodigo(codigo), HttpStatus.OK);
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
}