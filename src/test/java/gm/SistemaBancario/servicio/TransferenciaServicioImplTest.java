package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.modelo.MotivoTransferencia;
import gm.SistemaBancario.modelo.Transferencia;
import gm.SistemaBancario.observador.EmailEmisorObservador;
import gm.SistemaBancario.observador.EmailReceptorObservador;
import gm.SistemaBancario.repositorio.CuentaRepositorio;
import gm.SistemaBancario.repositorio.MotivoTransferenciaRepositorio;
import gm.SistemaBancario.repositorio.TransferenciaRepositorio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferenciaServicioImplTest {

    @Mock
    private CuentaRepositorio cuentaRepositorio;

    @Mock
    private MotivoTransferenciaRepositorio motivoRepositorio;

    @Mock
    private TransferenciaRepositorio transferenciaRepositorio;

    // Agregamos los Mocks de los observadores
    @Mock
    private EmailEmisorObservador emailEmisorObserver;

    @Mock
    private EmailReceptorObservador emailReceptorObserver;

    @InjectMocks
    private TransferenciaServicioImpl transferenciaServicio;

    @Test
    void realizarTransferencia_Exitosamente_DeberiaMoverDineroYRegistrar() {
        // ==========================================
        // PASO 1: GIVEN (Preparar los datos simulados)
        // ==========================================
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(21L);
        cuentaOrigen.setSaldo(new BigDecimal("500.00"));

        Cuenta cuentaDestino = new Cuenta();
        cuentaDestino.setIdCuenta(1L);
        cuentaDestino.setSaldo(new BigDecimal("1000.00"));

        MotivoTransferencia motivo = new MotivoTransferencia();
        motivo.setIdMotivo(2L);

        when(cuentaRepositorio.findById(21L)).thenReturn(Optional.of(cuentaOrigen));
        when(cuentaRepositorio.findById(1L)).thenReturn(Optional.of(cuentaDestino));
        when(motivoRepositorio.findById(2L)).thenReturn(Optional.of(motivo));

        when(transferenciaRepositorio.save(any(Transferencia.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ==========================================
        // PASO 2: WHEN (Ejecutar la lógica real)
        // ==========================================
        Float montoATransferir = 100.0f;
        Transferencia resultado = transferenciaServicio.realizarTransferencia(21L, 1L, montoATransferir, 2L);

        // ==========================================
        // PASO 3: THEN (Verificar que todo salió bien)
        // ==========================================
        assertNotNull(resultado);
        assertEquals("COMPLETADA", resultado.getEstado());
        assertEquals(montoATransferir, resultado.getMonto());

        //origen : 500 - 100 = 400
        //destino : 1000 + 100 = 1100
        assertEquals(new BigDecimal("400.00"), cuentaOrigen.getSaldo());
        assertEquals(new BigDecimal("1100.00"), cuentaDestino.getSaldo());

        verify(cuentaRepositorio, times(1)).save(cuentaOrigen);
        verify(cuentaRepositorio, times(1)).save(cuentaDestino);
        verify(transferenciaRepositorio, times(1)).save(any(Transferencia.class));

        // Verificar que se haya notificado a ambos observadores
        verify(emailEmisorObserver, times(1)).actualizar(any(Transferencia.class));
        verify(emailReceptorObserver, times(1)).actualizar(any(Transferencia.class));
    }

    @Test
    void realizarTransferencia_SaldoInsuficiente_DeberiaLanzarRuntimeException() {
        // ==========================================
        // PASO 1: GIVEN origen con poco saldo
        // ==========================================
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(1L);
        cuentaOrigen.setSaldo(new BigDecimal("100.00"));

        Cuenta cuentaDestino = new Cuenta();
        cuentaDestino.setIdCuenta(2L);
        cuentaDestino.setSaldo(new BigDecimal("1000.00"));

        MotivoTransferencia motivo = new MotivoTransferencia();
        motivo.setIdMotivo(9L);

        when(cuentaRepositorio.findById(1L)).thenReturn(Optional.of(cuentaOrigen));
        when(cuentaRepositorio.findById(2L)).thenReturn(Optional.of(cuentaDestino));
        when(motivoRepositorio.findById(9L)).thenReturn(Optional.of(motivo));

        // ==========================================
        // PASO 2 Y 3: WHEN y THEN Se espera el error
        // ==========================================
        Float montoExcesivo = 500.0f;

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            transferenciaServicio.realizarTransferencia(1L, 2L, montoExcesivo, 9L);
        });

        assertEquals("Saldo insuficiente", excepcion.getMessage());

        verify(cuentaRepositorio, never()).save(any(Cuenta.class));
        verify(transferenciaRepositorio, never()).save(any(Transferencia.class));

        //  Si falla por saldo, NO se debe mandar ningún email
        verify(emailEmisorObserver, never()).actualizar(any());
        verify(emailReceptorObserver, never()).actualizar(any());
    }
}