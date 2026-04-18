package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Tarjeta;

import java.util.List;
import java.util.Optional;

public interface TarjetaServicio {

    List<Tarjeta> listarTarjetas();

    Optional<Tarjeta> buscarPorId(Long id);

    List<Tarjeta> listarPorCuenta(Long idCuenta);

    Tarjeta guardarTarjeta(Tarjeta tarjeta);

    Tarjeta actualizarTarjeta(Tarjeta tarjeta);

    void eliminarTarjeta(Long id);
}
