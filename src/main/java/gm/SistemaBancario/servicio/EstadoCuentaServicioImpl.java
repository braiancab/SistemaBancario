package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.EstadoCuenta;
import gm.SistemaBancario.repositorio.EstadoCuentaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstadoCuentaServicioImpl implements EstadoCuentaServicio {

    private final EstadoCuentaRepositorio estadoCuentaRepositorio;

    public EstadoCuentaServicioImpl(EstadoCuentaRepositorio estadoCuentaRepositorio) {
        this.estadoCuentaRepositorio = estadoCuentaRepositorio;
    }

    @Override
    public List<EstadoCuenta> listar() {
        return estadoCuentaRepositorio.findAll();
    }

    @Override
    public Optional<EstadoCuenta> buscarPorId(Long id) {
        return estadoCuentaRepositorio.findById(id);
    }

    @Override
    public EstadoCuenta guardar(EstadoCuenta estadoCuenta) {
        return estadoCuentaRepositorio.save(estadoCuenta);
    }

    @Override
    public void eliminar(Long id) {
        estadoCuentaRepositorio.deleteById(id);
    }
}
