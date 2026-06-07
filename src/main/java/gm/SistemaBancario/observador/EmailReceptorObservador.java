package gm.SistemaBancario.observador;

import gm.SistemaBancario.modelo.Transferencia;
import gm.SistemaBancario.servicio.EmailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailReceptorObservador implements ObservadorTransferencia {

    @Autowired
    private EmailServicio emailServicio;

    @Override
    public void actualizar(Transferencia transferencia) {


        System.out.println(
                "[OBSERVER RECEPTOR] Transferencia detectada. Monto: "
                        + transferencia.getMonto()
        );
        String mensaje =
                "Estimado/a " +
                        transferencia.getCuentaDestino()
                                .getCliente()
                                .getNombre() +
                        ",\n\n" +

                        "Le informamos que ha recibido una transferencia bancaria con éxito.\n\n" +

                        "DETALLE DE LA OPERACIÓN\n" +
                        "----------------------------------\n" +
                        "Monto recibido: $" + transferencia.getMonto() + "\n" +
                        "Alias origen: " +
                        transferencia.getCuentaOrigen().getAlias() + "\n" +
                        "Fecha: " + LocalDateTime.now() + "\n" +
                        "Estado: COMPLETADA\n" +
                        "----------------------------------\n\n" +

                        "Si usted no reconoce esta operación, comuníquese con nuestro centro de atención al cliente.\n\n" +

                        "Gracias por utilizar Sistema Bancario.\n\n" +

                        "Este es un mensaje automático, por favor no responda este correo.";


        String email =
                transferencia.getCuentaDestino()
                        .getCliente()
                        .getEmail();

        emailServicio.enviarCorreo(
                email,
                "Transferencia recibida correctamente",
                mensaje
        );
    }
}