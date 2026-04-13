package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Transferencia;
import java.util.List;

public interface TransferenciaServicio {

    Transferencia realizarTransferencia(String cuentaOrigen,
                                        String cuentaDestino,
                                        Float monto);

    List<Transferencia> historialCuenta(Long idCuenta);
}