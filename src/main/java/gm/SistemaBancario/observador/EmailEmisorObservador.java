package gm.SistemaBancario.observador;

import gm.SistemaBancario.modelo.Transferencia;

import gm.SistemaBancario.servicio.EmailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailEmisorObservador implements ObservadorTransferencia {

    @Autowired
    private EmailServicio emailServicio;

    @Override
    public void actualizar(Transferencia transferencia) {


        System.out.println(
                "[OBSERVER EMISOR] Transferencia detectada. Monto: "
                        + transferencia.getMonto()
        );

        String mensaje =
                "Estimado/a " +
                        transferencia.getCuentaOrigen()
                                .getCliente()
                                .getNombre() +
                        ",\n\n" +

                        "Su transferencia fue procesada correctamente.\n\n" +

                        "DETALLE DE LA OPERACIÓN\n" +
                        "----------------------------------\n" +
                        "Monto transferido: $" + transferencia.getMonto() + "\n" +
                        "Cuenta destino: " +
                        transferencia.getCuentaDestino().getAlias() + "\n" +
                        "Motivo: " +
                        transferencia.getMotivo().getMotivo() + "\n" +
                        "Fecha: " + LocalDateTime.now() + "\n" +
                        "Estado: COMPLETADA\n" +
                        "----------------------------------\n\n" +

                        "Conserve este correo como comprobante de la operación.\n\n" +

                        "Gracias por utilizar Sistema Bancario.\n\n" +

                        "Este es un mensaje automático, por favor no responda este correo.";


        String email =
                transferencia.getCuentaOrigen()
                        .getCliente()
                        .getEmail();

        emailServicio.enviarCorreo(
                email,
                "Transferencia realizada correctamente",
                mensaje
        );
    }
}