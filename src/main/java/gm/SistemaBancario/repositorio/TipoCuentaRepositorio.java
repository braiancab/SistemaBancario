package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoCuentaRepositorio extends JpaRepository<TipoCuenta, Long> {

    Optional<TipoCuenta> findByTipo(String tipo);


    Optional<TipoCuenta> findById(Long idTipo);
}
