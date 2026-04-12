package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.Cliente;
import gm.SistemaBancario.servicio.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*") // permite conexión con React
public class ClienteControlador {

    private final ClienteServicio clienteServicio;

    @Autowired
    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    // ✅ LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteServicio.listarClientes());
    }

    // ✅ BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteServicio.buscarClientePorId(id);
        if(cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    // ✅ CREAR CLIENTE
    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteServicio.guardarCliente(cliente);
    }

    // ✅ ACTUALIZAR CLIENTE
    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {

        Cliente clienteExistente = clienteServicio.buscarClientePorId(id);

        if (clienteExistente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellido(cliente.getApellido());
        clienteExistente.setDni(cliente.getDni());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setContrasena(cliente.getContrasena());

        return clienteServicio.guardarCliente(clienteExistente);
    }

    // ✅ ELIMINAR CLIENTE
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteServicio.eliminarCliente(id);
    }
}