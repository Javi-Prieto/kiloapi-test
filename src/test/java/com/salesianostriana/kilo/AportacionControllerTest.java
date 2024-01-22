package com.salesianostriana.kilo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesianostriana.kilo.controllers.AportacionController;
import com.salesianostriana.kilo.dtos.aportaciones.AportacionRequestDTO;
import com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO;
import com.salesianostriana.kilo.dtos.aportaciones.LineaDTO;
import com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.entities.keys.DetalleAportacionPK;
import com.salesianostriana.kilo.services.AportacionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(controllers = AportacionController.class)
public class AportacionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    AportacionService aportacionService;

    @Test
    void getDetallesAportacion_200() throws Exception{
        Aportacion toRes = Aportacion.builder()
                .clase(Clase.builder().build())
                .id(1L)
                .detalleAportaciones(List.of(DetalleAportacion.builder()
                        .detalleAportacionPK(new DetalleAportacionPK(1L, 1L))
                        .tipoAlimento(TipoAlimento.builder().id(1L).build())
                        .build()))
                .fecha(LocalDate.now())
                .build();
        when(aportacionService.findById(1L)).thenReturn(Optional.of(toRes));
        mockMvc.perform(MockMvcRequestBuilders.get("/aportacion/{id}", 1)
                            .contentType("application/json"))
                    .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/aportacion/{id}", 1)
                        .contentType("application/json"))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        AportacionesReponseDTO expected = AportacionesReponseDTO.of(toRes);

        Assertions.assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expected));

    }

    @Test
    void getDetallesAportacion_404() throws Exception{
        when(aportacionService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/aportacion/{id}", 1)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAportacion_200()  throws  Exception{
        AportacionRequestDTO a = AportacionRequestDTO.builder().idClase(1L).lineas(List.of(new LineaDTO(1L, 3D))).build();

        when(aportacionService.createAportacion(a)).thenReturn(Optional
                .of(AportacionesReponseDTO.builder()
                        .id(1L)
                        .fecha(LocalDate.now())
                        .clase("1DAM")
                        .detallesAportacion(List.of(
                                DetallesAportacionResponseDTO.builder()
                                        .aportacionId(1L)
                                        .cantidadKg(10)
                                        .nombre("hola")
                                        .numLinea(1L).build())).build()));
        mockMvc.perform(post("/aportacion/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(a))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
