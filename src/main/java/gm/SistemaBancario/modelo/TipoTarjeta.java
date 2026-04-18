package gm.SistemaBancario.modelo;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

@Entity
@Table(name = "tipo_tarjeta")
public class TipoTarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipo")
    private Long idTipo;

    @Column(name = "tipo")
    private String tipo;


    public TipoTarjeta() {
    }

    public TipoTarjeta(Long idTipo, String tipo) {
        this.idTipo = idTipo;
        this.tipo = tipo;

    }

    public Long getIdTipoTarjeta() {
        return idTipo;
    }

    public void setIdTipoTarjeta(Long idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    @Override
    public String toString() {
        return "TipoTarjeta{" +
                "idTipo=" + idTipo +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}