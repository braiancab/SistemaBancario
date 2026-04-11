package gm.SistemaBancario.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transferencias")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float monto;

    private LocalDate fecha;

    @Column(length = 30)
    private String estado;

    // Cuenta origen
    @ManyToOne
    @JoinColumn(name = "cuenta_origen", nullable = false)
    private Cuenta cuentaOrigen;

    // Cuenta destino
    @ManyToOne
    @JoinColumn(name = "cuenta_destino", nullable = false)
    private Cuenta cuentaDestino;

    // --- Constructores ---
    public Transferencia() {
        this.fecha = LocalDate.now(); // default automático
    }

    public Transferencia(Long id, Float monto, LocalDate fecha, String estado,
                         Cuenta cuentaOrigen, Cuenta cuentaDestino) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Float getMonto() { return monto; }
    public void setMonto(Float monto) { this.monto = monto; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Cuenta getCuentaOrigen() { return cuentaOrigen; }
    public void setCuentaOrigen(Cuenta cuentaOrigen) { this.cuentaOrigen = cuentaOrigen; }

    public Cuenta getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(Cuenta cuentaDestino) { this.cuentaDestino = cuentaDestino; }

    // --- toString ---
    @Override
    public String toString() {
        return "Transferencia{" +
                "id=" + id +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                '}';
    }
}