package gm.SistemaBancario.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferenciaDTO {

    private Long cuentaOrigen;
    private Long cuentaDestino;
    private Float monto;
    private Long motivo;


    public TransferenciaDTO() {}


    // Getters y Setters
    public Long getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Long getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Long getMotivo() {
        return motivo;
    }

    public void setMotivo(Long motivo) {
        this.motivo = motivo;
    }
}