
package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.OrdenExtraccion;
import gm.SistemaBancario.servicio.OrdenExtraccionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes_extraccion")
@CrossOrigin(origins = "*")

public class OrdenExtraccionControlador {

    private final OrdenExtraccionServicio ordenServicio;


    public OrdenExtraccionControlador(OrdenExtraccionServicio ordenServicio) {
        this.ordenServicio = ordenServicio;
    }

    // Listar todas las órdenes
    @GetMapping
    public List<OrdenExtraccion> listar() {
        return ordenServicio.listarTodas();
    }

    // Buscar una orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrdenExtraccion> obtenerPorId(@PathVariable Long id) {
        return ordenServicio.buscarPorId(id)
                .map(orden -> new ResponseEntity<>(orden, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear una nueva orden
    @PostMapping
    public ResponseEntity<OrdenExtraccion> crear(@RequestBody OrdenExtraccion orden) {
        try {
            OrdenExtraccion nuevaOrden = ordenServicio.guardar(orden);
            return new ResponseEntity<>(nuevaOrden, HttpStatus.CREATED);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar una orden
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        try {
            ordenServicio.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar por código (ejemplo: /api/ordenes-extraccion/buscar?codigo=ABC123)
    @GetMapping("/buscar")
    public ResponseEntity<List<OrdenExtraccion>> buscarPorCodigo(@RequestParam String codigo) {
        return new ResponseEntity<>(ordenServicio.buscarPorCodigo(codigo), HttpStatus.OK);
    }
}