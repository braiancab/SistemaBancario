package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.modelo.OrdenExtraccion;
import gm.SistemaBancario.repositorio.OrdenExtraccionRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gm.SistemaBancario.repositorio.CuentaRepositorio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class OrdenExtraccionServicioImpl implements OrdenExtraccionServicio {

    private final OrdenExtraccionRepositorio ordenRepositorio;
    private final CuentaRepositorio cuentarepositorio;

    public OrdenExtraccionServicioImpl(OrdenExtraccionRepositorio ordenRepositorio, CuentaRepositorio cuentarepositorio) {
        this.ordenRepositorio = ordenRepositorio;
        this.cuentarepositorio = cuentarepositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenExtraccion> listarTodas() {
        return ordenRepositorio.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public List<OrdenExtraccion> historialOrdenExtraccion(String dni) {

        return ordenRepositorio.findByDni(dni);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenExtraccion> buscarPorId(Long id) {
        return ordenRepositorio.findById(id);
    }


    @Override
    @Transactional
    public OrdenExtraccion crearOrden(OrdenExtraccion orden) {

        Cuenta cuenta = cuentarepositorio.findById(
                orden.getCuentaOrigen().getIdCuenta()
        ).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // usamos compare to para convertir el monto de tipo double a bigdecimal y poder compararlos
        if(cuenta.getSaldo().compareTo(BigDecimal.valueOf(orden.getMonto_orden())) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }


        //BigDecimal resultado = total.subtract(BigDecimal.valueOf(descuento));
        BigDecimal retirado = BigDecimal.valueOf(orden.getMonto_orden());

        cuenta.setSaldo(



                cuenta.getSaldo().subtract(retirado)
        );

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