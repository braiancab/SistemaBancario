package gm.SistemaBancario.controlador;

import gm.SistemaBancario.dto.*;
import gm.SistemaBancario.servicio.AuthServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthControlador {

    @Autowired
    private AuthServicio authServicio;

    @PostMapping("/register")
    public String registrar(@RequestBody RegisterRequest request) {
        return authServicio.registrar(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authServicio.login(request);
    }
}