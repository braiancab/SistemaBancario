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

    @Column(length = 30)
    private String tipoCuenta;

    // Relación con cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @JsonIgnore
    private Cliente cliente;

    //Relacion tipo cuenta



    // Relación con transferencias
    @OneToMany(mappedBy = "cuentaOrigen")
    @JsonIgnore
    private List<Transferencia> transferenciasEnviadas;

    @OneToMany(mappedBy = "cuentaDestino")
    @JsonIgnore
    private List<Transferencia> transferenciasRecibidas;

    // --- Constructores ---
    public Cuenta() {}

    public Cuenta(Long idCuenta, String cvu, String alias,BigDecimal saldo, String tipoCuenta, Cliente cliente) {
        this.idCuenta = idCuenta;
        this.cvu = cvu;
        this.alias=alias;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
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

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

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