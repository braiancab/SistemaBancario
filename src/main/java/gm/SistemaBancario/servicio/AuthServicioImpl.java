package gm.SistemaBancario.servicio;

import gm.SistemaBancario.dto.*;
import gm.SistemaBancario.modelo.Cliente;
import gm.SistemaBancario.repositorio.ClienteRepositorio;
import gm.SistemaBancario.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServicioImpl implements AuthServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String registrar(RegisterRequest request) {

        if(clienteRepositorio.findByEmail(request.getEmail()).isPresent()) {
            return "Email ya registrado";
        }

        Cliente cliente = new Cliente();

        cliente.setDni(request.getDniCliente());
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setContrasena(encoder.encode(request.getContrasena()));

        clienteRepositorio.save(cliente);

        return "Usuario registrado correctamente";
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Cliente cliente = clienteRepositorio.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(!encoder.matches(request.getContrasena(), cliente.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = JwtUtil.generarToken(cliente.getEmail());

        return new LoginResponse(token, "Login exitoso");
    }
}