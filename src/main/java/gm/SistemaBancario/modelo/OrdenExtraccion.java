package gm.SistemaBancario.modelo;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

@Entity
@Table(name = "Orden_extraccion")
public class OrdenExtraccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_extraccion;

    private String codigo;

    private Double monto_orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_origen")
    private Cuenta cuentaOrigen;

    // --- Constructores ---

    public OrdenExtraccion() {
    }

    public OrdenExtraccion(Long id_extraccion, String codigo, Double monto_orden, Cuenta cuentaOrigen) {
        this.id_extraccion = id_extraccion;
        this.codigo = codigo;
        this.monto_orden = monto_orden;
        this.cuentaOrigen = cuentaOrigen;
    }

    // --- Métodos Getters y Setters ---

    public Long getId_extraccion() {
        return id_extraccion;
    }

    public void setId_extraccion(Long id_extraccion) {
        this.id_extraccion = id_extraccion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getMonto_orden() {
        return monto_orden;
    }

    public void setMonto_orden(Double monto_orden) {
        this.monto_orden = monto_orden;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }


    @Override
    public String toString() {
        return "OrdenExtraccion{" +
                "id_extraccion=" + id_extraccion +
                ", codigo='" + codigo + '\'' +
                ", monto_orden=" + monto_orden +
                ", cuentaOrigen=" + (cuentaOrigen != null ? cuentaOrigen.getIdCuenta() : "null") +
                '}';
    }
}