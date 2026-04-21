package gm.SistemaBancario.dto;

public class LoginResponse {

    private String token;
    private String mensaje;
    private Long idCliente;



    public LoginResponse(String token, String mensaje, Long idCliente) {
        this.token = token;
        this.mensaje = mensaje;
        this.idCliente = idCliente;
    }
    public String getToken() {
        return token;
    }

    public String getMensaje() {
        return mensaje;
    }
    public Long getIdCliente() { return idCliente; }


}