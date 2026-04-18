package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.OrdenExtraccion;
import java.util.List;
import java.util.Optional;


public interface OrdenExtraccionServicio {
    List<OrdenExtraccion> listarTodas();
    Optional<OrdenExtraccion> buscarPorId(Long id);
    OrdenExtraccion guardar(OrdenExtraccion orden);
    void eliminar(Long id);
    List<OrdenExtraccion> buscarPorCodigo(String codigo);
}