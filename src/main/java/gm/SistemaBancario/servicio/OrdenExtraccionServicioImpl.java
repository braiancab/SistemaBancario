package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.modelo.OrdenExtraccion;
import gm.SistemaBancario.repositorio.OrdenExtraccionRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gm.SistemaBancario.repositorio.CuentaRepositorio;

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
    public Optional<OrdenExtraccion> buscarPorId(Long id) {
        return ordenRepositorio.findById(id);
    }

    @Override
    @Transactional
    public OrdenExtraccion guardar(OrdenExtraccion orden) {

       if (orden.getCuentaOrigen() == null || orden.getCuentaOrigen().getIdCuenta() == null) {
        throw new RuntimeException("Cuenta origen inválida");
    }

    Long idCuenta = orden.getCuentaOrigen().getIdCuenta();

    Cuenta cuenta = cuentarepositorio.findById(idCuenta)
        .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

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