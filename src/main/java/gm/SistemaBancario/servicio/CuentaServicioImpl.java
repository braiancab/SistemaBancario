package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cliente;
import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.repositorio.ClienteRepositorio;
import gm.SistemaBancario.repositorio.CuentaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

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

        // 1. Inicializar saldo con BigDecimal.ZERO (es más eficiente que new BigDecimal(0))
        cuenta.setSaldo(BigDecimal.ZERO);

        // 2. Generar CVU de 22 dígitos
        cuenta.setCvu(generarCvuAleatorio());

        // 3. Generar Alias con formato palabra.palabra.palabra
        cuenta.setAlias(generarAliasAleatorio());

        return cuentaRepositorio.save(cuenta);
    }

    private String generarCvuAleatorio() {
        StringBuilder cvu = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 22; i++) {
            cvu.append(random.nextInt(10));
        }
        return cvu.toString();
    }

    private String generarAliasAleatorio() {
        String[] palabras = {"sol", "luna", "rio", "monte", "verde", "veloz", "azul", "nube", "fuego", "viento", "verde", "cancha", "clave", "casa"};
        java.util.Random random = new java.util.Random();

        return palabras[random.nextInt(palabras.length)] + "." +
                palabras[random.nextInt(palabras.length)] + "." +
                palabras[random.nextInt(palabras.length)];
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