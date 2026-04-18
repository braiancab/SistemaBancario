package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.OrdenExtraccion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdenExtraccionRepositorio extends JpaRepository<OrdenExtraccion, Long> {

    //
    List<OrdenExtraccion> findByCodigo(String codigo);

    //
    List<OrdenExtraccion> findByCuentaOrigen_IdCuenta(Long idCuenta);
}