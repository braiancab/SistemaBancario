package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.TipoTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoTarjetaRepositorio extends JpaRepository<TipoTarjeta, Long> {

    Optional<TipoTarjeta> findByTipo(String tipo);
}
