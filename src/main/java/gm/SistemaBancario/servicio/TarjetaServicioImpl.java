package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Tarjeta;
import gm.SistemaBancario.repositorio.TarjetaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TarjetaServicioImpl implements TarjetaServicio {

    private final TarjetaRepositorio tarjetaRepositorio;

    public TarjetaServicioImpl(TarjetaRepositorio tarjetaRepositorio) {
        this.tarjetaRepositorio = tarjetaRepositorio;
    }

    @Override
    public List<Tarjeta> listarTarjetas() {
        return tarjetaRepositorio.findAll();
    }

    @Override
    public Optional<Tarjeta> buscarPorId(Long id) {
        return tarjetaRepositorio.findById(id);
    }

    @Override
    public List<Tarjeta> listarPorCuenta(Long idCuenta) {
        return tarjetaRepositorio.findByCuentaIdCuenta(idCuenta);
    }

    @Override
    public Tarjeta guardarTarjeta(Tarjeta tarjeta) {
        return tarjetaRepositorio.save(tarjeta);
    }

    @Override
    public Tarjeta actualizarTarjeta(Tarjeta tarjeta) {
        return tarjetaRepositorio.save(tarjeta);
    }

    @Override
    public void eliminarTarjeta(Long id) {
        tarjetaRepositorio.deleteById(id);
    }
}
