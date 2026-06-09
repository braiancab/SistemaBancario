package gm.SistemaBancario.servicio;

import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.modelo.OrdenExtraccion;
import gm.SistemaBancario.repositorio.CuentaRepositorio;
import gm.SistemaBancario.repositorio.OrdenExtraccionRepositorio;
import gm.SistemaBancario.repositorio.ClienteRepositorio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.ArrayList;
import gm.SistemaBancario.modelo.Cliente;

@ExtendWith(MockitoExtension.class) // 1. Le dice a JUnit que use Mockito
class OrdenExtraccionServicioImplTest {

    // 2. Creamos los mocks (simuladores) de los repositorios
    @Mock
    private CuentaRepositorio cuentaRepositorio;

    @Mock
    private OrdenExtraccionRepositorio ordenRepositorio;
    @Mock
    private ClienteRepositorio clienteRepositorio;

    // 3. Mockito crea la implementación del servicio e inyecta los mocks
    @InjectMocks
    private OrdenExtraccionServicioImpl ordenServicio;

    @Test
    void crearOrdenExtraccion_Exitosamente_DeberiaDescontarDineroYGenerarCodigo() {
        // ==========================================
        // PASO 1: GIVEN (Preparar los datos simulados)
        // ==========================================

        // Creamos la cuenta con saldo usando BigDecimal
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(1L);
        cuentaOrigen.setSaldo(new BigDecimal("10000.00")); // Arranca con $10.000

        // Creamos la orden de extracción que entra por parámetro
        OrdenExtraccion orden = new OrdenExtraccion();
        orden.setCuentaOrigen(cuentaOrigen);
        orden.setMonto_orden(2000.0); // Quiere extraer $2.000

        // Configuramos la mente de los Mocks
        when(cuentaRepositorio.findById(1L)).thenReturn(Optional.of(cuentaOrigen));

        // Para el guardado de la orden, le decimos que devuelva el mismo objeto que recibe
        when(ordenRepositorio.save(any(OrdenExtraccion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ==========================================
        // PASO 2: WHEN (Ejecutar la lógica real)
        // ==========================================

        OrdenExtraccion resultado = ordenServicio.crearOrdenExtraccion(orden);

        // ==========================================
        // PASO 3: THEN (Verificar que todo salió bien)
        // ==========================================

        // Verificamos que el objeto no sea nulo y se haya generado el código de 6 letras
        assertNotNull(resultado);
        assertNotNull(resultado.getCodigo());
        assertEquals(6, resultado.getCodigo().length());

        // Verificamos matemáticamente que el BigDecimal se haya actualizado
        // 10000 - 2000 = 8000
        assertEquals(new BigDecimal("8000.00"), cuentaOrigen.getSaldo());

        // Verificamos que se haya llamado al método .save() para impactar el saldo nuevo
        verify(cuentaRepositorio, times(1)).save(cuentaOrigen);

        // Verificamos que finalmente se guardó el registro de la orden
        verify(ordenRepositorio, times(1)).save(any(OrdenExtraccion.class));
    }

    @Test
    void crearOrdenExtraccion_SaldoInsuficiente_DeberiaLanzarRuntimeException() {
        // ==========================================
        // PASO 1: GIVEN (Origen con POCO dinero)
        // ==========================================
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(1L);
        cuentaOrigen.setSaldo(new BigDecimal("1000.00")); // Solo tiene $1.000

        OrdenExtraccion orden = new OrdenExtraccion();
        orden.setCuentaOrigen(cuentaOrigen);
        orden.setMonto_orden(2500.0); // Quiere extraer $2.500 (No le alcanza)

        // Mockeamos la búsqueda
        when(cuentaRepositorio.findById(1L)).thenReturn(Optional.of(cuentaOrigen));

        // ==========================================
        // PASO 2 Y 3: WHEN y THEN (Esperamos el error)
        // ==========================================

        // assertThrows verifica que al ejecutar el código se lance la excepción esperada
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            ordenServicio.crearOrdenExtraccion(orden);
        });

        // Verificamos que el mensaje del error sea exactamente el que escribiste en tu lógica
        assertEquals("Saldo insuficiente", excepcion.getMessage());

        // SUPER IMPORTANTE: Verificamos que NUNCA se haya llamado al método .save()
        // Si no hay plata, no se debe descontar nada ni guardar la orden
        verify(cuentaRepositorio, never()).save(any(Cuenta.class));
        verify(ordenRepositorio, never()).save(any(OrdenExtraccion.class));
    }

    @Test
    void crearOrdenExtraccion_CuentaNoEncontrada_DeberiaLanzarRuntimeException() {
        // ==========================================
        // PASO 1: GIVEN (Cuenta inexistente)
        // ==========================================
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(99L); // ID que no existe

        OrdenExtraccion orden = new OrdenExtraccion();
        orden.setCuentaOrigen(cuentaOrigen);
        orden.setMonto_orden(1000.0);

        // Simulamos que la base de datos devuelve un Optional vacío
        when(cuentaRepositorio.findById(99L)).thenReturn(Optional.empty());

        // ==========================================
        // PASO 2 Y 3: WHEN y THEN (Esperamos el error)
        // ==========================================

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            ordenServicio.crearOrdenExtraccion(orden);
        });

