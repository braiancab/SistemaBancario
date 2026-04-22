package gm.SistemaBancario.modelo;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    // CVU de 22
    @Column(unique = true, nullable = false, length = 22)
    private String cvu;

    // Alias
    @Column(unique = true, nullable = false)
    private String alias;

    @Column(precision = 15, scale = 2)
    private BigDecimal saldo;


    // Relación con cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    //@JsonIgnore
    private Cliente cliente;

    //Relacion tipo cuenta
    @ManyToOne(fetch = FetchType.EAGER) // Muchas cuentas -> Un tipo
    @JoinColumn(name = "tipo_cuenta", referencedColumnName = "idTipo", nullable = false)
    //@JsonIgnore
    private TipoCuenta tipoCuenta;

    //Relacion con estado cuenta
    @ManyToOne(fetch = FetchType.EAGER) // Muchas cuentas -> Un estado
    @JoinColumn(name="estado_cuenta", referencedColumnName = "idEstado", nullable = false)
    //@JsonIgnore
    private EstadoCuenta estadoCuenta;


    // Relación con transferencias
    @OneToMany(mappedBy = "cuentaOrigen")
    @JsonIgnore
    private List<Transferencia> transferenciasEnviadas;

    @OneToMany(mappedBy = "cuentaDestino")
    @JsonIgnore
    private List<Transferencia> transferenciasRecibidas;

    // --- Constructores ---
    public Cuenta() {}

    public Cuenta(Long idCuenta, String cvu, String alias,BigDecimal saldo, TipoCuenta tipoCuenta,EstadoCuenta estadoCuenta, Cliente cliente) {
        this.idCuenta = idCuenta;
        this.cvu = cvu;
        this.alias=alias;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
        this.estadoCuenta = estadoCuenta;
        this.cliente = cliente;
    }

    // --- Getters y Setters ---
    public Long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Long idCuenta) { this.idCuenta = idCuenta; }

    public String getCvu() { return cvu; }
    public void setCvu(String cvu) { this.cvu = cvu; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public EstadoCuenta getEstadoCuenta() { return estadoCuenta; }
    public void setEstadoCuenta(EstadoCuenta estadoCuenta) { this.estadoCuenta = estadoCuenta; }


    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<Transferencia> getTransferenciasEnviadas() { return transferenciasEnviadas; }
    public void setTransferenciasEnviadas(List<Transferencia> transferenciasEnviadas) {
        this.transferenciasEnviadas = transferenciasEnviadas;
    }

    public List<Transferencia> getTransferenciasRecibidas() { return transferenciasRecibidas; }
    public void setTransferenciasRecibidas(List<Transferencia> transferenciasRecibidas) {
        this.transferenciasRecibidas = transferenciasRecibidas;
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Cuenta{" +
                "idCuenta=" + idCuenta +
                ", cvu='" + cvu + '\'' +
                ", alias='" + alias + '\'' +
                ", saldo=" + saldo +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                '}';
    }
}