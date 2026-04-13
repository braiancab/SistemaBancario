package gm.SistemaBancario.controlador;

import gm.SistemaBancario.modelo.Cliente;
import gm.SistemaBancario.servicio.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes") //http://localhost:8080/api/clientes
@CrossOrigin(origins = "*") //
public class ClienteControlador {

    private final ClienteServicio clienteServicio;

    @Autowired
    public ClienteControlador(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    //LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteServicio.listarClientes());
    }

    //BUSCAR POR ID

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteServicio.buscarClientePorId(id);
        if(cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    //CREAR CLIENTE
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        //Ignoramos un cliente con id para evitar conflictos
        cliente.setIdCliente(null);
        Cliente creado = clienteServicio.guardarCliente(cliente);
        URI location = URI.create("/api/clientes/"+creado.getIdCliente());
        return ResponseEntity.created(location).body(creado);
    }

    //ACTUALIZAR CLIENTE
    //PUT api/clientes/{id} ->actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {

        Cliente clienteExistente = clienteServicio.buscarClientePorId(id);

        if (clienteExistente == null) {
            return ResponseEntity.notFound().build();
        }
        //Forzar el id del path para la actualizacion

        cliente.setIdCliente(id);
        Cliente actualizado = clienteServicio.guardarCliente(cliente);
        return ResponseEntity.ok(actualizado);
    }

    //ELIMINAR CLIENTE
    //DELETE api/clientes/{id} ->eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        Cliente existente = clienteServicio.buscarClientePorId(id);
        if (existente==null){
            return ResponseEntity.notFound().build();
        }
        clienteServicio.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}