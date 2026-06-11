package gm.SistemaBancario.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import gm.SistemaBancario.dto.TransferenciaDTO;
import gm.SistemaBancario.modelo.Transferencia;
import gm.SistemaBancario.servicio.TransferenciaServicio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransferenciaControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransferenciaServicio transferenciaServicio; // Clon falso del servicio

    private final ObjectMapper objectMapper = new ObjectMapper(); // Convierte objetos Java a JSON (texto)

    @Test
    void realizarTransferencia_DeberiaRetornarHttp200YTransferencia() throws Exception {
        // --- GIVEN (Preparar los datos) ---
        TransferenciaDTO dto = new TransferenciaDTO();
        dto.setCuentaOrigen(1L);
        dto.setCuentaDestino(11L);
        dto.setMonto(1500.0f);
        dto.setMotivo(1L);

        Transferencia tSimulada = new Transferencia();
        tSimulada.setId(100L);
        tSimulada.setMonto(1500.0f);
        tSimulada.setEstado("COMPLETADA");

        // Cuando el controlador llame al servicio, devolvemos la transferencia simulada
        when(transferenciaServicio.realizarTransferencia(1L, 11L, 1500.0f, 1L))
                .thenReturn(tSimulada);

        // --- WHEN y THEN (Simular la petición HTTP de React/Postman) ---
        mockMvc.perform(post("/api/transferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))) // Convertimos el DTO a JSON texto
                .andExpect(status().isOk()) // Esperamos un HTTP 200 OK
                .andExpect(jsonPath("$.id").value(100)) // Verificamos los datos del JSON devuelto
                .andExpect(jsonPath("$.estado").value("COMPLETADA"))
                .andExpect(jsonPath("$.monto").value(1500.0));
    }

    @Test
    void obtenerHistorial_DeberiaRetornarListaDeTransferencias() throws Exception {
        // --- GIVEN ---
        Transferencia t = new Transferencia();
        t.setId(50L);
        t.setMonto(300.0f);

        when(transferenciaServicio.historialCuenta(1L))
                .thenReturn(Collections.singletonList(t));

        // --- WHEN y THEN ---
        mockMvc.perform(get("/api/transferencias/cuenta/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(50)) // $[0] apunta al primer elemento de la lista JSON
                .andExpect(jsonPath("$[0].monto").value(300.0));
    }
}