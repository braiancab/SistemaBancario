package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepositorio extends JpaRepository<Transferencia, Long> {


    //@Procedure(procedureName = "cantidad_transferencias")
    //Integer cantidadTransferencias(@Param("p_cuenta") Long cuentaId);

    @Query(
            value = "CALL cantidad_transferencias(:p_cuenta)",
            nativeQuery = true
    )
    Integer cantidadTransferencias(@Param("p_cuenta") Long cuentaId);

    @Query(
            value = "CALL total_transferido(:p_cuenta)",
            nativeQuery = true

    )
    Float totalTransferido(@Param("p_cuenta") Long cuentaId);

    // Transferencias enviadas desde una cuenta
    List<Transferencia> findByCuentaOrigenIdCuenta(Long idCuenta);

    // Transferencias recibidas en una cuenta
    List<Transferencia> findByCuentaDestinoIdCuenta(Long idCuenta);

    // Historial completo de una cuenta
    List<Transferencia> findByCuentaOrigenIdCuentaOrCuentaDestinoIdCuenta(Long origen, Long destino);
}