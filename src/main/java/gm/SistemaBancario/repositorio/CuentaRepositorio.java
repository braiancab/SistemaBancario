package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {

    // Buscar por número de cuenta (único)
   Optional<Cuenta> findByAlias(String alias);

    // Verificar existencia
   // boolean existsByNumeroCuenta(String numeroCuenta);

    boolean existsByCvu(String cvu);

    // Obtener cuentas de un cliente
    List<Cuenta> findByClienteIdCliente(Long idCliente);

    //Buscar cuentas de destino
    @Query("SELECT c FROM Cuenta c WHERE c.cliente.idCliente <> :idCliente")
    List<Cuenta> findCuentasDestino(@Param("idCliente") Long idCliente);
}