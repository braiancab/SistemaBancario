package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.MotivoTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotivoTransferenciaRepositorio extends JpaRepository<MotivoTransferencia, Long> {

    Optional<MotivoTransferencia> findByMotivo(String motivo);
}

