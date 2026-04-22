package gm.SistemaBancario.servicio;

import gm.SistemaBancario.dto.TransferenciaComprobanteDTO;
import gm.SistemaBancario.dto.TransferenciaDTO;
import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.modelo.Transferencia;
import gm.SistemaBancario.repositorio.CuentaRepositorio;
import gm.SistemaBancario.repositorio.TransferenciaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TransferenciaServicioImpl implements TransferenciaServicio {

    private final CuentaRepositorio cuentaRepositorio;
    private final TransferenciaRepositorio transferenciaRepositorio;

    public TransferenciaServicioImpl(CuentaRepositorio cuentaRepositorio,
                                     TransferenciaRepositorio transferenciaRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.transferenciaRepositorio = transferenciaRepositorio;
    }

    public TransferenciaComprobanteDTO realizarTransferencia(TransferenciaDTO request) {

        Cuenta origen = cuentaRepositorio.findById(request.getCuentaOrigen()).orElseThrow();
        Cuenta destino = cuentaRepositorio.findById(request.getCuentaDestino()).orElseThrow();


        BigDecimal monto = BigDecimal.valueOf(request.getMonto());

        // lógica de transferencia
        origen.setSaldo(origen.getSaldo().subtract(monto));
        destino.setSaldo(destino.getSaldo().add(monto));

        cuentaRepositorio.save(origen);
        cuentaRepositorio.save(destino);

        // 🔥 crear comprobante
        TransferenciaComprobanteDTO comprobante = new TransferenciaComprobanteDTO();
        comprobante.setCuentaOrigen(origen.getAlias()); // o nombre
        comprobante.setCuentaDestino(destino.getAlias());
        comprobante.setMonto(request.getMonto());
        comprobante.setMotivo("Varios"); // o buscarlo desde DB
        comprobante.setFecha(LocalDateTime.now());
        comprobante.setCodigoOperacion(UUID.randomUUID().toString());

        return comprobante;
    }




    // TRANSFERENCIA
    @Override
    public Transferencia realizarTransferencia(Long cuentaOrigenNum,
                                               Long cuentaDestinoNum,
                                               Float monto,
                                               Long motivoId) {

        // 1. Validar monto
        if (monto <= 0) {
            throw new RuntimeException("El monto debe ser mayor a 0");
        }

        // 2. Buscar cuentas
        Cuenta origen = cuentaRepositorio.findById(cuentaOrigenNum)
                .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada"));

        Cuenta destino = cuentaRepositorio.findById(cuentaDestinoNum)
                .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada"));



        // 1. Convertir el monto de float a BigDecimal de forma segura
        BigDecimal montoBI = BigDecimal.valueOf(monto);

        // 2. Comparar saldos: origen.getSaldo() < monto
        // compareTo devuelve: -1 (menor), 0 (igual), 1 (mayor)
        if (origen.getSaldo().compareTo(montoBI)<0) {
         throw new RuntimeException("Saldo insuficiente");
        }



        // 3. Actualizar saldos usando .subtract() y .add()
        origen.setSaldo(origen.getSaldo().subtract(montoBI));
        destino.setSaldo(destino.getSaldo().add(montoBI));

        cuentaRepositorio.save(origen);
        cuentaRepositorio.save(destino);




        // 5. Registrar transferencia

        Transferencia transferencia = new Transferencia();
        transferencia.setMonto(monto);
        transferencia.setEstado("COMPLETADA");
        transferencia.setCuentaOrigen(origen);
        transferencia.setCuentaDestino(destino);

        return transferenciaRepositorio.save(transferencia);
    }

    //  Historial de una cuenta
    @Override
    @Transactional(readOnly = true)
    public List<Transferencia> historialCuenta(Long idCuenta) {
        return transferenciaRepositorio
                .findByCuentaOrigenIdCuentaOrCuentaDestinoIdCuenta(idCuenta, idCuenta);
    }
}