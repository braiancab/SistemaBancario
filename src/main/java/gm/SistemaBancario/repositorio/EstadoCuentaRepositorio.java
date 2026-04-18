package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.EstadoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoCuentaRepositorio extends JpaRepository<EstadoCuenta, Long> {

    Optional<EstadoCuenta> findByEstado(String estado);

    Optional<EstadoCuenta> findById(Long idEstado);
}