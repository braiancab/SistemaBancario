package gm.SistemaBancario.servicio;
import gm.SistemaBancario.dto.*;

public interface AuthServicio {

    LoginResponse login(LoginRequest request);

    String registrar(RegisterRequest request);
}