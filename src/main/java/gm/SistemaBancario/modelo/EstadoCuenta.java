package gm.SistemaBancario.modelo;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;


@Entity
@Table(name = "estado_cuenta")
class EstadoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstado;
    @Column(nullable = false, length = 20)
    private String estado;

    public EstadoCuenta() {
    }

    public EstadoCuenta(Long idEstado, String estado) {
        this.idEstado = idEstado;
        this.estado = estado;
    }

    //Getters y Setters
    public Long getIdEstado() {return idEstado;}
    public void setIdEstado(Long idEstado) {this.idEstado = idEstado;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    //Metodo toString
    @Override
    public String toString() {
        return "EstadoCuenta{idEstado=" + idEstado + ", estado='" + estado + "'}";
    }
}
