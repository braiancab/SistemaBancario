package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.modelo.Transferencia;
import gm.SistemaBancario.repositorio.CuentaRepositorio;
import gm.SistemaBancario.repositorio.TransferenciaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

import java.util.List;

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

    // 💥 TRANSFERENCIA REAL
    @Override
    public Transferencia realizarTransferencia(String cuentaOrigenNum,
                                               String cuentaDestinoNum,
                                               Float monto) {

        // 1. Validar monto
        if (monto <= 0) {
            throw new RuntimeException("El monto debe ser mayor a 0");
        }

        // 2. Buscar cuentas
        Cuenta origen = cuentaRepositorio.findByAlias(cuentaOrigenNum)
                .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada"));

        Cuenta destino = cuentaRepositorio.findByAlias(cuentaDestinoNum)
                .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada"));

        // 3. Validar saldo
        //if (origen.getSaldo() < monto) {
          //  throw new RuntimeException("Saldo insuficiente");
        //}

        // 1. Convertir el monto de float a BigDecimal de forma segura
        BigDecimal montoBI = BigDecimal.valueOf(monto);

        // 2. Comparar saldos: origen.getSaldo() < monto
        // compareTo devuelve: -1 (menor), 0 (igual), 1 (mayor)
        if (origen.getSaldo().compareTo(montoBI)<0) {
         throw new RuntimeException("Saldo insuficiente");
        }


        // 4. Actualizar saldos
      //  origen.setSaldo(origen.getSaldo() - monto);
       // destino.setSaldo(destino.getSaldo() + monto);


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

    // 📊 Historial de una cuenta
    @Override
    @Transactional(readOnly = true)
    public List<Transferencia> historialCuenta(Long idCuenta) {
        return transferenciaRepositorio
                .findByCuentaOrigenIdCuentaOrCuentaDestinoIdCuenta(idCuenta, idCuenta);
    }
}