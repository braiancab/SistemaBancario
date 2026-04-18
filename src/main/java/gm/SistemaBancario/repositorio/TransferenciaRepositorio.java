package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepositorio extends JpaRepository<Transferencia, Long> {

    // Transferencias enviadas desde una cuenta
    List<Transferencia> findByCuentaOrigenIdCuenta(Long idCuenta);

    // Transferencias recibidas en una cuenta
    List<Transferencia> findByCuentaDestinoIdCuenta(Long idCuenta);

    // Historial completo de una cuenta
    List<Transferencia> findByCuentaOrigenIdCuentaOrCuentaDestinoIdCuenta(Long origen, Long destino);
}