package gm.SistemaBancario.modelo;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "tipo_cuenta")
class TipoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipo;

    @Column( nullable = false, length = 20)
    private String tipo;



    //Constructores

    public TipoCuenta() {
    }


    public TipoCuenta(Long idTipo, String tipo) {
        this.idTipo = idTipo;
        this.tipo = tipo;
    }


    //Getters y Setters


    public Long getIdTipo() { return idTipo;}
    public void setIdTipo(Long idTipo) { this.idTipo = idTipo;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

    //Metodo toString
    @Override
    public String toString() {
        return "TipoCuenta{idTipo=" + idTipo + ", tipo='" + tipo + "'}";
    }
}
