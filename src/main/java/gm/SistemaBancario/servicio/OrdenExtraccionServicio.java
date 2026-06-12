package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.modelo.OrdenExtraccion;
import java.util.List;
import java.util.Optional;


public interface OrdenExtraccionServicio {
     OrdenExtraccion crearOrdenExtraccion(OrdenExtraccion orden);
    List<OrdenExtraccion> historialOrdenExtraccion(Long idCliente);
    //void verificarDni(OrdenExtraccion orden);
    //void generarCodigoSeguridad();
    //void verificarSaldo(Cuenta cuentaOrigen,double monto);

}