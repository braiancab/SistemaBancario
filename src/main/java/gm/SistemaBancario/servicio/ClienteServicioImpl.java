package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cliente;
import gm.SistemaBancario.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteServicioImpl implements ClienteServicio {

    @Autowired
    private final ClienteRepositorio clienteRepositorio;

    public ClienteServicioImpl (ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return clienteRepositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarClientePorId(Long id) {
        return clienteRepositorio.findById(id).orElse(null);
    }

    @Override
    public Cliente buscarPorDni(String dni){
        return clienteRepositorio.findByDni(dni).orElse(null);
    }
    @Override
    public Cliente buscarPorEmail(String email){
        return clienteRepositorio.findByEmail(email).orElse(null);
    }


    @Override
    public Cliente guardarCliente(Cliente cliente) {

        return clienteRepositorio.save(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepositorio.deleteById(id);
    }
}