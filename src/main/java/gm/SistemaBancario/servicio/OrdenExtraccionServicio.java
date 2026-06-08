package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.OrdenExtraccion;
import java.util.List;
import java.util.Optional;


public interface OrdenExtraccionServicio {
    List<OrdenExtraccion> listarTodas();
     OrdenExtraccion crearOrdenExtraccion(OrdenExtraccion orden);
    Optional<OrdenExtraccion> buscarPorId(Long id);
    List<OrdenExtraccion> historialOrdenExtraccion(Long idCliente);
    //OrdenExtraccion guardar(OrdenExtraccion orden);
    void eliminar(Long id);
    List<OrdenExtraccion> buscarPorCodigo(String codigo);
}