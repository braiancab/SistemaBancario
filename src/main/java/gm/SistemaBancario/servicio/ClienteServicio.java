package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cliente;
import java.util.List;

public interface ClienteServicio {

    List<Cliente> listarClientes();

    Cliente buscarClientePorId(Long id);

    Cliente buscarPorDni(String dni);

    Cliente buscarPorEmail(String email);

    Cliente guardarCliente(Cliente cliente);

    void eliminarCliente(Long id);
}