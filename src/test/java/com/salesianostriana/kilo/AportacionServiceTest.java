package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import com.salesianostriana.kilo.services.AportacionService;
import com.salesianostriana.kilo.services.TipoAlimentoSaveService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AportacionServiceTest {

    @Mock
    AportacionRepository aportacionRepository;
    @Mock
    TipoAlimentoSaveService tipoAlimentoSaveService;

    @InjectMocks
    AportacionService aportacionService;

    public static Stream<Arguments> getData(){
        return Stream.of(
                Arguments.arguments(Aportacion.builder()
                                .clase(Clase.builder().build())
                                .detalleAportaciones(List.of(DetalleAportacion.builder().build()))
                                .id(1L)
                        .build()),
                Arguments.arguments(Aportacion.builder()
                        .clase(Clase.builder().build())
                        .detalleAportaciones(List.of())
                        .id(1L)
                        .build()),
                Arguments.arguments(Aportacion.builder()
                        .clase(Clase.builder().build())
                        .detalleAportaciones(null)
                        .id(1L)
                        .build()),
                null
        );
    }

    @ParameterizedTest
    @MethodSource("getData")
    void deleteDetalleAportacion(Aportacion a){
        TipoAlimento tipo = TipoAlimento.builder().build();
        DetalleAportacion detalle = DetalleAportacion.builder().tipoAlimento(tipo).build();

        Mockito.when(aportacionRepository.findDetallesBorrables()).thenReturn(a.getDetalleAportaciones());
        Mockito.when(tipoAlimentoSaveService.save(detalle.getTipoAlimento()));
        Mockito.when(aportacionRepository.save(a));
        if (a == null){
            Throwable throwable = assertThrows(NullPointerException.class ,() -> aportacionService.borrarDetallesAportacion(a));
        }


    }

}
