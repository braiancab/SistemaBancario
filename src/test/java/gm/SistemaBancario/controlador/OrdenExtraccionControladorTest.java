package gm.SistemaBancario.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import gm.SistemaBancario.modelo.Cuenta;
import gm.SistemaBancario.modelo.OrdenExtraccion;
import gm.SistemaBancario.servicio.OrdenExtraccionServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrdenExtraccionControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrdenExtraccionServicio ordenServicio; // Clon falso del servicio

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void crearOrden_DeberiaRetornarHttp200YOrdenCreada() throws Exception {
        // --- GIVEN (Preparar los datos de entrada que mandaría React) ---
        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(1L);

        OrdenExtraccion peticion = new OrdenExtraccion();
        peticion.setCuentaOrigen(cuentaOrigen);
        peticion.setMonto_orden(2000.0);

        // Preparar lo que va a devolver nuestro servicio falso al procesar la petición
        OrdenExtraccion ordenSimulada = new OrdenExtraccion();

        ordenSimulada.setId_extraccion(10L);
        ordenSimulada.setMonto_orden(2000.0);
        ordenSimulada.setCodigo("A1B2C3");

        when(ordenServicio.crearOrdenExtraccion(any(OrdenExtraccion.class))).thenReturn(ordenSimulada);

        // --- WHEN y THEN (Simular la petición POST) ---
        mockMvc.perform(post("/api/ordenes_extraccion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(peticion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("A1B2C3"))
                .andExpect(jsonPath("$.monto_orden").value(2000.0));
    }

    @Test
    void obtenerHistorialPorCliente_DeberiaRetornarListaYHttp200() throws Exception {
        // --- GIVEN (Preparar el historial falso) ---
        OrdenExtraccion ordenSimulada = new OrdenExtraccion();
        ordenSimulada.setMonto_orden(500.0);
        ordenSimulada.setCodigo("XYZ789");

        when(ordenServicio.historialOrdenExtraccion(1L))
                .thenReturn(Collections.singletonList(ordenSimulada));

        // --- WHEN y THEN (Simular la petición GET de React) ---
        mockMvc.perform(get("/api/ordenes_extraccion/historial/cliente/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].monto_orden").value(500.0))
                .andExpect(jsonPath("$[0].codigo").value("XYZ789"));
    }
}