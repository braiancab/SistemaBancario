package gm.SistemaBancario.servicio;


import gm.SistemaBancario.modelo.Transferencia;
import java.util.List;

public interface TransferenciaServicio {



    Transferencia realizarTransferencia(Long cuentaOrigen,
                                        Long cuentaDestino,
                                        Float monto,
                                        Long motivoId);

    List<Transferencia> historialCuenta(Long idCuenta);


     List<Transferencia> obtenerRecibidas(Long idCuenta);

     List<Transferencia> obtenerEnviadas(Long idCuenta);
}