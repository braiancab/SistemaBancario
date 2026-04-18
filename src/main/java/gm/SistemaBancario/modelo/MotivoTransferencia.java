package gm.SistemaBancario.modelo;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;



@Entity
@Table(name = "motivo_transferencia")
public class MotivoTransferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motivo")
    private Long idMotivo;

    private String motivo;

    public MotivoTransferencia() {
    }

    public MotivoTransferencia(Long idMotivo, String motivo) {
        this.idMotivo = idMotivo;
        this.motivo = motivo;
    }

    public Long getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Long idMotivo) {
        this.idMotivo = idMotivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "MotivoTransferencia{idMotivo=" + idMotivo + ", motivo='" + motivo + "'}";
    }
}
