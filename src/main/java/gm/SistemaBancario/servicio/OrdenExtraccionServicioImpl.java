package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.OrdenExtraccion;
import gm.SistemaBancario.repositorio.OrdenExtraccionRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrdenExtraccionServicioImpl implements OrdenExtraccionServicio {

    private final OrdenExtraccionRepositorio ordenRepositorio;


    public OrdenExtraccionServicioImpl(OrdenExtraccionRepositorio ordenRepositorio) {
        this.ordenRepositorio = ordenRepositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenExtraccion> listarTodas() {
        return ordenRepositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenExtraccion> buscarPorId(Long id) {
        return ordenRepositorio.findById(id);
    }

    @Override
    @Transactional
    public OrdenExtraccion guardar(OrdenExtraccion orden) {
        // Aquí podrías agregar validaciones de saldo antes de guardar
        return ordenRepositorio.save(orden);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        ordenRepositorio.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenExtraccion> buscarPorCodigo(String codigo) {
        return ordenRepositorio.findByCodigo(codigo);
    }
}