        assertEquals("Cuenta no encontrada", excepcion.getMessage());

        verify(cuentaRepositorio, never()).save(any(Cuenta.class));
        verify(ordenRepositorio, never()).save(any(OrdenExtraccion.class));
    }
    @Test
    void historialOrdenExtraccion_ClienteExiste_DeberiaDevolverListaDeOrdenes() {
        // ==========================================
        // PASO 1: GIVEN (Preparar el cliente y su historial)
        // ==========================================

        // Creamos el cliente falso con un DNI específico
        Cliente clienteFalso = new Cliente();
        clienteFalso.setIdCliente(1L);
        clienteFalso.setDni("12345678");

        // Creamos una lista falsa de extracciones simulando lo que devolvería la BD
        List<OrdenExtraccion> listaFalsa = new ArrayList<>();
        listaFalsa.add(new OrdenExtraccion());
        listaFalsa.add(new OrdenExtraccion());

        // Le enseñamos a los mocks qué responder en cadena
        // 1ro: Cuando busquen al cliente 1, devolvé el clienteFalso
        when(clienteRepositorio.findById(1L)).thenReturn(Optional.of(clienteFalso));
        // 2do: Cuando busquen por el DNI "12345678", devolvé la lista de 2 órdenes
        when(ordenRepositorio.findByDni("12345678")).thenReturn(listaFalsa);

        // ==========================================
        // PASO 2: WHEN (Ejecutar el método)
        // ==========================================

        List<OrdenExtraccion> resultado = ordenServicio.historialOrdenExtraccion(1L);

        // ==========================================
        // PASO 3: THEN (Verificar el comportamiento)
        // ==========================================

        // Comprobamos que no devuelva nulo y que la lista tenga exactamente 2 elementos
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        // Comprobamos que el servicio efectivamente llamó a ambos repositorios una vez cada uno
        verify(clienteRepositorio, times(1)).findById(1L);
        verify(ordenRepositorio, times(1)).findByDni("12345678");
    }

    @Test
    void historialOrdenExtraccion_ClienteNoExiste_DeberiaLanzarExcepcion() {
        // ==========================================
        // PASO 1: GIVEN (El cliente no está en la base de datos)
        // ==========================================

        // El repositorio de clientes no encuentra nada
        when(clienteRepositorio.findById(99L)).thenReturn(Optional.empty());

        // ==========================================
        // PASO 2 Y 3: WHEN y THEN (Esperamos la explosión controlada)
        // ==========================================

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            ordenServicio.historialOrdenExtraccion(99L);
        });

        // Verificamos que el texto del error sea exacto
        assertEquals("Cliente no encontrado", excepcion.getMessage());

        // SUPER IMPORTANTE: Como el cliente no se encontró, el servicio NUNCA
        // debería intentar ir a buscar órdenes por DNI. Comprobamos que eso no pase.
        verify(ordenRepositorio, never()).findByDni(anyString());
    }
}