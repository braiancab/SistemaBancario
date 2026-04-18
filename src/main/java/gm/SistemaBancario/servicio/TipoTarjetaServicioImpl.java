package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.TipoTarjeta;
import gm.SistemaBancario.repositorio.TipoTarjetaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoTarjetaServicioImpl implements TipoTarjetaServicio {

    private final TipoTarjetaRepositorio tipoTarjetaRepositorio;

    public TipoTarjetaServicioImpl(TipoTarjetaRepositorio tipoTarjetaRepositorio) {
        this.tipoTarjetaRepositorio = tipoTarjetaRepositorio;
    }

    @Override
    public List<TipoTarjeta> listar() {
        return tipoTarjetaRepositorio.findAll();
    }

    @Override
    public Optional<TipoTarjeta> buscarPorId(Long id) {
        return tipoTarjetaRepositorio.findById(id);
    }

    @Override
    public TipoTarjeta guardar(TipoTarjeta tipoTarjeta) {
        return tipoTarjetaRepositorio.save(tipoTarjeta);
    }

    @Override
    public void eliminar(Long id) {
        tipoTarjetaRepositorio.deleteById(id);
    }
}
