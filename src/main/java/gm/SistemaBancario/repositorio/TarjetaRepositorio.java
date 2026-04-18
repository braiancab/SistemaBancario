package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.Tarjeta;
import gm.SistemaBancario.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarjetaRepositorio extends JpaRepository<Tarjeta, Long> {

    List<Tarjeta> findByCuenta(Cuenta cuenta);

    List<Tarjeta> findByCuentaIdCuenta(Long idCuenta);

    Optional<Tarjeta> findByNumero(String numero);

    boolean existsByNumero(String numero);
}
