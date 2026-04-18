package gm.SistemaBancario.modelo;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

@Entity
@Table(name = "tarjeta")
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Long idTarjeta;

    @Column(nullable = false, length = 16, unique = true)
    private String numero;
    @Column(nullable = false)
    private String fecha_vencimiento;
    @Column(nullable = false)
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "id_cuenta")
    private Cuenta cuenta;

    //Relacion tipo tarjeta
    @ManyToOne(fetch = FetchType.EAGER) // Muchas cuentas -> Un tipo
    @JoinColumn(name="id_tipo", referencedColumnName = "idTipo", nullable = false)
    private TipoTarjeta tipoTarjeta;

    public Tarjeta() {
    }

    public Tarjeta(Long idTarjeta, String numero, String fecha_vencimiento,
                   String cvv, TipoTarjeta tipoTarjeta, Cuenta cuenta) {
        this.idTarjeta = idTarjeta;
        this.numero = numero;
        this.fecha_vencimiento = fecha_vencimiento;
        this.cvv = cvv;
        this.tipoTarjeta = tipoTarjeta;
        this.cuenta = cuenta;
    }

    public Long getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Long idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public TipoTarjeta getTipo() {
        return tipoTarjeta;
    }

    public void setTipo(TipoTarjeta tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public String toString() {
        return "Tarjeta{idTarjeta=" + idTarjeta +
                ", numero='" + numero + '\'' +
                ", tipo='" + tipoTarjeta + '\'' + '}';
    }
}

