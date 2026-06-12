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
    public List<OrdenExtraccion> historialOrdenExtraccion(Long idCliente) {
        // 1. Buscamos el Cliente
        Cliente cliente = clienteRepositorio.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<OrdenExtraccion> historial = ordenRepositorio.findByDni(cliente.getDni());
       if (historial.isEmpty()) {
            throw new RuntimeException("No hay movimientos registrados");
        }

        // 2. Extraemos su DNI y buscamos las extracciones
        return historial;

    }

    @Override
    @Transactional
    public OrdenExtraccion crearOrdenExtraccion(OrdenExtraccion orden) {


        verificarDni(orden);

        Cuenta cuentaOrigen = cuentarepositorio.findById(
                orden.getCuentaOrigen().getIdCuenta()
        ).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        verificarSaldo(orden,cuentaOrigen);

        generarCodigoSeguridad(orden);

        BigDecimal retirado = BigDecimal.valueOf(orden.getMonto_orden());
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(retirado)); //resta saldo

        cuentarepositorio.save(cuentaOrigen);
        orden.setCuentaOrigen(cuentaOrigen);

        return ordenRepositorio.save(orden);
    }


    private void verificarDni(OrdenExtraccion orden){
        if (orden.getDni() == null || orden.getDni().length() != 8) {
            throw new RuntimeException("DNI invalido");
        }
    }
    private void verificarSaldo(OrdenExtraccion orden,Cuenta cuentaOrigen){
        if(cuentaOrigen.getSaldo().compareTo(BigDecimal.valueOf(orden.getMonto_orden())) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
    }
    private void generarCodigoSeguridad(OrdenExtraccion orden){
        orden.setCodigo(UUID.randomUUID().toString().substring(0,6));
    }
}