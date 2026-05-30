package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.modelo.MotivoTransferencia;
import gm.SistemaBancario.modelo.Transferencia;
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

@ExtendWith(MockitoExtension.class) // 1. Le dice a JUnit que use Mockito
class TransferenciaServicioImplTest {

    // 2. Creamos los mocks (simuladores) de los repositorios que usa tu servicio
    @Mock
    private CuentaRepositorio cuentaRepositorio;

    @Mock
    private MotivoTransferenciaRepositorio motivoRepositorio;

    @Mock
    private TransferenciaRepositorio transferenciaRepositorio;

    // 3. Mockito crea la implementación del servicio e inyecta los mocks de arriba adentro
    @InjectMocks
    private TransferenciaServicioImpl transferenciaServicio;

    @Test
    void realizarTransferencia_Exitosamente_DeberiaMoverDineroYRegistrar() {
        // ==========================================
        // PASO 1: GIVEN (Preparar los datos simulados)
        // ==========================================

        // Creamos las cuentas con saldos usando BigDecimal (como tenés en tu modelo)
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(1L);
        cuentaOrigen.setSaldo(new BigDecimal("5000.00")); // Arranca con 5000

        Cuenta cuentaDestino = new Cuenta();
        cuentaDestino.setIdCuenta(2L);
        cuentaDestino.setSaldo(new BigDecimal("1000.00")); // Arranca con 1000

        MotivoTransferencia motivo = new MotivoTransferencia();
        motivo.setIdMotivo(9L);

        // Configuramos la mente de los Mocks (qué responder cuando el servicio los llame)
        when(cuentaRepositorio.findById(1L)).thenReturn(Optional.of(cuentaOrigen));
        when(cuentaRepositorio.findById(2L)).thenReturn(Optional.of(cuentaDestino));
        when(motivoRepositorio.findById(9L)).thenReturn(Optional.of(motivo));

        // Para el guardado de la transferencia, le decimos que devuelva el mismo objeto que recibe
        when(transferenciaRepositorio.save(any(Transferencia.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ==========================================
        // PASO 2: WHEN (Ejecutar la lógica real)
        // ==========================================

        Float montoATransferir = 2000.0f;
        Transferencia resultado = transferenciaServicio.realizarTransferencia(1L, 2L, montoATransferir, 9L);

        // ==========================================
        // PASO 3: THEN (Verificar que todo salió bien)
        // ==========================================

        // Verificamos que el objeto transferencia no sea nulo y tenga los datos correctos
        assertNotNull(resultado);
        assertEquals("COMPLETADA", resultado.getEstado());
        assertEquals(montoATransferir, resultado.getMonto());

        // Verificamos matemáticamente que los BigDecimals se hayan actualizado correctamente
        // 5000 - 2000 = 3000
        assertEquals(new BigDecimal("3000.00"), cuentaOrigen.getSaldo());
        // 1000 + 2000 = 3000
        assertEquals(new BigDecimal("3000.00"), cuentaDestino.getSaldo());

        // Verificamos que se haya llamado al método .save() para impactar los cambios en las cuentas
        verify(cuentaRepositorio, times(1)).save(cuentaOrigen);
        verify(cuentaRepositorio, times(1)).save(cuentaDestino);

        // Verificamos que finalmente se guardó el registro de la transferencia
        verify(transferenciaRepositorio, times(1)).save(any(Transferencia.class));
    }
}