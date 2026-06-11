package gm.SistemaBancario.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import gm.SistemaBancario.modelo.Transferencia;

public interface TransferenciaProcedimientoAlmacenado extends JpaRepository<Transferencia, Long> {

    @Procedure(procedureName = "cantidad_transferencias")
    Integer cantidadTransferencias(@Param("p_cuenta") Long cuentaId);

    @Procedure(procedureName = "total_transferido")
    Float totalTransferido(@Param("p_cuenta") Long cuentaId);
}