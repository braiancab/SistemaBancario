package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cuenta;
import java.util.List;

public interface CuentaServicio {

    Cuenta crearCuenta(Long idCliente, String tipoCuenta);

    List<Cuenta> obtenerCuentasPorCliente(Long idCliente);

    Cuenta buscarPorNumeroCuenta(String numeroCuenta);
}