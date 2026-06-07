package gm.SistemaBancario.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(
            String destinatario,
            String asunto,
            String mensaje) {

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("braiancabral618@gmail.com");
        email.setTo(destinatario);
        email.setSubject(asunto);
        email.setText(mensaje);

        try {

            mailSender.send(email);

            System.out.println("Correo enviado correctamente");

        } catch (Exception e) {

            System.out.println("ERROR AL ENVIAR CORREO");
            e.printStackTrace();
        }
    }
}