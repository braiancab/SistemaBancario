package gm.SistemaBancario.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    @Column(unique = true, nullable = false)
    private String numeroCuenta;

    private Float saldo;

    @Column(length = 30)
    private String tipoCuenta;

    // Relación con cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    // Relación con transferencias
    @OneToMany(mappedBy = "cuentaOrigen")
    private List<Transferencia> transferenciasEnviadas;

    @OneToMany(mappedBy = "cuentaDestino")
    private List<Transferencia> transferenciasRecibidas;

    // --- Constructores ---
    public Cuenta() {}

    public Cuenta(Long idCuenta, String numeroCuenta, Float saldo, String tipoCuenta, Cliente cliente) {
        this.idCuenta = idCuenta;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
        this.cliente = cliente;
    }

    // --- Getters y Setters ---
    public Long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(Long idCuenta) { this.idCuenta = idCuenta; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public Float getSaldo() { return saldo; }
    public void setSaldo(Float saldo) { this.saldo = saldo; }

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
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", saldo=" + saldo +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                '}';
    }
}