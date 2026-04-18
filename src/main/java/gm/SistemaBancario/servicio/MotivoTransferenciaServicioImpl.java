package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.MotivoTransferencia;
import gm.SistemaBancario.repositorio.MotivoTransferenciaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MotivoTransferenciaServicioImpl implements MotivoTransferenciaServicio {

    private final MotivoTransferenciaRepositorio motivoRepositorio;

    public MotivoTransferenciaServicioImpl(MotivoTransferenciaRepositorio motivoRepositorio) {
        this.motivoRepositorio = motivoRepositorio;
    }

    @Override
    public List<MotivoTransferencia> listar() {
        return motivoRepositorio.findAll();
    }

    @Override
    public Optional<MotivoTransferencia> buscarPorId(Long id) {
        return motivoRepositorio.findById(id);
    }

    @Override
    public MotivoTransferencia guardar(MotivoTransferencia motivo) {
        return motivoRepositorio.save(motivo);
    }

    @Override
    public void eliminar(Long id) {
        motivoRepositorio.deleteById(id);
    }
}
