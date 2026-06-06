package gm.SistemaBancario.observador;

import gm.SistemaBancario.modelo.Transferencia;

import gm.SistemaBancario.servicio.EmailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailEmisorObservador implements ObservadorTransferencia {

    @Autowired
    private EmailServicio emailServicio;

    @Override
    public void actualizar(Transferencia transferencia) {

        String email =
                transferencia.getCuentaOrigen()
                        .getCliente()
                        .getEmail();

        emailServicio.enviarCorreo(
                email,
                "Transferencia realizada",
                "Su transferencia por $" +
                        transferencia.getMonto() +
                        " fue realizada correctamente."
        );
    }
}