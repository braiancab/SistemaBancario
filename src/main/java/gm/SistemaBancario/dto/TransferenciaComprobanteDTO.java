package gm.SistemaBancario.dto;
import java.time.LocalDateTime;



public class TransferenciaComprobanteDTO {

    private String cuentaOrigen;
    private String cuentaDestino;
    private Float monto;
    private String motivo;
    private LocalDateTime fecha;
    private String codigoOperacion;

    public TransferenciaComprobanteDTO() {}

    // GETTERS

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public Float getMonto() {
        return monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getCodigoOperacion() {
        return codigoOperacion;
    }

    // SETTERS

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setCodigoOperacion(String codigoOperacion) {
        this.codigoOperacion = codigoOperacion;
    }
}