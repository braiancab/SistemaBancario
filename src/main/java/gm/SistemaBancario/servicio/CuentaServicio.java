package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cuenta;
import java.util.List;

public interface CuentaServicio {

    Cuenta crearCuenta(Long idCliente, Long idTipo, Long idEstado);

    List<Cuenta> obtenerCuentasPorCliente(Long idCliente);

    Cuenta buscarPorNumeroCuenta(String numeroCuenta);

     List<Cuenta> obtenerCuentasDestino(Long idCliente);
}