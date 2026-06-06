package gm.SistemaBancario.observador;

import gm.SistemaBancario.modelo.Transferencia;

public interface ObservadorTransferencia {

    void actualizar(Transferencia transferencia);

}