package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cliente;
import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.repositorio.ClienteRepositorio;
import gm.SistemaBancario.repositorio.CuentaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CuentaServicioImpl implements CuentaServicio {

    private final CuentaRepositorio cuentaRepositorio;
    private final ClienteRepositorio clienteRepositorio;

    public CuentaServicioImpl(CuentaRepositorio cuentaRepositorio,
                              ClienteRepositorio clienteRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    // ✅ Crear cuenta
    @Override
    public Cuenta crearCuenta(Long idCliente, String tipoCuenta) {

        Cliente cliente = clienteRepositorio.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Cuenta cuenta = new Cuenta();
        cuenta.setCliente(cliente);
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setSaldo(0f);
        cuenta.setNumeroCuenta(generarNumeroCuenta());

        return cuentaRepositorio.save(cuenta);
    }

    // ✅ Generar número único
    private String generarNumeroCuenta() {
        String numero;
        do {
            numero = String.valueOf(10000000 + new Random().nextInt(90000000));
        } while (cuentaRepositorio.existsByNumeroCuenta(numero));

        return numero;
    }

    // ✅ Obtener cuentas de un cliente
    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> obtenerCuentasPorCliente(Long idCliente) {
        return cuentaRepositorio.findByClienteIdCliente(idCliente);
    }

    // ✅ Buscar cuenta
    @Override
    @Transactional(readOnly = true)
    public Cuenta buscarPorNumeroCuenta(String numeroCuenta) {
        return cuentaRepositorio.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }
}