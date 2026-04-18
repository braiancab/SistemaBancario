package gm.SistemaBancario.repositorio;

import gm.SistemaBancario.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    // Buscar por DNI (único)
    Optional<Cliente> findByDni(String dni);

    // Buscar por email (login)
    Optional<Cliente> findByEmail(String email);

    // Validaciones
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
}