package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.MotivoTransferencia;

import java.util.List;
import java.util.Optional;

public interface MotivoTransferenciaServicio {

    List<MotivoTransferencia> listar();

    Optional<MotivoTransferencia> buscarPorId(Long id);

    MotivoTransferencia guardar(MotivoTransferencia motivo);

    void eliminar(Long id);
}

