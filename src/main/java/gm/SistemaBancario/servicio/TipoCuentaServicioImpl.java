package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.TipoCuenta;
import gm.SistemaBancario.repositorio.TipoCuentaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoCuentaServicioImpl implements TipoCuentaServicio {

    private final TipoCuentaRepositorio tipoCuentaRepositorio;

    public TipoCuentaServicioImpl(TipoCuentaRepositorio tipoCuentaRepositorio) {
        this.tipoCuentaRepositorio = tipoCuentaRepositorio;
    }

    @Override
    public List<TipoCuenta> listar() {
        return tipoCuentaRepositorio.findAll();
    }

    @Override
    public Optional<TipoCuenta> buscarPorId(Long id) {
        return tipoCuentaRepositorio.findById(id);
    }

    @Override
    public TipoCuenta guardar(TipoCuenta tipoCuenta) {
        return tipoCuentaRepositorio.save(tipoCuenta);
    }

    @Override
    public void eliminar(Long id) {
        tipoCuentaRepositorio.deleteById(id);
    }
}

