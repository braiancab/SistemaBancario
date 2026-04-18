package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.TipoTarjeta;

import java.util.List;
import java.util.Optional;

public interface TipoTarjetaServicio {

    List<TipoTarjeta> listar();

    Optional<TipoTarjeta> buscarPorId(Long id);

    TipoTarjeta guardar(TipoTarjeta tipoTarjeta);

    void eliminar(Long id);
}
