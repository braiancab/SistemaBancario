package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.EstadoCuenta;

import java.util.List;
import java.util.Optional;

public interface EstadoCuentaServicio {

    List<EstadoCuenta> listar();

    Optional<EstadoCuenta> buscarPorId(Long id);

    EstadoCuenta guardar(EstadoCuenta estadoCuenta);

    void eliminar(Long id);
}
