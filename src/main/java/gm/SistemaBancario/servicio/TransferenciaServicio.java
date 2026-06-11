package gm.SistemaBancario.servicio;


import gm.SistemaBancario.modelo.Transferencia;
import gm.SistemaBancario.repositorio.TransferenciaProcedimientoAlmacenado;
import java.util.List;

public interface TransferenciaServicio {

    Integer cantidadTransferencias(Long cuentaId);

    Float totalTransferido(Long cuentaId);


    Transferencia realizarTransferencia(Long cuentaOrigen,
                                        Long cuentaDestino,
                                        Float monto,
                                        Long motivoId);

    List<Transferencia> historialCuenta(Long idCuenta);


     List<Transferencia> obtenerRecibidas(Long idCuenta);

     List<Transferencia> obtenerEnviadas(Long idCuenta);
}