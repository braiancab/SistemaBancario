package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;


public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {

    // Buscar por número de cuenta (único)
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    // Verificar existencia
    boolean existsByNumeroCuenta(String numeroCuenta);

    // Obtener cuentas de un cliente
    List<Cuenta> findByClienteIdCliente(Long idCliente);
}