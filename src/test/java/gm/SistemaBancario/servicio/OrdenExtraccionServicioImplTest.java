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
        // GIVEN: Cuenta 1 con $15.000, extracción de $2.000, DNI válido (8 dígitos)
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(1L);
        cuentaOrigen.setSaldo(new BigDecimal("15000.00"));

        OrdenExtraccion orden = new OrdenExtraccion();
        orden.setCuentaOrigen(cuentaOrigen);
        orden.setMonto_orden(2000.0);
        orden.setDni("12345678");

        when(cuentaRepositorio.findById(1L)).thenReturn(Optional.of(cuentaOrigen));
        when(ordenRepositorio.save(any(OrdenExtraccion.class))).thenAnswer(i -> i.getArgument(0));

        // WHEN
        OrdenExtraccion resultado = ordenServicio.crearOrdenExtraccion(orden);

        // THEN: Se realiza exitosamente
        assertNotNull(resultado);
        assertNotNull(resultado.getCodigo()); // Verificamos que se generó el código
        assertEquals(new BigDecimal("13000.00"), cuentaOrigen.getSaldo()); // 15000 - 2000

        verify(cuentaRepositorio, times(1)).save(cuentaOrigen);
        verify(ordenRepositorio, times(1)).save(any(OrdenExtraccion.class));
    }

    @Test
    void crearOrdenExtraccion_SaldoInsuficiente_DeberiaLanzarRuntimeException() {
        // GIVEN: Cuenta 2 con $15.000, extracción de $25.000, DNI válido
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(2L);
        cuentaOrigen.setSaldo(new BigDecimal("15000.00"));

        OrdenExtraccion orden = new OrdenExtraccion();
        orden.setCuentaOrigen(cuentaOrigen);
        orden.setMonto_orden(25000.0);
        orden.setDni("12345678");

        when(cuentaRepositorio.findById(2L)).thenReturn(Optional.of(cuentaOrigen));

        // WHEN & THEN: Muestra mensaje de "Saldo insuficiente"
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            ordenServicio.crearOrdenExtraccion(orden);
        });

        assertEquals("Saldo insuficiente", excepcion.getMessage());

        // Verificamos que NO se guardó la orden ni se modificó el saldo en la BD
        verify(cuentaRepositorio, never()).save(any(Cuenta.class));
        verify(ordenRepositorio, never()).save(any(OrdenExtraccion.class));
    }
    @Test
    void crearOrdenExtraccion_DniConFormatoIncorrecto_DeberiaLanzarExcepcion() {
        // GIVEN: Cuenta 1 con $15.000, extracción de $2.000, DNI inválido (9 dígitos)
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(1L);
        cuentaOrigen.setSaldo(new BigDecimal("15000.00"));

        OrdenExtraccion orden = new OrdenExtraccion();
        orden.setCuentaOrigen(cuentaOrigen);
        orden.setMonto_orden(2000.0);
        orden.setDni("123456789");

        // Usamos lenient() por si la validación frena el flujo en la primera línea
        lenient().when(cuentaRepositorio.findById(1L)).thenReturn(Optional.of(cuentaOrigen));

        // WHEN & THEN: Esperamos la excepción
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            ordenServicio.crearOrdenExtraccion(orden);
        });

        // Verificamos el texto exacto que configuramos en tu backend
        assertEquals("DNI invalido", excepcion.getMessage());

        // Verificamos que no se haya persistido absolutamente nada en la BD
        verify(cuentaRepositorio, never()).save(any(Cuenta.class));
        verify(ordenRepositorio, never()).save(any(OrdenExtraccion.class));
    }
    @Test
    void historialOrdenExtraccion_ClienteConOrdenes_DeberiaRetornarLista() {
        // GIVEN: Cliente 1 con historial
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1L);
        cliente.setDni("12345678");

        // Creamos una lista falsa con al menos 1 orden
        List<OrdenExtraccion> listaFalsa = new ArrayList<>();
        listaFalsa.add(new OrdenExtraccion());

        when(clienteRepositorio.findById(1L)).thenReturn(Optional.of(cliente));
        when(ordenRepositorio.findByDni("12345678")).thenReturn(listaFalsa);

        // WHEN
        List<OrdenExtraccion> resultado = ordenServicio.historialOrdenExtraccion(1L);

        // THEN: Retorna la lista de órdenes
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty()); // Verificamos que la lista no esté vacía

        verify(clienteRepositorio, times(1)).findById(1L);
        verify(ordenRepositorio, times(1)).findByDni("12345678");
    }

    @Test
    void historialOrdenExtraccion_ClienteSinOrdenes_DeberiaLanzarExcepcion() {
        // GIVEN: Cliente 2 sin historial
        Cliente cliente = new Cliente();
        cliente.setIdCliente(2L);
        cliente.setDni("87654321");

        // Simulamos que el cliente existe...
        when(clienteRepositorio.findById(2L)).thenReturn(Optional.of(cliente));
        // la base de datos devuelve una lista VACÍA al buscar sus órdenes
        when(ordenRepositorio.findByDni("87654321")).thenReturn(new ArrayList<>());

        // WHEN & THEN: Esperamos que lance el mensaje exacto de la tabla
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            ordenServicio.historialOrdenExtraccion(2L);
        });

        // Verificamos el texto exacto del error
        assertEquals("No hay movimientos registrados", excepcion.getMessage());

        // Verificamos que sí se llamó a ambos repositorios para intentar buscar la info
        verify(clienteRepositorio, times(1)).findById(2L);
        verify(ordenRepositorio, times(1)).findByDni("87654321");
    }
}