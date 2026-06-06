package gm.SistemaBancario.observador;

import gm.SistemaBancario.modelo.Transferencia;
import gm.SistemaBancario.servicio.EmailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailReceptorObservador implements ObservadorTransferencia {

    @Autowired
    private EmailServicio emailServicio;

    @Override
    public void actualizar(Transferencia transferencia) {

        String email =
                transferencia.getCuentaDestino()
                        .getCliente()
                        .getEmail();

        emailServicio.enviarCorreo(
                email,
                "Transferencia recibida",
                "Ha recibido una transferencia por $" +
                        transferencia.getMonto()
        );
    }
}