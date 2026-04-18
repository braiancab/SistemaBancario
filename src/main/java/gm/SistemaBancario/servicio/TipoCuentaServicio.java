package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.TipoCuenta;

import java.util.List;
import java.util.Optional;

public interface TipoCuentaServicio {

    List<TipoCuenta> listar();

    Optional<TipoCuenta> buscarPorId(Long id);

    TipoCuenta guardar(TipoCuenta tipoCuenta);

    void eliminar(Long id);
}
