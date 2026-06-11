package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.modelo.OrdenExtraccion;
import gm.SistemaBancario.modelo.Cliente;
import gm.SistemaBancario.repositorio.OrdenExtraccionRepositorio;
import gm.SistemaBancario.repositorio.CuentaRepositorio;
import gm.SistemaBancario.repositorio.ClienteRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrdenExtraccionServicioImpl implements OrdenExtraccionServicio {

    private final OrdenExtraccionRepositorio ordenRepositorio;
    private final CuentaRepositorio cuentarepositorio;
    private final ClienteRepositorio clienteRepositorio;

    // Constructor
    public OrdenExtraccionServicioImpl(OrdenExtraccionRepositorio ordenRepositorio,
                                       CuentaRepositorio cuentarepositorio,
                                       ClienteRepositorio clienteRepositorio) {
        this.ordenRepositorio = ordenRepositorio;
        this.cuentarepositorio = cuentarepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenExtraccion> listarTodas() {
        return ordenRepositorio.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public List<OrdenExtraccion> historialOrdenExtraccion(Long idCliente) {
        // 1. Buscamos el Cliente
        Cliente cliente = clienteRepositorio.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<OrdenExtraccion> historial = ordenRepositorio.findByDni(cliente.getDni());
       if (historial.isEmpty()) {
            throw new RuntimeException("no hay movimientos registrados");
        }

        // 2. Extraemos su DNI y buscamos las extracciones
        return historial;

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenExtraccion> buscarPorId(Long id) {
        return ordenRepositorio.findById(id);
    }

    @Override
    @Transactional
    public OrdenExtraccion crearOrdenExtraccion(OrdenExtraccion orden) {
        Cuenta cuenta = cuentarepositorio.findById(
                orden.getCuentaOrigen().getIdCuenta()
        ).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if(cuenta.getSaldo().compareTo(BigDecimal.valueOf(orden.getMonto_orden())) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        BigDecimal retirado = BigDecimal.valueOf(orden.getMonto_orden());
        cuenta.setSaldo(cuenta.getSaldo().subtract(retirado));

        cuentarepositorio.save(cuenta);

        orden.setCuentaOrigen(cuenta);
        orden.setCodigo(UUID.randomUUID().toString().substring(0,6));
        return ordenRepositorio.save(orden);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        ordenRepositorio.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenExtraccion> buscarPorCodigo(String codigo) {
        return ordenRepositorio.findByCodigo(codigo);
    }
}